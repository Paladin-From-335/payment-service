package com.github.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String code;

    private String text;

    public static ResponseEntity<Object> errorResponse(String code, String text, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(code, text), status);
    }
}