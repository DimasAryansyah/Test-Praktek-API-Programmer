package com.example.thtbp.controller;

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
        if (userRepository.existsByEmail(user.getEmail())) {
            return Map.of("status", 102, "message", "Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return Map.of("status", 0, "message", "Registration success", "data", user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User req) {
        User user = userRepository.findByEmail(req.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return Map.of("status", 103, "message", "Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return Map.of("status", 0, "message", "Login success", "data", Map.of("token", token));
    }
}
