package com.github.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentJournalDto {


    private long payerId;

    private long recipientId;

    @JsonProperty("source_acc_id")
    private long sourceAccId;

    @JsonProperty("dest_acc_id")
    private long destAccId;


}
