package ru.t1.aspects.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.t1.tracktime.service.TrackTimeService;

import static ru.t1.aspects.aspect.AspectHelper.executionTime;

/**
 * Аспект для синхронного отслеживания времени выполнения методов
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TrackTimeAspect {
    private final TrackTimeService trackTimeService;

    @Pointcut("@annotation(ru.t1.aspects.annotation.TrackTime)")
    public void syncRunningPointcut(){
    }

    /**
     * Метод для синхронного отслеживания времени выполнения методов
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return {@link Object}
     * @throws Throwable {@link Throwable} исключение
     */
    @Around("syncRunningPointcut()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return executionTime(joinPoint, false, log, trackTimeService);
    }
}
