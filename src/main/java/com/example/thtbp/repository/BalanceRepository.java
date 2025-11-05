package com.example.thtbp.repository;

import com.example.thtbp.entity.Balance;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query(value = "SELECT * FROM balances WHERE user_id = :userId", nativeQuery = true)
    Balance findByUserId(@Param("userId") Long userId);
}
