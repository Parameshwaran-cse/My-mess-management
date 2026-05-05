package com.mess.management.controller;

import com.mess.management.config.JwtUtil;
import com.mess.management.dto.request.UpdateUserRequest;
import com.mess.management.dto.response.ApiResponse;
import com.mess.management.dto.response.UserResponse;
import com.mess.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for user profile operations.
 * All endpoints require authentication (Bearer token).
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * GET /api/users/me
     * Returns the profile of the currently logged-in user.
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile(
            @RequestHeader("Authorization") String authHeader) {
        String username = extractUsername(authHeader);
        UserResponse user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * PUT /api/users/me
     * Update the current user's profile (fullName, email, phone, password).
     */
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMyProfile(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody UpdateUserRequest request) {
        Long userId = extractUserId(authHeader);
        UserResponse updated = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully.", updated));
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private String extractUsername(String authHeader) {
        return jwtUtil.extractUsername(authHeader.substring(7));
    }

    private Long extractUserId(String authHeader) {
        return jwtUtil.extractUserId(authHeader.substring(7));
    }
}
