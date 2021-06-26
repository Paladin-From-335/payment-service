package com.github.paymentservice.services;

import com.github.paymentservice.dto.PaymentDto;
import com.github.paymentservice.dto.PaymentResult;
import com.github.paymentservice.entity.Account;
import com.github.paymentservice.entity.Payment;
import com.github.paymentservice.exceptions.BadRequest;
import com.github.paymentservice.exceptions.PaymentException;
import com.github.paymentservice.repository.AccountRepo;
import com.github.paymentservice.repository.PaymentRepo;
import com.github.paymentservice.utils.PaymentStatus;
import com.github.paymentservice.utils.TransferObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;

    private final AccountRepo accountRepo;

    @Autowired
    public PaymentService(PaymentRepo paymentRepo, AccountRepo accountRepo) {
        this.paymentRepo = paymentRepo;
        this.accountRepo = accountRepo;
    }

    public void insert(Payment payment) {
        paymentRepo.save(payment);
    }

    public ResponseEntity<Object> responseEntity(List<PaymentDto> paymentDtoList) {
        List<PaymentResult> res = new ArrayList<>();
        for (PaymentDto paymentDto : paymentDtoList) {
            try {
                Long paymentId = processingPayment(paymentDto);
                PaymentResult result = new PaymentResult(paymentId, "OK");
                res.add(result);
            } catch (PaymentException e) {
                PaymentResult result = new PaymentResult(e.getPaymentId(), "ERROR");
                res.add(result);
            }
        }
        if(paymentDtoList.size() == 1) {
            return ResponseEntity.ok(res.get(0));
        }
        return ResponseEntity.ok(res);
    }

    @Transactional
    public Long processingPayment(PaymentDto payload) {
        Payment payment = TransferObj.toPayment(payload);
        insert(payment);
        if (paymentRepo.findAccountById(payload.getSourceAccId()) == null || paymentRepo.findAccountById(payload.getDestAccId()) == null) {
            throw new PaymentException("Wrong sender or receiver", payment.getPaymentId());
        }
        List<Account> accounts = getAccounts(payload);
        if (accounts.size() != 2) {
            throw new PaymentException("Not found account", payment.getPaymentId());
        }
        Map<Long, Account> map = convertAccountId(accounts);
        checkPayerBalance(map, payload.getAmount(), payload.getSourceAccId());
        updateBalance(map, payload);
        accountRepo.saveAll(accounts);
        payment.setStatus(PaymentStatus.OK);
        paymentRepo.save(payment);
        return payment.getPaymentId();
    }

    public Map<Long, Account> convertAccountId(List<Account> accounts) {
        return accounts.stream().collect(Collectors.toMap(Account::getAccountId, Function.identity()));
    }

    public void checkPayerBalance(Map<Long, Account> accounts, BigDecimal sum, Long payerId) {
        Account payerAccount = accounts.get(payerId);
        if (payerAccount.getBalance().compareTo(sum) < 0) {
            throw new BadRequest("Balance too low");
        }
    }

    public List<Account> getAccounts(PaymentDto payload) {
        List<Long> ids = new ArrayList();
        ids.add(payload.getSourceAccId());
        ids.add(payload.getDestAccId());
        return accountRepo.getAccListById(ids);
    }

    public void insertClientAccounts(List<Payment> paymentList) {
        paymentRepo.saveAll(paymentList);
    }

    public void updateBalance(Map<Long, Account> account, PaymentDto payload) {
        Account payer = account.get(payload.getSourceAccId());
        Account dest = account.get(payload.getDestAccId());
        payer.setBalance(payer.getBalance().subtract(payload.getAmount()));
        dest.setBalance(payer.getBalance().add(payload.getAmount()));
    }


    public List<Payment> getPaymentJournal(PaymentDto payload) {
        return paymentRepo.getPaymentJournal(payload.getSourceAccId());
    }
}
