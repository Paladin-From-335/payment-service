package com.github.paymentservice.controllers;

import com.github.paymentservice.dto.ErrorResponse;
import com.github.paymentservice.exceptions.BadRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadRequest.class})
    public ResponseEntity<Object> handleLogicException(BadRequest e) {
        return ErrorResponse.errorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}