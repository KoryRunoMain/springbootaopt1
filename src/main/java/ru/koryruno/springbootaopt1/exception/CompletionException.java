package ru.koryruno.springbootaopt1.exception;

import ru.koryruno.springbootaopt1.annotation.Throw;

@Throw
public class CompletionException extends RuntimeException {

    public CompletionException(String message) {
        super(message);
    }

}
