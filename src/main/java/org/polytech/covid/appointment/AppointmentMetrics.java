package org.polytech.covid.appointment;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
public class AppointmentMetrics {
    private final MeterRegistry registry;
    @Autowired
    public AppointmentMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    @AfterReturning("execution(public * org.polytech.covid.appointment.AppointmentService.*(..))")
    public void successCounter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        registry.counter(methodName + "-success", "path", methodName).increment();
    }

    @AfterThrowing("execution(public * org.polytech.covid.appointment.AppointmentService.*(..))")
    public void failCounter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        registry.counter(methodName + "-fail", "path", methodName).increment();
    }
}
