package com.exam.stud.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String role;
    private String userId;

    public JwtResponse(String accessToken, String email, String role, String userId) {
        this.token = accessToken;
        this.email = email;
        this.role = role;
        this.userId = userId;
    }
}