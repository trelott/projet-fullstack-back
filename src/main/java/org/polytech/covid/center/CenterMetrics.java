package org.polytech.covid.center;

import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CenterMetrics {
    private final MeterRegistry registry;
    @Autowired
    public CenterMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    @AfterReturning("execution(* org.polytech.covid.center.CenterService.*(..))")
    public void successCounter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        registry.counter(methodName + "-success", "method", joinPoint.getSignature().getName()).increment();
    }

    @AfterThrowing("execution(* org.polytech.covid.center.CenterService.*(..))")
    public void failCounter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        registry.counter(methodName + "-fail", "method", joinPoint.getSignature().getName()).increment();
    }

}
