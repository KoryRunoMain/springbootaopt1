package ru.koryruno.springbootaopt1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@Aspect
@Slf4j
public class AsyncRunnerAspect {

    @Pointcut("execution(@ru.koryruno.springbootaopt1.annotation.Asynchronously * *(..))")
    public void asyncRunnerPointcut() {}

    @Around("asyncRunnerPointcut()")
    public Object asyncRunnerAround(ProceedingJoinPoint joinPoint) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                log.info("Asynchronous startup in AsyncRunnerAspect");
                return joinPoint.proceed();
            } catch (Throwable e) {
                log.error("Error in AsyncRunnerAspect: ", e);
                throw new CompletionException(e);
            }
        }).join();
    }

}
