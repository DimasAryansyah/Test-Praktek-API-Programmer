package com.example.thtbp.repository;

import com.example.thtbp.entity.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
@Query(value = "SELECT * FROM transactions WHERE user_id = :userId ORDER BY created_on DESC", nativeQuery = true)
    List<Transaction> findAllByUserId(@Param("userId") Long userId);
    List<Transaction> findByUser_UserIdOrderByCreated_onDesc(Long userId);
}
