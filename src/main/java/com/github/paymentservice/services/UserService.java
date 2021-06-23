package com.github.paymentservice.services;

import com.github.paymentservice.entity.User;
import com.github.paymentservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void insert(User user) {
        userRepo.save(user);
    }

}
