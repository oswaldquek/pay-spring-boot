package com.example.springboot.entity;

import com.example.springboot.api.PaymentRequest;
import com.example.springboot.model.ChargeStatus;

import java.time.LocalDate;
import java.util.Random;

public class Payment {

    private Long id;

    private String reference;

    private LocalDate createdDate;

    private int amount;
    private ChargeStatus status;

    public Payment(Long id, String reference, LocalDate createdDate, int amount, ChargeStatus status) {
        this.id = id;
        this.reference = reference;
        this.createdDate = createdDate;
        this.amount = amount;
        this.status = status;
    }

    public static Payment fromPaymentRequest(PaymentRequest paymentRequest, ChargeStatus status) {
        return new Payment(new Random().nextLong(), paymentRequest.getReference(), LocalDate.now(), paymentRequest.getAmount(), status);
    }

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public ChargeStatus getStatus() {
        return status;
    }

    public void setStatus(ChargeStatus status) {
        this.status = status;
    }
}
