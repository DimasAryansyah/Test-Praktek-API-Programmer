package com.example.thtbp.controller;

import com.example.thtbp.DTO.ProfileResponse;
import com.example.thtbp.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<?> getProfile(Principal principal) {
        ProfileResponse data = profileService.getProfile(principal.getName());
        Map<String, Object> response = new HashMap<>();
        response.put("status", 0);
        response.put("message", "Sukses");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(Principal principal, @RequestBody Map<String, String> request) {
        ProfileResponse data = profileService.updateProfile(
                principal.getName(),
                request.get("first_name"),
                request.get("last_name")
        );
        Map<String, Object> response = new HashMap<>();
        response.put("status", 0);
        response.put("message", "Update Profile berhasil");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/image")
    public ResponseEntity<?> updateProfileImage(Principal principal, @RequestParam("file") MultipartFile file) {
        ProfileResponse data = profileService.updateProfileImage(principal.getName(), file);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 0);
        response.put("message", "Update Profile Image berhasil");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
}
