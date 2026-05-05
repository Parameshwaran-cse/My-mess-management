package com.mess.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores a student's feedback for a meal session.
 * Contains meal ratings (numeric) and alternate/suggestion text per meal type.
 *
 * JPA-ready: maps to 'feedback' table in MySQL.
 */
@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    /**
     * Ratings per meal type, e.g. {"BREAKFAST": 4, "LUNCH": 3}
     * Rating scale: 1 (terrible) to 5 (excellent)
     */
    @ElementCollection
    @CollectionTable(name = "feedback_ratings", joinColumns = @JoinColumn(name = "feedback_id"))
    @MapKeyColumn(name = "meal_type")
    @Column(name = "rating")
    @Builder.Default
    private Map<String, Integer> ratings = new HashMap<>();

    /**
     * Alternate suggestions per meal type, e.g. {"LUNCH": "Less oily food please"}
     */
    @ElementCollection
    @CollectionTable(name = "feedback_alternates", joinColumns = @JoinColumn(name = "feedback_id"))
    @MapKeyColumn(name = "meal_type")
    @Column(name = "suggestion")
    @Builder.Default
    private Map<String, String> alternates = new HashMap<>();

    @Column(length = 500)
    private String comment;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
