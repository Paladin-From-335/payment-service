package com.github.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    @NotNull
    @NotEmpty
    private BigDecimal amount;

    @NotNull
    @NotEmpty
    private String reason;

    @NotNull
    @NotEmpty
    private Date timestamp = new Date();

    @NotNull
    @NotEmpty
    @JsonProperty("source_acc_id")
    private Long sourceAccId;

    @NotNull
    @NotEmpty
    @JsonProperty("dest_acc_id")
    private Long destAccId;

    public PaymentDto(BigDecimal amount, String reason, Long sourceAccId, Long destAccId) {
        this.amount = amount;
        this.reason = reason;
        this.sourceAccId = sourceAccId;
        this.destAccId = destAccId;
    }
}
