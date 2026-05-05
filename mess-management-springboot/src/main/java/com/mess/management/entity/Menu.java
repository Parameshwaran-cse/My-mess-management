package com.mess.management.entity;

import com.mess.management.enums.MealType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a daily mess menu for a specific meal type.
 * Each menu has a list of food items and optional alternates.
 *
 * JPA-ready: maps to 'menus' table in MySQL.
 */
@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @ElementCollection
    @CollectionTable(name = "menu_items", joinColumns = @JoinColumn(name = "menu_id"))
    @Builder.Default
    private List<MenuItem> items = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "menu_alternates", joinColumns = @JoinColumn(name = "menu_id"))
    @Column(name = "alternate")
    @Builder.Default
    private List<String> alternates = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate date;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (date == null) {
            date = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
