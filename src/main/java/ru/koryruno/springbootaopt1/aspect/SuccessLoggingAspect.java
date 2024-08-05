package ru.koryruno.springbootaopt1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@Order(1)
public class SuccessLoggingAspect {

    @AfterReturning("within(ru.koryruno.springbootaopt1.service.*) && " +
            "@within(ru.koryruno.springbootaopt1.annotation.SuccessLogging)")
    public void afterThrowing(JoinPoint joinPoint) {
        log.info("The method was successfully executed: {}", joinPoint.getSignature().getName());
    }

}
