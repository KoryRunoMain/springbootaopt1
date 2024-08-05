package ru.koryruno.springbootaopt1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@Order(1)
public class ExceptionHandlerAspect {

    @AfterThrowing(pointcut = "within(ru.koryruno.springbootaopt1.controller.*) && " +
            "execution(* *(..)) throws ru.koryruno.springbootaopt1.exception.Throw *", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("Error: {}", e.getMessage());
        log.info("An error occurred while calling the method: {}", joinPoint.getSignature().toLongString());
    }
}
