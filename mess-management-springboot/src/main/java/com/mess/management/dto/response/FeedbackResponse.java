package com.mess.management.dto.response;

import com.mess.management.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Response DTO for feedback data returned to clients.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Long id;
    private Long userId;
    private String username;
    private Map<String, Integer> ratings;
    private Map<String, String> alternates;
    private String comment;
    private LocalDateTime createdAt;

    /**
     * Convenience factory method to map from a Feedback entity.
     */
    public static FeedbackResponse from(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .userId(feedback.getUserId())
                .username(feedback.getUsername())
                .ratings(feedback.getRatings())
                .alternates(feedback.getAlternates())
                .comment(feedback.getComment())
                .createdAt(feedback.getCreatedAt())
                .build();
    }
}
