package com.challengeurlshortapi.url_short_api.service;

import com.challengeurlshortapi.url_short_api.dto.UrlCreationRequest;
import com.challengeurlshortapi.url_short_api.dto.UrlDto;

import java.util.List;

public interface UrlService {
    UrlDto createShortUrl(UrlCreationRequest urlCreationRequest);

    UrlDto getOriginalUrlByShortUrl(String shortUrl);

    List<UrlDto> getAllByOriginalUrl(String originalUrl);

    UrlDto incrementVisitCount(String shortUrl);
}
