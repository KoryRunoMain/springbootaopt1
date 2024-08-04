package ru.koryruno.springbootaopt1.exception;

import ru.koryruno.springbootaopt1.annotation.Throw;

@Throw
public class ApplicationException extends RuntimeException {

    public ApplicationException() {}

    public ApplicationException(String message) {
        super(message);
    }

}
