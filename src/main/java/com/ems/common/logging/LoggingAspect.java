package com.ems.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Pointcut("within(com.ems..controller..*)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("========== API REQUEST ==========");
        log.info("Method: {}", methodName);

        if (args.length > 0) {
            try {
                log.info("Request Body: {}", objectMapper.writeValueAsString(args));
            } catch (Exception e) {
                log.info("Request Body: [unable to serialize]");
            }
        }

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        log.info("Execution Time: {} ms", executionTime);
        log.info("========== END ==========");

        return result;
    }
}
