package com.github.paymentservice.controllers;

import com.github.paymentservice.dto.UserRegDto;
import com.github.paymentservice.entity.User;
import com.github.paymentservice.exceptions.BadRequest;
import com.github.paymentservice.services.UserService;
import com.github.paymentservice.utils.TransferObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public HttpStatus registration(@RequestBody @Valid UserRegDto payload) {
        if (payload.getFirstname() == null || payload.getLastname() == null) {
            throw new BadRequest("Invalid data");
        }
        User user = TransferObj.toUser(payload);
        userService.insert(user);
        return HttpStatus.CREATED;
    }
}
