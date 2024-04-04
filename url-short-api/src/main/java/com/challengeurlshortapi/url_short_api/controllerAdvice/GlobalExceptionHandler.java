package com.challengeurlshortapi.url_short_api.controllerAdvice;

import com.challengeurlshortapi.url_short_api.dto.errors.ErrorDetails;
import com.challengeurlshortapi.url_short_api.dto.errors.ErrorResponse;
import com.challengeurlshortapi.url_short_api.exception.UrlNotFoundException;
import com.challengeurlshortapi.url_short_api.exception.UrlShortenerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation error occurred: {}", ex.getMessage(), ex);
        List<ErrorDetails> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapToErrorDetails)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ErrorResponse(ZonedDateTime.now(), errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HTTP message not readable error occurred: {}", ex.getMessage(), ex);
        ErrorDetails error = ErrorDetails.builder()
                .code("BAD_REQUEST")
                .message("Invalid request body")
                .build();
        return ResponseEntity.badRequest().body(new ErrorResponse(ZonedDateTime.now(), List.of(error)));
    }

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleUrlNotFoundException(UrlNotFoundException ex) {
        log.error("URL not found error occurred: {}", ex.getMessage(), ex);
        ErrorDetails error = ErrorDetails.builder()
                .code("NOT_FOUND")
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ZonedDateTime.now(), List.of(error)));
    }

    @ExceptionHandler(UrlShortenerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleUrlShortenerException(UrlShortenerException ex) {
        log.error("URL shortener error occurred: {}", ex.getMessage(), ex);
        ErrorDetails error = ErrorDetails.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ZonedDateTime.now(), List.of(error)));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorDetails error = ErrorDetails.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ZonedDateTime.now(), List.of(error)));
    }

    private ErrorDetails mapToErrorDetails(FieldError fieldError) {
        return ErrorDetails.builder()
                .code("VALIDATION_ERROR")
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }
}