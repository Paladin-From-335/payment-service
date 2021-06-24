package com.github.paymentservice.exceptions;

public class PaymentException extends RuntimeException{

    private String message;

    private Long paymentId;

    public PaymentException(String message, Long paymentId) {
        this.message = message;
        this.paymentId = paymentId;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
