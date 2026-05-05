package com.mess.management.dto.response;

import com.mess.management.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for booking data returned to clients.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private Long userId;
    private String username;
    private Long menuId;
    private String mealType;
    private LocalDate bookingDate;
    private String status;
    private LocalDateTime createdAt;

    /**
     * Convenience factory method to map from a Booking entity.
     */
    public static BookingResponse from(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .username(booking.getUsername())
                .menuId(booking.getMenuId())
                .mealType(booking.getMealType().name())
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus().name())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}
