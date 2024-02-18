package com.example.springboot.service;

import com.example.springboot.api.PaymentRequest;
import com.example.springboot.dao.PaymentDao;
import com.example.springboot.entity.Payment;
import com.example.springboot.model.ChargeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentService {

    private final PaymentDao paymentDao;

    @Autowired
    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public List<Payment> getCars(int limit, Integer afterId) {
        return paymentDao.getCars(limit, afterId);
    }

    public Payment createPayment(PaymentRequest paymentRequest) {
        return paymentDao.addPayment(Payment.fromPaymentRequest(paymentRequest, ChargeStatus.CREATED));
    }
}
