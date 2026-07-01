package hu.anime.libary.AnimeLibary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Custom AnimeException kezelése
     */
    @ExceptionHandler(AnimeException.class)
    public ResponseEntity<hu.anime.libary.AnimeLibary.exception.ErrorResponse> handleAnimeException(
            AnimeException ex, WebRequest request) {

        log.error("AnimeException: {}", ex.getMessage());

        hu.anime.libary.AnimeLibary.exception.ErrorResponse errorResponse = new hu.anime.libary.AnimeLibary.exception.ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Validáció hiba kezelése
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        log.error("Validáció hiba: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validáció sikertelen!");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        response.put("errors", errors);
        response.put("path", request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Általános exception kezelése
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<hu.anime.libary.AnimeLibary.exception.ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {

        log.error("Váratlan hiba: {}", ex.getMessage(), ex);

        hu.anime.libary.AnimeLibary.exception.ErrorResponse errorResponse = new hu.anime.libary.AnimeLibary.exception.ErrorResponse(
                LocalDateTime.now(),
                "Belső szerverhiba történt!",
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}