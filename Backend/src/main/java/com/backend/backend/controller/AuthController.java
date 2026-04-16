package com.backend.backend.controller;

import com.backend.backend.dto.AuthResponseDto;
import com.backend.backend.dto.LoginDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        boolean success = authService.registerUser(userDto);
        if (!success) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginReq) {
        AuthResponseDto result = authService.loginUser(loginReq);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
