package com.mess.management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for booking a meal from a specific menu.
 */
@Data
public class BookingRequest {

    @NotNull(message = "Menu ID is required")
    private Long menuId;

    @NotNull(message = "Booking date is required (format: YYYY-MM-DD)")
    private LocalDate bookingDate;
}
