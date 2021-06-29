package com.github.paymentservice.services;

import com.github.paymentservice.dto.JournalResponseDto;
import com.github.paymentservice.dto.PaymentDto;
import com.github.paymentservice.dto.PaymentJournalDto;
import com.github.paymentservice.dto.PaymentResult;
import com.github.paymentservice.entity.Account;
import com.github.paymentservice.entity.Payment;
import com.github.paymentservice.entity.User;
import com.github.paymentservice.exceptions.BadRequest;
import com.github.paymentservice.exceptions.PaymentException;
import com.github.paymentservice.repository.AccountRepo;
import com.github.paymentservice.repository.PaymentRepo;
import com.github.paymentservice.repository.UserRepo;
import com.github.paymentservice.utils.PaymentStatus;
import com.github.paymentservice.utils.TransferObj;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;

    private final AccountRepo accountRepo;

    private final UserRepo userRepo;

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
        if (paymentDtoList.size() == 1) {
            return ResponseEntity.ok(res.get(0));
        }
        return ResponseEntity.ok(res);
    }

    @Transactional
    public Long processingPayment(PaymentDto payload) {
        Payment payment = TransferObj.toPayment(payload);
        insert(payment);
        List<Account> accounts = getAccounts(payload);
        Map<Long, Account> map = convertAccountId(accounts);
        validateFoundAccounts(payment, map);
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

    public void validateFoundAccounts(Payment payment, Map<Long, Account> accounts) {
        if (accounts.size() != 2) {
            throw new PaymentException("Not found account", payment.getPaymentId());
        }
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
        dest.setBalance(dest.getBalance().add(payload.getAmount()));
    }


    public List<JournalResponseDto> getPaymentJournal(PaymentJournalDto payload) {
        List<Payment> paymentJournalSearch = paymentRepo.getPaymentJournal(payload.getSourceAccId(), payload.getDestAccId(), payload.getPayerId(), payload.getReceiverId());
//        return paymentRepo.getPaymentJournal(payload.getSourceAccId(), payload.getDestAccId(), payload.getPayerId(), payload.getReceiverId());
        return convertEntityToDto(paymentJournalSearch);
    }

    public List<JournalResponseDto> convertEntityToDto(List<Payment> paymentList) {
        List<JournalResponseDto> journalResponseDtoList = new ArrayList<>();
        Map<Long, JournalResponseDto.UserJournal> relationUserByAccId = new HashMap<>();
        Map<Long, Long> relationAccIdByNum = new HashMap<>();
        for (Payment payment : paymentList) {
            JournalResponseDto journalResponseDto = new JournalResponseDto();
            journalResponseDto.setPaymentId(payment.getPaymentId());
            journalResponseDto.setAmount(payment.getAmount());
            journalResponseDto.setTimestamp(payment.getTimestamp());
            journalResponseDto.setDestAccNum(getAccountNumById(payment.getDestAccId(), relationAccIdByNum));
            journalResponseDto.setSourceAccNum(getAccountNumById(payment.getSourceAccId(), relationAccIdByNum));
            JournalResponseDto.UserJournal payer = getUserByAccount(payment.getSourceAccId(), relationUserByAccId);
            JournalResponseDto.UserJournal receiver = getUserByAccount(payment.getDestAccId(), relationUserByAccId);
            journalResponseDto.setPayer(payer);
            journalResponseDto.setReceiver(receiver);
            journalResponseDtoList.add(journalResponseDto);
        }
        return journalResponseDtoList;
    }

    //Если мы находили такого юзера, чтобы еще раз не ходить в базу юзаем это
    public JournalResponseDto.UserJournal getUserByAccount(Long accountId, Map<Long, JournalResponseDto.UserJournal> userByAccId) {
        if (Objects.nonNull(userByAccId.get(accountId)))
            return userByAccId.get(accountId);
        User user = userRepo.getUserById(accountId);
        JournalResponseDto.UserJournal userJournal = new JournalResponseDto.UserJournal(user.getFirstname(), user.getLastname());
        userByAccId.put(accountId, userJournal);
        return userJournal;
    }


    //Если нашли такой счет, то второй раз не будем искать
    public long getAccountNumById(long accountId, Map<Long, Long> accountIdNum) {
        if (Objects.nonNull(accountIdNum.get(accountId)))
            return accountIdNum.get(accountId);
        Account account = accountRepo.getById(accountId);
        accountIdNum.put(accountId, account.getAccountNum());
        return account.getAccountNum();
    }

}
