package com.github.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountReadDto {

    private long accountId;

    private long accountNum;

    private String accountType;

    private BigDecimal balance;
}
