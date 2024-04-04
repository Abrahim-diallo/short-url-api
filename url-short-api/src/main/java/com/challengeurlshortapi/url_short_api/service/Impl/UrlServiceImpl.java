package com.challengeurlshortapi.url_short_api.service.Impl;

import com.challengeurlshortapi.url_short_api.converter.UrlConverter;
import com.challengeurlshortapi.url_short_api.dto.UrlCreationRequest;
import com.challengeurlshortapi.url_short_api.dto.UrlDto;
import com.challengeurlshortapi.url_short_api.entity.UrlEntity;
import com.challengeurlshortapi.url_short_api.exception.UrlExpiredException;
import com.challengeurlshortapi.url_short_api.exception.UrlNotFoundException;
import com.challengeurlshortapi.url_short_api.exception.UrlShortenerException;
import com.challengeurlshortapi.url_short_api.repository.UrlRepository;
import com.challengeurlshortapi.url_short_api.service.UrlService;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;
    private final UrlConverter urlConverter;

    /**
     * Supprime périodiquement les URLs expirées.
     */
    @Scheduled(cron = "0 0/15 * * * *")
    @Transactional
    public void clearExpiredUrls() {
        try {
            log.info("Suppression des URLs expirées");
            long deletedCount = urlRepository.deleteByExpiredAtBefore(LocalDateTime.now());
            log.info("{} URLs expirées supprimées", deletedCount);
        } catch (Exception e) {
            log.error("Erreur lors de la suppression des URLs expirées : {}", e.getMessage(), e);
        }
    }

    /**
     * Crée une nouvelle URL courte unique à partir d'une URL originale.
     */
    @Override
    @Transactional
    public UrlDto createShortUrl(UrlCreationRequest request) {
        log.debug("Création d'une nouvelle URL courte pour l'URL originale : {}", request.getOriginalUrl());
        String shortUrl = generateUniqueShortUrl(request.getOriginalUrl());
        UrlEntity entity = buildUrlEntity(request, shortUrl);
        UrlEntity savedEntity = saveUrlEntity(entity);
        log.info("URL courte créée avec succès : {}", savedEntity.getShortUrl());
        return urlConverter.toDto(savedEntity);
    }

    /**
     * Construit une entité UrlEntity à partir des informations fournies.
     */

    private UrlEntity buildUrlEntity(UrlCreationRequest request, String shortUrl) {
        return UrlEntity.builder()
                .originalUrl(request.getOriginalUrl())
                .shortUrl(shortUrl)
                .expiredAt(request.getExpiredAt() == null ? LocalDateTime.now().plusDays(1) : request.getExpiredAt())
                .build();
    }

    /**
     * Génère une URL courte unique à partir d'une URL originale.
     */
    private String generateUniqueShortUrl(String originalUrl) {
        String shortUrl;
        do {
            shortUrl = generateShortUrlByOriginalUrl(originalUrl);
        } while (urlRepository.existsByShortUrl(shortUrl));
        return shortUrl;
    }

    /**
     * Sauvegarde une entité UrlEntity dans la base de données.
     */
    private UrlEntity saveUrlEntity(UrlEntity entity) {
        try {
            return urlRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            log.error("Impossible de générer une URL courte unique : {}", ex.getMessage(), ex);
            throw new UrlShortenerException("Impossible de générer une URL courte unique");
        }
    }

    /**
     * Récupère l'URL originale à partir d'une URL courte.
     */
    @Override
    public UrlDto getOriginalUrlByShortUrl(String shortUrl) {
        log.debug("Récupération de l'URL courte : {}", shortUrl);
        UrlEntity entity = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> {
                    log.warn("URL courte non trouvée : {}", shortUrl);
                    throw new UrlNotFoundException("URL courte non trouvée : " + shortUrl);
                });

        if (entity.getExpiredAt().isBefore(LocalDateTime.now())) {
            log.warn("URL courte expirée : {}", shortUrl);
            throw new UrlExpiredException("URL courte expirée : " + shortUrl);
        }
        log.info("URL courte récupérée avec succès : {}", entity.getShortUrl());
        return urlConverter.toDto(entity);
    }

    /**
     * Récupère toutes les URLs courtes associées à une URL originale.
     */
    @Override
    public List<UrlDto> getAllByOriginalUrl(String originalUrl) {
        log.debug("Récupération des URLs courtes pour l'URL originale : {}", originalUrl);
        List<UrlEntity> entities = urlRepository.findByOriginalUrl(originalUrl);
        return entities.stream()
                .map(urlConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Incrémente le compteur de visites d'une URL courte.
     */
    @Transactional
    public UrlDto incrementVisitCount(String shortUrl) {
        log.debug("Incrémentation du compteur de visites pour l'URL courte : {}", shortUrl);
        UrlEntity entity = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL courte non trouvée : " + shortUrl));
        // On incrémente le compteur de visites de manière thread-safe
        synchronized (entity) {
            entity.setVisitCount(entity.getVisitCount() + 1);
        }
        UrlEntity savedEntity = urlRepository.save(entity);
        return urlConverter.toDto(savedEntity);
    }

    /**
     * Génère une URL courte à partir d'une URL originale.
     */
    private String generateShortUrlByOriginalUrl(String originalUrl) {
        log.debug("Génération d'une URL courte pour l'URL originale : {}", originalUrl);
        String hashedUrl = Hashing.murmur3_32_fixed()
                .hashString(originalUrl.concat(String.valueOf(System.currentTimeMillis())), StandardCharsets.UTF_8).toString();
        log.debug("URL courte générée : {}", hashedUrl);
        return hashedUrl;
    }
}