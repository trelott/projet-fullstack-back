package org.polytech.covid.user;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserMetrics {
    private final MeterRegistry registry;
    @Autowired
    public UserMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    @AfterReturning("execution(* org.polytech.covid.user.UserService.*(..))")
    public void successCounter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        registry.counter(methodName + "-success", "method", methodName).increment();
    }

    @AfterThrowing("execution(* org.polytech.covid.user.UserService.*(..))")
    public void failCounter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        registry.counter(methodName + "-fail", "method", methodName).increment();
    }
}
