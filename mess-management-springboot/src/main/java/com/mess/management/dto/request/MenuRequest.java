package com.mess.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for creating or updating a menu entry.
 */
@Data
public class MenuRequest {

    @NotBlank(message = "Meal type is required (BREAKFAST, LUNCH, or DINNER)")
    private String mealType;

    private List<MenuItemDto> items;

    private List<String> alternates;

    /** Defaults to today if not provided. */
    private LocalDate date;

    /**
     * Inner DTO for individual food items within a menu.
     */
    @Data
    public static class MenuItemDto {
        @NotBlank(message = "Item name is required")
        private String name;
        private boolean available = true;
    }
}
