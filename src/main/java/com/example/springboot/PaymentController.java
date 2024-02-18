package com.example.springboot;

import com.example.springboot.api.PaymentRequest;
import com.example.springboot.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static java.lang.String.format;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping(value = "/payments", produces = "application/json")
    public GetPaymentsResponse getPayments(@RequestParam(required = false, defaultValue = "5") Integer limit,
                                           @RequestParam(required = false, name = "after_id", defaultValue = "0") Integer afterId) {
        return new GetPaymentsResponse(paymentService.getCars(limit, afterId), limit, afterId);
    }

    @PostMapping(value = "/payment", consumes = "application/json")
    public ResponseEntity createPayment(@RequestBody PaymentRequest paymentRequest) {
        var payment = paymentService.createPayment(paymentRequest);
        return ResponseEntity.created(URI.create(format("/payment?id=%s", payment.getId()))).build();
    }
}
