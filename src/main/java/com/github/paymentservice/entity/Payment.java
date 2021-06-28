package com.github.paymentservice.entity;

import com.github.paymentservice.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, columnDefinition = "BIGINT", unique = true)
    private long paymentId;

    @Column(name = "amount", nullable = false, columnDefinition = "DECIMAL")
    private BigDecimal amount;

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private Date timestamp;

    //В итоге здесь лучше хранить объект, но я начал с АЙДИ и не стал переделывать, дабы не терять время
    //В будущем, наверное, лучше хранить объект сущности вместо АЙДИ
    @Column(name = "source_acc_id", nullable = false, columnDefinition = "BIGINT")
    private Long sourceAccId;

    @Column(name = "dest_acc_id", nullable = false, columnDefinition = "BIGINT")
    private Long destAccId;

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    private PaymentStatus status;

    public Payment(BigDecimal amount, String reason, Date timestamp, Long sourceAccId, Long destAccId) {
        this.amount = amount;
        this.reason = reason;
        this.timestamp = timestamp;
        this.sourceAccId = sourceAccId;
        this.destAccId = destAccId;
    }
}
