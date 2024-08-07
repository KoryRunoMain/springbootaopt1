package ru.koryruno.springbootaopt1.aspectTest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.koryruno.springbootaopt1.aspect.AsyncRunnerAspect;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AsyncRunnerAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private AsyncRunnerAspect aspect;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(joinPoint.getSignature()).thenReturn(Mockito.mock(Signature.class));
    }

    @Test
    void whenExecuteJoinPoint_thenProceedIsCalledAsynchronously() throws Throwable {
        Mockito.when(joinPoint.proceed()).thenReturn(null);
        CompletableFuture.runAsync(() -> {
            aspect.asyncRunnerAround(joinPoint);
        }).join();
        Mockito.verify(joinPoint, times(1)).proceed();
    }

    @Test
    void whenJoinPointThrowsException_thenApplicationExceptionIsThrown() throws Throwable {
        Mockito.when(joinPoint.proceed()).thenThrow(new RuntimeException("Error message"));
        assertThrows(CompletionException.class, () -> {
            aspect.asyncRunnerAround(joinPoint);
        });
        Mockito.verify(joinPoint, times(1)).proceed();
    }

}
