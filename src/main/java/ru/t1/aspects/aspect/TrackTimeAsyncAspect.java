package ru.t1.aspects.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.t1.tracktime.service.TrackTimeService;

import java.util.concurrent.CompletableFuture;

import static ru.t1.aspects.aspect.AspectHelper.executionTime;

/**
 * Аспект для асинхронного отслеживания времени выполнения методов
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TrackTimeAsyncAspect {
    private final TrackTimeService trackTimeService;

    @Pointcut("@annotation(ru.t1.aspects.annotation.TrackTimeAsync)")
    public void asyncRunningPointcut(){
    }

    /**
     * Метод для асинхронного отслеживания времени выполнения методов
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return {@link Object}
     */
    @Around("asyncRunningPointcut()")
    public Object trackTimeAsync(ProceedingJoinPoint joinPoint) {
        CompletableFuture<Object> futureResult = CompletableFuture.supplyAsync(() -> {
            try {
                return executionTime(joinPoint, true, log, trackTimeService);
            } catch (Throwable e) {
                log.error("Exception in async method {}: {}", joinPoint.getSignature().getName(), e.getMessage());
                throw new RuntimeException(e);
            }
        });
        return futureResult.join();
    }
}
