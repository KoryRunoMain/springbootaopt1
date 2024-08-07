package ru.koryruno.springbootaopt1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletionException;

@RestControllerAdvice
public class ErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError applicationExceptionHandle(ApplicationException e) {
        return new ApiError(
                ApiStatus.CONFLICT,
                "Integrity constraint has been violated",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundExceptionHandle(NotFoundException e) {
        return new ApiError(
                ApiStatus.NOT_FOUND,
                "The required object was not found",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

    @ExceptionHandler(CompletionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError completionExceptionHandler(CompletionException e) {
        return new ApiError(
                ApiStatus.CONFLICT,
                "Integrity constraint has been violated",
                e.getMessage(),
                LocalDateTime.now().format(formatter)
        );
    }

}
