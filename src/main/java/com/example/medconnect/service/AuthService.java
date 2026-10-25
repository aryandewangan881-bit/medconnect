package com.example.medconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.medconnect.dto.AuthResponse;
import com.example.medconnect.dto.LoginRequest;
import com.example.medconnect.dto.RegisterRequest;
import com.example.medconnect.entity.User;
import com.example.medconnect.repository.UserRepository;
import com.example.medconnect.security.JwtUtil;

@Service
public class AuthService {

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private AuthenticationManager authenticationManager;

	    public AuthResponse register(RegisterRequest request) {
	        // Check karo email already exist to nahi karta
	        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
	            throw new RuntimeException("Email already registered");
	        }

	        User user = new User();
	        user.setName(request.getName());
	        user.setEmail(request.getEmail());
	        user.setPassword(passwordEncoder.encode(request.getPassword())); // hash karke save
	        user.setPhone(request.getPhone());
	        user.setRole(User.Role.PATIENT); // default role patient hi hoga register se

	        userRepository.save(user);

	        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

	        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
	    }

	    public AuthResponse login(LoginRequest request) {
	        // Spring Security se credentials verify karwao
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
	        );

	        User user = userRepository.findByEmail(request.getEmail())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

	        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
	    }
	}


