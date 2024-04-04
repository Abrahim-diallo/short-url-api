package com.challengeurlshortapi.url_short_api.converter;

import com.challengeurlshortapi.url_short_api.dto.UrlDto;
import com.challengeurlshortapi.url_short_api.entity.UrlEntity;
import org.springframework.stereotype.Component;

@Component
public class UrlConverter {
    public UrlDto toDto(UrlEntity urlEntity) {
        return UrlDto.builder()
                .id(urlEntity.getId())
                .originalUrl(urlEntity.getOriginalUrl())
                .shortUrl(urlEntity.getShortUrl())
                .expiredAt(urlEntity.getExpiredAt())
                .createdAt(urlEntity.getCreatedAt())
                .visitCount(urlEntity.getVisitCount())
                .build();
    }

    public UrlEntity toEntity(UrlDto urlDto) {
        return UrlEntity.builder()
                .id(urlDto.getId())
                .originalUrl(urlDto.getOriginalUrl())
                .shortUrl(urlDto.getShortUrl())
                .expiredAt(urlDto.getExpiredAt())
                .createdAt(urlDto.getCreatedAt())
                .visitCount(urlDto.getVisitCount())
                .build();
    }
}