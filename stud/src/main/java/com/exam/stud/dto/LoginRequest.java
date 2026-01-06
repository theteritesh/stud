package com.exam.stud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
	@Schema(description = "User's email address", example = "student@test.com")
    private String email;
	
	@Schema(description = "User's password", example = "password123")
    private String password;
}