package com.example.thtbp.DTO.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String email;
    private String first_name;
    private String last_name;
    private String password;
}
