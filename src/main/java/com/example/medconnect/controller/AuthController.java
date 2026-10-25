package com.example.medconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medconnect.dto.AuthResponse;
import com.example.medconnect.dto.LoginRequest;
import com.example.medconnect.dto.RegisterRequest;
import com.example.medconnect.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	 @Autowired
	    private AuthService authService;

	    @PostMapping("/register")
	    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
	        return ResponseEntity.ok(authService.register(request));
	    }

	    @PostMapping("/login")
	    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
	        return ResponseEntity.ok(authService.login(request));
	    }
}
