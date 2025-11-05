package com.example.thtbp.service;

import com.example.thtbp.DTO.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileResponse getProfile(String email);
    ProfileResponse updateProfile(String email, String firstName, String lastName);
    ProfileResponse updateProfileImage(String email, MultipartFile file);
}
