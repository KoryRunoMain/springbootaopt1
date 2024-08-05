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
import ru.koryruno.springbootaopt1.aspect.LoggingAspect;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private LoggingAspect aspect;

    @BeforeEach
    void setUp() {
        Mockito.when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
    }

    @Test
    void whenExecuteJoinPoint_thenLoggerInfoIsCalled() throws Throwable {
        Mockito.when(joinPoint.proceed()).thenReturn(null);
        aspect.around(joinPoint);
        Mockito.verify(joinPoint, times(1)).proceed();
    }

}
