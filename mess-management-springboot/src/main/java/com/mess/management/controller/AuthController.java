package com.mess.management.controller;

import com.mess.management.dto.request.LoginRequest;
import com.mess.management.dto.request.RegisterRequest;
import com.mess.management.dto.response.ApiResponse;
import com.mess.management.dto.response.AuthResponse;
import com.mess.management.dto.response.UserResponse;
import com.mess.management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication (register and login).
 * All endpoints in this controller are PUBLIC — no token required.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/register
     * Register a new user account.
     *
     * Request body: { username, password, email, fullName, phone, role? }
     * Response:     201 Created + UserResponse
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully.", user));
    }

    /**
     * POST /api/auth/login
     * Login with credentials and receive a JWT token.
     *
     * Request body: { username, password }
     * Response:     200 OK + AuthResponse (token + user info)
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful.", response));
    }
}
