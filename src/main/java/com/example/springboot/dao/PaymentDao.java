package com.example.springboot.dao;

import com.example.springboot.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentDao {

    private static final List<Payment> PAYMENTS = new ArrayList<>();

    public List<Payment> getCars(int limit, Integer afterId) {
        return PAYMENTS.subList(afterId, afterId + limit);
    }

    public Payment addPayment(Payment payment) {
        PAYMENTS.add(payment);
        return payment;
    }
}
