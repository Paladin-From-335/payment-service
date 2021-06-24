package com.github.paymentservice.repository;

import com.github.paymentservice.entity.Account;
import com.github.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM account_table WHERE account_table.account_id in ?1", nativeQuery = true)
    List<Account> getAccListById(List<Long> accountId);
//
//    @Query(value = "SELECT client_id FROM account_table WHERE account_table.account_id =: dest_acc_id", nativeQuery = true)
//    Long findClientByDestAccId(long destAccId);

    @Query(value = "SELECT account_id from account_table WHERE account_table.account_id = :incomingId", nativeQuery = true)
    Long findAccountById(long incomingId);


}
