package com.exam.stud.controller;

import com.exam.stud.dto.JwtResponse;
import com.exam.stud.dto.LoginRequest;
import com.exam.stud.dto.RegisterRequest;
import com.exam.stud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest signUpRequest) {
        String message = authService.registerStudent(signUpRequest);
        return ResponseEntity.ok(message);
    }
}