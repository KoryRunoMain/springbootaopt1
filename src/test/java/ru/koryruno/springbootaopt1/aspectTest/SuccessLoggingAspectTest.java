package ru.koryruno.springbootaopt1.aspectTest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import ru.koryruno.springbootaopt1.aspect.SuccessLoggingAspect;

public class SuccessLoggingAspectTest {

    @Mock
    private Logger logger;
    @InjectMocks
    private SuccessLoggingAspect successLoggingAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAfterReturning() {
        JoinPoint joinPoint = Mockito.mock(JoinPoint.class);
        Mockito.when(joinPoint.getSignature()).thenReturn(Mockito.mock(Signature.class));

        successLoggingAspect.afterThrowing(joinPoint);
    }

}
