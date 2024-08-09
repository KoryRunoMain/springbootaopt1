package ru.koryruno.springbootaopt1.aspectTest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.koryruno.springbootaopt1.aspect.ExceptionHandlerAspect;

public class ExceptionHandlerAspectTest {

    @Mock
    private JoinPoint joinPoint;

    @InjectMocks
    private ExceptionHandlerAspect exceptionHandlerAspect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(joinPoint.getSignature()).thenReturn(Mockito.mock(Signature.class));
        Mockito.when(joinPoint.getSignature().toLongString()).thenReturn("Test message");
    }

    @Test
    public void testAfterThrowing() {
        Exception testException = new RuntimeException("Error message");
        exceptionHandlerAspect.afterThrowing(joinPoint, testException);
        Mockito.verify(joinPoint, Mockito.times(2)).getSignature();
    }

}
