package com.challengeurlshortapi.url_short_api.repository;

import com.challengeurlshortapi.url_short_api.converter.UrlConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(UrlConverter.class)
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void testSaveUrl_whenValidUrl_persistsSuccessfully() {
        // Given

        // When

        // Then

    }

}
