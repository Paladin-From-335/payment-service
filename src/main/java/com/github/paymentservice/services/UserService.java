package com.github.paymentservice.services;

import com.github.paymentservice.dto.UserRegDto;
import com.github.paymentservice.entity.Account;
import com.github.paymentservice.entity.User;
import com.github.paymentservice.repository.UserRepo;
import com.github.paymentservice.utils.Converter;
import com.github.paymentservice.utils.TransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final AccountService accountService;

    private final Converter converter;

    public void insert(User user) {
        userRepo.save(user);
    }

    @Transactional
    public Long clientId(UserRegDto payload) {
        User user = TransferObj.toUser(payload);
        insert(user);
        accountService.insertClientAccounts(converter.convertDtoToEntity(payload.getAccounts(), user.getClientId()));
        return user.getClientId();
    }
    public List<Account> accounts(Long clientId) {
        return accountService.findAllAccountsByClientId(clientId);
    }
}
