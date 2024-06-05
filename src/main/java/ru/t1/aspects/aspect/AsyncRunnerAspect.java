package ru.t1.aspects.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class AsyncRunnerAspect {

    @Pointcut("@annotation(ru.t1.aspects.annotation.Asynchronously)")
    public void asyncRunnerPointcut() {
    }

    @Around("asyncRunnerPointcut()")
    public Object asyncRunner(ProceedingJoinPoint joinPoint) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("Асинхронный запуск в AsyncRunnerAspect");
                joinPoint.proceed();
            } catch (Throwable e) {
                log.error("Ошибка AsyncRunnerAspect", e);
            }
        });
    }
}
