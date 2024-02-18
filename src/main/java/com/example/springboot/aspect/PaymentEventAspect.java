package com.example.springboot.aspect;

import com.example.springboot.entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PaymentEventAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentEventAspect.class);

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Pointcut("execution(public com.example.springboot.entity.Payment " +
            "com.example.springboot.dao.PaymentDao.addPayment(com.example.springboot.entity.Payment))")
    public void sendPaymentEvent() {}

    @AfterReturning("sendPaymentEvent()")
    public void sendPaymentEventAdvice(JoinPoint joinPoint) throws JsonProcessingException {
        Payment payment = (Payment) joinPoint.getArgs()[0];
        LOGGER.info("Sending payment created event to event store: {}", objectMapper.writeValueAsString(payment));
    }
}
