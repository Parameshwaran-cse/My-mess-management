package com.mess.management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * DTO for submitting meal feedback.
 */
@Data
public class FeedbackRequest {

    /**
     * Ratings per meal type.
     * Key: meal type string (e.g., "BREAKFAST"), Value: rating 1–5
     */
    @NotNull(message = "Ratings map is required")
    private Map<String, Integer> ratings;

    /**
     * Alternate / suggestion text per meal type.
     * Key: meal type string (e.g., "LUNCH"), Value: suggestion text
     */
    private Map<String, String> alternates;

    /** Optional general comment */
    private String comment;
}
