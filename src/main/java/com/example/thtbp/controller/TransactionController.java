package com.example.thtbp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.thtbp.DTO.request.TransactionRequest;
import com.example.thtbp.service.TransactionService;
import com.example.thtbp.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private TransactionService transactionService;

    @PostMapping("/transaction")
    public Map<String, Object> createTransaction(
            @RequestBody TransactionRequest request,
            HttpServletRequest httpRequest) {

        String token = httpRequest.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);

        return transactionService.createTransaction(email, request);
    }

    @GetMapping("/transaction/history")
    public Map<String, Object> getHistory(
        @RequestParam(required = false) Integer limit,
        @RequestParam(required = false) Integer offset,
        HttpServletRequest request) {

    String token = request.getHeader("Authorization").substring(7);
    String email = jwtUtil.extractEmail(token);

    return transactionService.getHistory(email, limit, offset);
    }
}