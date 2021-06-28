package com.github.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalResponseDto {

    @JsonProperty("payment_id")
    private long paymentId;

    private Date timestamp;

    @JsonProperty("source_acc_num")
    private long sourceAccNum;

    @JsonProperty("dest_acc_num")
    private long destAccNum;

    private BigDecimal amount;

    private UserJournal payer;

    private UserJournal receiver;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class UserJournal {

        private String firstname;

        private String lastname;
    }
}
