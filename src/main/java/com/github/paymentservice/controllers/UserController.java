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
        if (payload.getFirstname() == null || payload.getLastname() == null || payload.getAccounts() == null) {
            return ResponseEntity.badRequest().body("Not enough user data");
        }
        if (payload.getAccounts().isEmpty()) {
            return ResponseEntity.badRequest().body("Not enough account data");
        }

        return ResponseEntity.ok(userService.createUser(payload));
    }

    @PostMapping("/accounts")
    public ResponseEntity<Object> getAccounts(@RequestBody User user) {
        if (user.getClientId() <= 0) {
            return ResponseEntity.badRequest().body("Wrong id");
        }
        return ResponseEntity.ok(userService.getAccounts(user.getClientId()));

    }
}
