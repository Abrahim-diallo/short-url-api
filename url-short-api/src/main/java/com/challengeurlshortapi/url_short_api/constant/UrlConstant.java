package com.challengeurlshortapi.url_short_api.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlConstant {

    /**
     * Format pour les dates locales.
     * Utilise un format plus lisible et conforme aux conventions internationales.
     */
    public static final String LOCAL_DATE_FORMAT = "dd-MM-yyyy";

    /**
     * Format pour les date-temps locales.
     * Sépare la date et l'heure avec un espace pour une meilleure lisibilité et conformité avec ISO 8601.
     */
    public static final String LOCAL_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    /**
     * Format pour les date-temps avec fuseau horaire.
     * Ajoute le fuseau horaire pour une clarté internationale, conforme à ISO 8601.
     */
    public static final String ZONED_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss z";

    /**
     * Format pour les instants.
     * Utilise le format ISO 8601 pour les instants, assurant une compréhension et une interopérabilité globales.
     */
    public static final String INSTANT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
}