package com.github.paymentservice.repository;

import com.github.paymentservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM account_table WHERE account_table.client_id = :clientId", nativeQuery = true)
    List<Account> findAllByClientId(long clientId);
}

