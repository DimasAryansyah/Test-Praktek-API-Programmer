package com.example.thtbp.service;

import com.example.thtbp.DTO.request.LoginRequest;
import com.example.thtbp.DTO.request.RegistrationRequest;
import com.example.thtbp.DTO.response.BaseResponse;
import com.example.thtbp.DTO.response.LoginResponse;

public interface AuthService {
    BaseResponse<?> register(RegistrationRequest request);
    BaseResponse<LoginResponse> login(LoginRequest request);
}
