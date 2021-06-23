package com.github.paymentservice.controllers;

import com.github.paymentservice.dto.UserRegDto;
import com.github.paymentservice.entity.User;
import com.github.paymentservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@RequestBody UserRegDto payload) {
        return ResponseEntity.ok(userService.clientId(payload));
    }
    @PostMapping("/accounts")
    public ResponseEntity<Object> getAccounts(@RequestBody User user) {
        return ResponseEntity.ok(userService.accounts(user.getClientId()));

    }
}
