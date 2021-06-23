package com.github.paymentservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false, columnDefinition = "BIGINT", unique = true)
    private long accountId;

    @JoinColumn(name = "client_id")
    @JsonProperty("client_id")
    private Long clientId;

    @Column(name = "account_num", nullable = false, columnDefinition = "BIGINT")
    private long accountNum;

    @Column(name = "account_type", nullable = false, columnDefinition = "TEXT")
    private String accountType;

    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL")
    private BigDecimal balance;

    public Account(Long clientId,long accountNum, String accountType, BigDecimal balance) {
        this.clientId = clientId;
        this.accountNum = accountNum;
        this.accountType = accountType;
        this.balance = balance;
    }
}
