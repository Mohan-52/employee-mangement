package com.rajesh.employee_mangement.controller;

import com.rajesh.employee_mangement.dto.AuthResponseDTO;
import com.rajesh.employee_mangement.dto.UserLoginDTO;
import com.rajesh.employee_mangement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginRequest) {
        try {
            // Authenticate the credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Generate JWT token
            String token = jwtUtil.generateToken(loginRequest.getUsername());

            return ResponseEntity.ok(new AuthResponseDTO(token, "Login successful"));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new AuthResponseDTO(null, "Invalid credentials"));
        }
    }
}
