package com.github.paymentservice.repository;

import com.github.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM payment_table WHERE (:source_acc_id = 0 OR source_acc_id =:source_acc_id) AND\n" +
            "                                                (:dest_acc_id = 0 OR dest_acc_id =:dest_acc_id) AND\n" +
            "            (:payer_id = 0 OR source_acc_id in((SELECT account_id FROM payment_table inner join account_table act on payment_table.source_acc_id = act.account_id where act.client_id = :payer_id))) AND\n" +
            "            (:receiver_id = 0 OR dest_acc_id in((SELECT account_id FROM payment_table inner join account_table act on payment_table.dest_acc_id = act.account_id where act.client_id = :receiver_id)))", nativeQuery = true)
    List<Payment> getPaymentJournal(@Param("source_acc_id") long sourceAccId, @Param("dest_acc_id") long destAccId, @Param("payer_id") long payerId, @Param("receiver_id") long receiverId);

    @Query(value = "SELECT account_id from account_table WHERE account_table.account_id = :incomingId", nativeQuery = true)
    Long findAccountById(long incomingId);

}
