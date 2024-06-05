package ru.t1.aspects.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;
import ru.t1.tracktime.service.TrackTimeService;

import java.time.LocalDateTime;

public class AspectHelper {
    private AspectHelper() {
    }

    static Object executionTime(ProceedingJoinPoint joinPoint, Boolean iaAsync,
                                Logger log, TrackTimeService trackTimeService) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        LocalDateTime timeStamp = LocalDateTime.now();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        StringBuilder name = new StringBuilder();
        name.append(methodSignature.getDeclaringType().getSimpleName());
        name.append(".");
        name.append(methodSignature.getName());

        stopWatch.start();

        log.info("TrackTime: method: {}, timestamp: {}", name, timeStamp);

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        long totalTime = stopWatch.getTotalTimeMillis();

        log.info("TrackTime: method: {}, timestamp: {}, duration: {}", name, timeStamp, totalTime);

        trackTimeService.add(name.toString(), iaAsync, timeStamp,totalTime);
        return proceed;
    }
}
