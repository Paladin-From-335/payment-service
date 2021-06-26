package com.github.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegDto {

    @NotNull
    @NotEmpty
    private long accountNum;

    @NotNull
    @NotEmpty
    private String accountType;

    @NotNull
    @NotEmpty
    private BigDecimal balance;
}
