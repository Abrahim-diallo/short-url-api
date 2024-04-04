package com.challengeurlshortapi.url_short_api.controller;

import com.challengeurlshortapi.url_short_api.dto.UrlCreationRequest;
import com.challengeurlshortapi.url_short_api.dto.UrlDto;
import com.challengeurlshortapi.url_short_api.exception.UrlNotFoundException;
import com.challengeurlshortapi.url_short_api.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
@Log4j2
public class UrlController {

    private final UrlService urlService;

    /**
     * Crée une URL courte
     *
     * @param urlCreationRequest Requête de création d'URL
     * @return UrlDto Objet DTO de l'URL créée
     */
    @Operation(summary = "Créer une URL courte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Short URL created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlDto.class))}),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide")
    })
    @PostMapping("/createShortUrl")
    public ResponseEntity<UrlDto> createShortUrl(@Valid @RequestBody UrlCreationRequest urlCreationRequest) {
        log.info("Creating short URL for request: {}", urlCreationRequest);
        UrlDto createdUrlDto = urlService.createShortUrl(urlCreationRequest);
        URI location = URI.create("/api/v1/urls/" + createdUrlDto.getShortUrl());
        log.info("Short URL created successfully: {}", location);
        return ResponseEntity.created(location).body(createdUrlDto);
    }

    /**
     * Récupère une URL par son code court
     *
     * @param shortUrl Code court de l'URL
     * @return UrlDto Objet DTO de l'URL
     */
    @Operation(summary = "Récupérer une URL par son url court")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlDto.class))}),
            @ApiResponse(responseCode = "404", description = "URL non trouvée")
    })
    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlDto> getByShortUrl(@PathVariable(name = "shortUrl") String shortUrl) {
        log.info("Fetching URL for short code: {}", shortUrl);
        try {
            UrlDto urlDto = urlService.getOriginalUrlByShortUrl(shortUrl);
            log.info("Original URL retrieved: {}", urlDto.getOriginalUrl());
            return ResponseEntity.ok(urlDto);
        } catch (UrlNotFoundException e) {
            log.error("URL not found: {}", shortUrl);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Récupère toutes les URLs pour une URL d'origine
     *
     * @param originalUrl URL d'origine
     * @return List<UrlDto> Liste des objets DTO des URLs
     */
    @Operation(summary = "Récupérer toutes les URLs pour une URL d'origine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URLs found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlDto.class))}),
            @ApiResponse(responseCode = "404", description = "Aucune URL trouvée")
    })
    @GetMapping("/getByOriginalUrl")
    public ResponseEntity<List<UrlDto>> getAllByOriginalUrl(@RequestParam(name = "originalUrl") String originalUrl) {
        log.info("Fetching URLs for original URL: {}", originalUrl);
        List<UrlDto> urlDtos = urlService.getAllByOriginalUrl(originalUrl);
        return urlDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(urlDtos);
    }

    /**
     * Redirige vers l'URL d'origine
     *
     * @param shortUrl Code court de l'URL
     * @return ResponseEntity Entité de réponse avec redirection
     */
    @Operation(summary = "Rediriger vers l'URL d'origine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirected to the original URL"),
            @ApiResponse(responseCode = "404", description = "URL not found")
    })
    @GetMapping("/redirect/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable(name = "shortUrl") String shortUrl) {
        log.info("Redirecting short URL: {}", shortUrl);
        try {
            UrlDto urlDto = urlService.getOriginalUrlByShortUrl(shortUrl);
            String originalUrl = urlDto.getOriginalUrl();
            // Incrémenter le compteur de visites
            urlService.incrementVisitCount(shortUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(originalUrl));
            log.info("Redirect to: {}", originalUrl);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (UrlNotFoundException e) {
            log.error("URL not found: {}", shortUrl);
            return ResponseEntity.notFound().build();
        }
    }
}