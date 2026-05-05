package com.mess.management.service;

import com.mess.management.dto.request.FeedbackRequest;
import com.mess.management.dto.response.FeedbackResponse;
import com.mess.management.entity.Feedback;
import com.mess.management.exception.ResourceNotFoundException;
import com.mess.management.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Business logic for meal feedback submission and retrieval.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // ─── Submit Feedback ─────────────────────────────────────────────────────

    public FeedbackResponse submitFeedback(FeedbackRequest request, Long userId, String username) {
        // Validate rating values (must be 1–5)
        if (request.getRatings() != null) {
            request.getRatings().forEach((meal, rating) -> {
                if (rating < 1 || rating > 5) {
                    throw new com.mess.management.exception.BadRequestException(
                            "Rating for " + meal + " must be between 1 and 5.");
                }
            });
        }

        Feedback feedback = Feedback.builder()
                .userId(userId)
                .username(username)
                .ratings(request.getRatings())
                .alternates(request.getAlternates())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        Feedback saved = feedbackRepository.save(feedback);
        log.info("Feedback submitted by user: {}", username);
        return FeedbackResponse.from(saved);
    }

    // ─── My Feedback ─────────────────────────────────────────────────────────

    public List<FeedbackResponse> getMyFeedback(Long userId) {
        return feedbackRepository.findByUserId(userId).stream()
                .map(FeedbackResponse::from)
                .toList();
    }

    // ─── Admin: All Feedback ─────────────────────────────────────────────────

    public List<FeedbackResponse> getAllFeedback() {
        return feedbackRepository.findAll().stream()
                .map(FeedbackResponse::from)
                .toList();
    }

    // ─── Get by ID ───────────────────────────────────────────────────────────

    public FeedbackResponse getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", id));
        return FeedbackResponse.from(feedback);
    }
}
