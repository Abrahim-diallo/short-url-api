package com.challengeurlshortapi.url_short_api.repository;


import com.challengeurlshortapi.url_short_api.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    /**
     * Recherche une URL par son URL courte.
     *
     * @param shortUrl L'URL courte à rechercher.
     * @return L'URL correspondante, s'il existe.
     */
    Optional<UrlEntity> findByShortUrl(String shortUrl);

    /**
     * Recherche des URLs par leur URL originale.
     *
     * @param originalUrl L'URL originale à rechercher.
     * @return Une liste des URLs correspondantes.
     */
    List<UrlEntity> findByOriginalUrl(String originalUrl);

    /**
     * Supprime les URLs expirées avant une certaine date.
     *
     * @param expiredAt La date d'expiration à partir de laquelle supprimer les URLs.
     * @return Le nombre d'URLs supprimées.
     */
    long deleteByExpiredAtBefore(LocalDateTime expiredAt);

    /**
     * Vérifie si une URL courte existe déjà dans la base de données.
     *
     * @param shortUrl L'URL courte à vérifier.
     * @return true si l'URL courte existe, sinon false.
     */
    boolean existsByShortUrl(String shortUrl);
}
