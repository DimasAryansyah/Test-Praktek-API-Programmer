package com.example.thtbp.service;

import java.util.Map;

public interface BalanceService {
    Map<String, Object> getBalance(String email);
    Map<String, Object> topUp(String email, Double amount);
}
