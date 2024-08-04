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

    @AfterThrowing(pointcut = "within(ru.koryruno.springbootaopt1.service.*) && " +
            "execution(* *(..)) throws ru.koryruno.springbootaopt1.exception.Throw *", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("Ошибка: {}", e.getMessage());
        log.info("Произошла ошибка при вызове метода: {}", joinPoint.getSignature().toLongString());
    }
}
