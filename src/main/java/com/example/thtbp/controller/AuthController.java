package com.example.thtbp.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.thtbp.entity.User;
import com.example.thtbp.repository.UserRepository;
import com.example.thtbp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/registration")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

    if (userRepository.existsByEmail(user.getEmail())) {
        response.put("status", 102);
        response.put("message", "Email sudah terdaftar");
        response.put("data", null);
        return response;
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);

    response.put("status", 0);
    response.put("message", "Registrasi berhasil silahkan login");
    response.put("data", null);

    return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User req) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByEmail(req.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            response.put("status", 103);
            response.put("message", "Email atau password salah");
            response.put("data", null);
            return response;
        }

        String token = jwtUtil.generateToken(user.getEmail());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);

        response.put("status", 0);
        response.put("message", "Login berhasil");
        response.put("data", data);

        return response;
    }

}
