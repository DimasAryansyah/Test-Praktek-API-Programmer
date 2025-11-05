package com.example.thtbp.service;

import com.example.thtbp.DTO.ProfileResponse;
import com.example.thtbp.entity.User;
import com.example.thtbp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public ProfileResponse getProfile(String email) {
    User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User tidak ditemukan");
        }
        return new ProfileResponse(
                user.getEmail(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getProfile_image()
        );
    }

    @Override
    public ProfileResponse updateProfile(String email, String firstName, String lastName) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User tidak ditemukan");
        }
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        userRepository.save(user);

        return new ProfileResponse(
                user.getEmail(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getProfile_image()
        );
    }

    @Override
    public ProfileResponse updateProfileImage(String email, MultipartFile file) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new RuntimeException("User tidak ditemukan");
        }

        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/profile/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = email.replaceAll("@", "_") + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            file.transferTo(filePath.toFile());

            // Simulasi URL hasil upload
            String imageUrl = "https://test-praktek-api-programmer-production.up.railway.app" + fileName;

            user.setProfile_image(imageUrl);
            userRepository.save(user);

            return new ProfileResponse(
                    user.getEmail(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getProfile_image()
            );
        } catch (IOException e) {
            throw new RuntimeException("Gagal upload gambar: " + e.getMessage());
        }
    }
}