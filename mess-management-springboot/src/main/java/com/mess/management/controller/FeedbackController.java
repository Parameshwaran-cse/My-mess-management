package com.mess.management.controller;

import com.mess.management.config.JwtUtil;
import com.mess.management.dto.request.FeedbackRequest;
import com.mess.management.dto.response.ApiResponse;
import com.mess.management.dto.response.FeedbackResponse;
import com.mess.management.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for meal feedback.
 * All endpoints require authentication.
 */
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final JwtUtil jwtUtil;

    /**
     * POST /api/feedback
     * Submit feedback for one or more meal types.
     *
     * Request body: { ratings: { "BREAKFAST": 4, "LUNCH": 3 }, alternates?, comment? }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FeedbackResponse>> submitFeedback(
            @Valid @RequestBody FeedbackRequest request,
            @RequestHeader("Authorization") String authHeader) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        String username = jwtUtil.extractUsername(authHeader.substring(7));
        FeedbackResponse feedback = feedbackService.submitFeedback(request, userId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Feedback submitted successfully.", feedback));
    }

    /**
     * GET /api/feedback/my
     * Returns all feedback submitted by the currently logged-in user.
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getMyFeedback(
            @RequestHeader("Authorization") String authHeader) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        return ResponseEntity.ok(ApiResponse.success(feedbackService.getMyFeedback(userId)));
    }
}
