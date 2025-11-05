package com.example.thtbp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.thtbp.DTO.request.TopupRequest;
import com.example.thtbp.service.BalanceService;
import com.example.thtbp.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class TopupController {

    @Autowired private BalanceService balanceService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/topup")
    public Map<String, Object> topUp(@RequestBody TopupRequest request, HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Token tidak ditemukan atau invalid");
        }

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        return balanceService.topUp(email, request.getTop_up_amount());
    }
}
