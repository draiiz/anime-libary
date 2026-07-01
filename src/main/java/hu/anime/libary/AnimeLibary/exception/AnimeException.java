package hu.anime.libary.AnimeLibary.exception;

/**
 * Custom exception az Anime műveleteknél
 */
public class AnimeException extends RuntimeException {

    public AnimeException(String message) {
        super(message);
    }

    public AnimeException(String message, Throwable cause) {
        super(message, cause);
    }
}