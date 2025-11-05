package com.example.thtbp.service.impl;

import com.example.thtbp.DTO.request.LoginRequest;
import com.example.thtbp.DTO.request.RegistrationRequest;
import com.example.thtbp.DTO.response.BaseResponse;
import com.example.thtbp.DTO.response.LoginResponse;
import com.example.thtbp.entity.Balance;
import com.example.thtbp.entity.User;
import com.example.thtbp.repository.BalanceRepository;
import com.example.thtbp.repository.UserRepository;
import com.example.thtbp.service.AuthService;
import com.example.thtbp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public BaseResponse<?> register(RegistrationRequest request) {
        if (request.getEmail() == null || !EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            return new BaseResponse<>(1, "Format email tidak valid", null);
        }
        if (request.getPassword() == null || request.getPassword().length() < 8) {
            return new BaseResponse<>(1, "Password minimal 8 karakter", null);
        }

        if (userRepository.findByEmail(request.getEmail()) != null) {
            return new BaseResponse<>(1, "Email sudah terdaftar", null);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirst_name(request.getFirst_name());
        user.setLast_name(request.getLast_name());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // Buat balance awal
        Balance balance = new Balance();
        balance.setUser(user);
        balance.setBalance(0.0);
        balanceRepository.save(balance);

        return new BaseResponse<>(0, "Registrasi berhasil silahkan login", null);
    }

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest request) {
        if (request.getEmail() == null || !EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            return new BaseResponse<>(1, "Format email tidak valid", null);
        }
        if (request.getPassword() == null || request.getPassword().length() < 8) {
            return new BaseResponse<>(1, "Password minimal 8 karakter", null);
        }

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return new BaseResponse<>(1, "Email tidak terdaftar", null);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new BaseResponse<>(1, "Password salah", null);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new BaseResponse<>(0, "Login Sukses", new LoginResponse(token));
    }
}
