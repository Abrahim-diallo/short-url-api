package com.challengeurlshortapi.url_short_api.dto;

import com.challengeurlshortapi.url_short_api.constant.UrlConstant;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlCreationRequest {
    @NotNull(message = "L'URL originale ne peut pas être nulle")
    @NotBlank(message = "L'URL originale ne peut pas être vide")
    @URL(message = "L'URL originale n'est pas valide")
    private String originalUrl;

    @Future(message = "Le format de date ou la date d'expiration n'est pas valide")
    @DateTimeFormat(pattern = UrlConstant.LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime expiredAt;
}