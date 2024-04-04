/*
package com.challengeurlshortapi.url_short_api.repository;

import com.challengeurlshortapi.url_short_api.converter.UrlConverter;
import com.challengeurlshortapi.url_short_api.entity.UrlEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UrlConverter.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    private UrlEntity urlEntity;

    @BeforeEach
    void setUp() {
        urlEntity = UrlEntity.builder()
                .originalUrl("https://www.example.com")
                .shortUrl("abc123")
                .expiredAt(LocalDateTime.now().plusDays(7))
                .visitCount(0)
                .build();
    }

    @Test
    void shouldSaveAndFindUrlEntity() {
        // given
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);

        // when
        Optional<UrlEntity> foundUrlEntity = urlRepository.findById(savedUrlEntity.getId());

        // then
        assertThat(foundUrlEntity).isPresent();
        assertThat(foundUrlEntity.get()).isEqualTo(savedUrlEntity);
    }

    @Test
    void shouldFindByShortUrl() {
        // given
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);

        // when
        Optional<UrlEntity> foundUrlEntity = urlRepository.findByShortUrl(savedUrlEntity.getShortUrl());

        // then
        assertThat(foundUrlEntity).isPresent();
        assertThat(foundUrlEntity.get()).isEqualTo(savedUrlEntity);
    }

   */
/* @Test
    void shouldFindByOriginalUrl() {
        // given
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);

        // when
        Optional<UrlEntity> foundUrlEntity = urlRepository.findByOriginalUrl(savedUrlEntity.getOriginalUrl());

        // then
        assertThat(foundUrlEntity).isPresent();
        assertThat(foundUrlEntity.get()).isEqualTo(savedUrlEntity);
    }*//*


    @Test
    void shouldIncrementVisitCount() {
        // given
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);
        int initialVisitCount = savedUrlEntity.getVisitCount();

        // when
        savedUrlEntity.setVisitCount(savedUrlEntity.getVisitCount() + 1);
        urlRepository.save(savedUrlEntity);
        Optional<UrlEntity> updatedUrlEntityOptional = urlRepository.findById(savedUrlEntity.getId());

        // then
        assertThat(updatedUrlEntityOptional).isPresent();
        UrlEntity updatedUrlEntity = updatedUrlEntityOptional.get();
        assertThat(updatedUrlEntity.getVisitCount()).isEqualTo(initialVisitCount + 1);
    }

    @Test
    void shouldDeleteUrlEntity() {
        // given
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);

        // when
        urlRepository.delete(savedUrlEntity);

        // then
        Optional<UrlEntity> foundUrlEntity = urlRepository.findById(savedUrlEntity.getId());
        assertThat(foundUrlEntity).isEmpty();
    }
}
*/
