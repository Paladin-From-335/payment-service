package com.github.paymentservice.utils;

import com.github.paymentservice.exceptions.BadRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomValidator {

    @Resource
    protected javax.validation.Validator validator;

    public <T> void validate(T object) throws BadRequest {
        if (!validator.validate(object).isEmpty()) {
            throw new BadRequest("Invalid data");
        }
    }
}
