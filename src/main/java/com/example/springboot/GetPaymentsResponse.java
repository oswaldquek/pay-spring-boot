package com.example.springboot;

import com.example.springboot.api.Pagination;
import com.example.springboot.entity.Payment;

import java.util.List;

public class GetPaymentsResponse {
    private final List<Payment> results;

    private final Pagination pagination;

    public GetPaymentsResponse(List<Payment> allPayments, Integer limit, Integer afterId) {
        this.results = allPayments;
        this.pagination = new Pagination(limit, afterId);
    }

    public List<Payment> getResults() {
        return results;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
