package com.github.paymentservice;

import com.github.paymentservice.dto.PaymentDto;
import com.github.paymentservice.entity.Account;
import com.github.paymentservice.entity.Payment;
import com.github.paymentservice.exceptions.BadRequest;
import com.github.paymentservice.exceptions.PaymentException;
import com.github.paymentservice.repository.AccountRepo;
import com.github.paymentservice.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private AccountRepo accountRepo;

    @Test
    void convertAccountId() {
        List<Account> accountList = List.of(new Account(1,123L, 123, "", new BigDecimal(100)),
                new Account(2,123L, 789, "", new BigDecimal(1000)));
        Map<Long, Account> map = paymentService.convertAccountId(accountList);

        assertAll(
                () -> assertEquals(2, map.size()),
                () -> assertNotNull(map.get((long)1)),
                () -> assertNotNull(map.get((long)2))
        );
    }

    @Test
    void checkPayerBalanceException() {
        Map<Long, Account> map = getAccountMap();
        assertThrows(BadRequest.class, () -> paymentService.checkPayerBalance(map,  new BigDecimal(200), 1L));
    }

    @Test
    void checkPayerBalanceEqualsSum() {
        Map<Long, Account> map = getAccountMap();
        assertDoesNotThrow(() -> paymentService.checkPayerBalance(map,  new BigDecimal(100), 1L));
    }

    @Test
    void checkPayerBalanceLessSum() {
        Map<Long, Account> map = getAccountMap();
        assertDoesNotThrow(() -> paymentService.checkPayerBalance(map,  new BigDecimal(99), 1L));
    }

    @Test
    void updateBalance() {
        Map<Long, Account> map = getAccountMap();
        PaymentDto payload = new PaymentDto(new BigDecimal(30), "За обучение", 1L, 2L);

        paymentService.updateBalance(map, payload);

        assertAll(
                () -> assertEquals(new BigDecimal(70), map.get((long)1).getBalance()),
                () -> assertEquals(new BigDecimal(1030), map.get((long)2).getBalance())
        );
    }

    @Test
    void accountCountLess2(){
        Map<Long, Account> map = Map.of(1L , new Account(1,123L, 123, "", new BigDecimal(100)));
        assertThrows(PaymentException.class, () -> paymentService.validateFoundAccounts(new Payment(), map));
    }

    @Test
    void accountCountEquals2(){
        Map<Long, Account> map = getAccountMap();
        assertDoesNotThrow(() -> paymentService.validateFoundAccounts(new Payment(), map));
    }

    @Test
    void getAccountList(){
        List<Account> returnAccountList = List.of(new Account(1,123L, 123, "", new BigDecimal(100)),
                new Account(2,123L, 789, "", new BigDecimal(1000)));
        PaymentDto paymentDto = new PaymentDto(new BigDecimal(100), "За обучение", 1L, 2L);
        Mockito.doReturn(returnAccountList).when(accountRepo).getAccListById(anyList());

        List<Account> result = paymentService.getAccounts(paymentDto);
        assertAll(
                () ->  assertArrayEquals(result.toArray(), returnAccountList.toArray()),
                () -> verify(accountRepo, times(1)).getAccListById(anyList())
        );
    }

    private Map<Long, Account> getAccountMap() {
        return Map.of(1L, new Account(1, 123L, 123, "", new BigDecimal(100)),
                2L, new Account(2, 123L, 789, "", new BigDecimal(1000)));
    }
}