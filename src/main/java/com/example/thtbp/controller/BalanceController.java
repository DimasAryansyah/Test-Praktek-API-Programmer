package com.example.thtbp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.thtbp.service.BalanceService;
import com.example.thtbp.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class BalanceController {

    @Autowired private BalanceService balanceService;
    @Autowired private JwtUtil jwtUtil;

    @GetMapping("/balance")
    public Map<String, Object> getBalance(HttpServletRequest request) {
        // ambil header Authorization: Bearer <token>
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Token tidak ditemukan atau invalid");
        }

        String token = header.substring(7); // potong "Bearer "
        String email = jwtUtil.extractEmail(token); // ambil email dari JWT payload

        return balanceService.getBalance(email);
    }
}
