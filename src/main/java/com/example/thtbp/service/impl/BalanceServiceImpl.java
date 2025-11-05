package com.example.thtbp.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thtbp.entity.Balance;
import com.example.thtbp.entity.User;
import com.example.thtbp.entity.Transaction;
import com.example.thtbp.repository.BalanceRepository;
import com.example.thtbp.repository.TransactionRepository;
import com.example.thtbp.repository.UserRepository;
import com.example.thtbp.service.BalanceService;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired private UserRepository userRepository;
    @Autowired private BalanceRepository balanceRepository;
    @Autowired private TransactionRepository transactionRepository;

    @Override
    public Map<String, Object> getBalance(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User tidak ditemukan");
        }

        Balance balance = balanceRepository.findByUserId(user.getUserId());
        if (balance == null) {
            balance = new Balance();
            balance.setUser(user);
            balance.setBalance(0.0);
            balanceRepository.save(balance);
        }

        return Map.of(
                "status", 0,
                "message", "Get Balance Berhasil",
                "data", Map.of("balance", balance.getBalance())
        );
    }

    @Override
    public Map<String, Object> topUp(String email, Double amount) {
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Jumlah top up tidak valid");
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User tidak ditemukan");
        }

        Balance balance = balanceRepository.findByUserId(user.getUserId());
        if (balance == null) {
            balance = new Balance();
            balance.setUser(user);
            balance.setBalance(0.0);
        }

        balance.setBalance(balance.getBalance() + amount);
        balanceRepository.save(balance);

        // simpan transaksi top up
        Transaction trx = new Transaction();
        trx.setUser(user);
        trx.setTransaction_type("TOPUP");
        trx.setDescription("Top Up balance");
        trx.setTotal_amount(amount);
        trx.setCreated_on(java.time.LocalDateTime.now());
        trx.setInvoice_number("INV" + System.currentTimeMillis());
        transactionRepository.save(trx);

        return Map.of(
                "status", 0,
                "message", "Top Up Balance berhasil",
                "data", Map.of("balance", balance.getBalance())
        );
    }
}
