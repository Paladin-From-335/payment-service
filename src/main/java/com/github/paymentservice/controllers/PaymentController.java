package com.github.paymentservice.controllers;

import com.github.paymentservice.dto.PaymentDto;
import com.github.paymentservice.dto.PaymentJournalDto;
import com.github.paymentservice.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<Object> createPayment(@RequestBody PaymentDto payload) {
        if (payload.getAmount() == null || payload.getReason() == null || payload.getDestAccId() <= 0 || payload.getSourceAccId() <= 0) {
            return ResponseEntity.badRequest().body("Wrong payment data");
        }
        return paymentService.responseEntity(List.of(payload));
    }

    @PostMapping("/paymentbatch")
    public ResponseEntity<Object> createPayment(@RequestBody List<PaymentDto> payload) {
        if (payload.isEmpty()) {
            return ResponseEntity.badRequest().body("Wrong payment data");
        }
        return paymentService.responseEntity(payload);
    }

    @PostMapping("/journal")
    public ResponseEntity<Object> getJournal(@RequestBody PaymentJournalDto payload) {
        if (payload.getPayerId() <= 0 || payload.getReceiverId() <= 0 || payload.getSourceAccId() <= 0 || payload.getDestAccId() <= 0) {
            return ResponseEntity.badRequest().body("Wrong journal request");
        }
        return ResponseEntity.ok(paymentService.getPaymentJournal(payload));
    }
}
