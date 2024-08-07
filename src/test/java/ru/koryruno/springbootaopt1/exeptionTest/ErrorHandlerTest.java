package ru.koryruno.springbootaopt1.exeptionTest;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.koryruno.springbootaopt1.exception.ApiError;
import ru.koryruno.springbootaopt1.exception.ApiStatus;
import ru.koryruno.springbootaopt1.exception.ApplicationException;
import ru.koryruno.springbootaopt1.exception.ErrorHandler;
import ru.koryruno.springbootaopt1.exception.NotFoundException;

import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ErrorHandlerTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void applicationException_successfully() {
        ApplicationException exception = new ApplicationException("validation error");
        ApiError response = errorHandler.applicationExceptionHandle(exception);
        assertEquals(ApiStatus.CONFLICT, response.getStatus());
        assertEquals("Integrity constraint has been violated", response.getReason());
        assertEquals("validation error", response.getMessage());
    }

    @Test
    void notFoundException_successfully() {
        NotFoundException exception = new NotFoundException("not found");
        ApiError response = errorHandler.notFoundExceptionHandle(exception);
        assertEquals(ApiStatus.NOT_FOUND, response.getStatus());
        assertEquals("The required object was not found", response.getReason());
        assertEquals("not found", response.getMessage());
    }

    @Test
    void completionException_successfully() {
        CompletionException exception = new CompletionException(new RuntimeException("something went wrong"));
        ApiError response = errorHandler.completionExceptionHandler(exception);
        assertEquals(ApiStatus.CONFLICT, response.getStatus());
        assertEquals("Integrity constraint has been violated", response.getReason());
        assertEquals("java.lang.RuntimeException: something went wrong", response.getMessage());
    }

}
