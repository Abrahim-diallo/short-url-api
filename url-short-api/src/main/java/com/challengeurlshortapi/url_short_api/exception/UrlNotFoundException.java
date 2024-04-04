package com.challengeurlshortapi.url_short_api.exception;


import java.io.Serial;

public class UrlNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UrlNotFoundException() {
        super();
    }

    public UrlNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlNotFoundException(String message) {
        super(message);
    }

    public UrlNotFoundException(Throwable cause) {

    }


}
