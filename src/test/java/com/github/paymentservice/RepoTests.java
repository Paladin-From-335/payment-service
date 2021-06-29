package com.github.paymentservice;

import com.github.paymentservice.entity.User;
import com.github.paymentservice.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RepoTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    void a() {
        User user = userRepo.getUserById(34L);
        System.out.println(user == null);
    }

}
