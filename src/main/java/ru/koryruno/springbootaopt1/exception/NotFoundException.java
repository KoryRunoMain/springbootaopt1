package ru.koryruno.springbootaopt1.exception;

import ru.koryruno.springbootaopt1.annotation.Throw;

@Throw
public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

}
