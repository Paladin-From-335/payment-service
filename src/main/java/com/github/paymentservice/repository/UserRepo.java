package com.github.paymentservice.repository;

import com.github.paymentservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user_table inner join account_table a on user_table.client_id = a.client_id\n" +
            "where a.account_id =:account_id", nativeQuery = true)
    User getUserById(@Param("account_id") long accountId);
}
