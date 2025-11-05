package com.example.thtbp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thtbp.entity.Balance;
import com.example.thtbp.entity.ServiceEntity;
import com.example.thtbp.entity.Transaction;
import com.example.thtbp.entity.User;
import com.example.thtbp.DTO.request.TransactionRequest;
import com.example.thtbp.repository.BalanceRepository;
import com.example.thtbp.repository.ServiceRepository;
import com.example.thtbp.repository.TransactionRepository;
import com.example.thtbp.repository.UserRepository;
import com.example.thtbp.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired private UserRepository userRepository;
    @Autowired private BalanceRepository balanceRepository;
    @Autowired private ServiceRepository serviceRepository;
    @Autowired private TransactionRepository transactionRepository;

    @Override
    public Map<String, Object> createTransaction(String email, TransactionRequest request) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User tidak ditemukan");
        }

        Balance balance = balanceRepository.findByUserId(user.getUserId());
        if (balance == null || balance.getBalance() < request.getTotal_amount()) {
            throw new RuntimeException("Saldo tidak cukup");
        }

        ServiceEntity service = serviceRepository.findByServiceCode(request.getService_code());
        if (service == null) {
            throw new RuntimeException("Service tidak ditemukan");
        }

        // kurangi saldo
        balance.setBalance(balance.getBalance() - request.getTotal_amount());
        balanceRepository.save(balance);

        // simpan transaksi
        Transaction trx = new Transaction();
        trx.setUser(user);
        trx.setTransaction_type("PAYMENT");
        trx.setDescription(service.getService_name());
        trx.setTotal_amount(request.getTotal_amount());
        trx.setInvoice_number("INV" + System.currentTimeMillis());
        trx.setCreated_on(LocalDateTime.now());
        transactionRepository.save(trx);

        return Map.of(
                "status", 0,
                "message", "Transaksi berhasil",
                "data", Map.of(
                        "invoice_number", trx.getInvoice_number(),
                        "service_code", service.getService_code(),
                        "service_name", service.getService_name(),
                        "transaction_type", trx.getTransaction_type(),
                        "total_amount", trx.getTotal_amount(),
                        "created_on", trx.getCreated_on()
                )
        );
    }

    @Override
    public Map<String, Object> getHistory(String email, Integer limit, Integer offset) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User tidak ditemukan");
        }

        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getUserId());

        int fromIndex = offset != null ? offset : 0;
        int toIndex = (limit != null) ? Math.min(fromIndex + limit, transactions.size()) : transactions.size();
        List<Transaction> paged = transactions.subList(fromIndex, toIndex);

        List<Map<String, Object>> records = paged.stream().map(trx -> {
            Map<String, Object> map = new java.util.HashMap<>();
            map.put("invoice_number", trx.getInvoice_number());
            map.put("transaction_type", trx.getTransaction_type());
            map.put("description", trx.getDescription());
            map.put("total_amount", trx.getTotal_amount());
            map.put("created_on", trx.getCreated_on());
            return map;
        }).toList();


        return Map.of(
                "status", 0,
                "message", "Get History Berhasil",
                "data", Map.of(
                        "offset", fromIndex,
                        "limit", limit != null ? limit : records.size(),
                        "records", records
                )
        );
    }
}
