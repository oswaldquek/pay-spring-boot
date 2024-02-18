package com.example.springboot.api;

public class PaymentRequest {

    private String reference;

    private int amount;

    public PaymentRequest(String reference, int amount) {
        this.reference = reference;
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public int getAmount() {
        return amount;
    }
}
