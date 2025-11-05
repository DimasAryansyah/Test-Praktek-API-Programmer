package com.example.thtbp.service;

import java.util.Map;
import com.example.thtbp.DTO.request.TransactionRequest;

public interface TransactionService {
    Map<String, Object> createTransaction(String email, TransactionRequest request);
    Map<String, Object> getHistory(String email, Integer limit, Integer offset);
}