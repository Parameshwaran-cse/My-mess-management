package com.mess.management.controller;

import com.mess.management.config.JwtUtil;
import com.mess.management.dto.request.BookingRequest;
import com.mess.management.dto.response.ApiResponse;
import com.mess.management.dto.response.BookingResponse;
import com.mess.management.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for meal bookings.
 * All endpoints require authentication.
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final JwtUtil jwtUtil;

    /**
     * POST /api/bookings
     * Book a meal from a specific menu for a given date.
     *
     * Request body: { menuId, bookingDate }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request,
            @RequestHeader("Authorization") String authHeader) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        String username = jwtUtil.extractUsername(authHeader.substring(7));
        BookingResponse booking = bookingService.createBooking(request, userId, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking confirmed.", booking));
    }

    /**
     * GET /api/bookings/my
     * Returns all bookings made by the currently logged-in user.
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getMyBookings(
            @RequestHeader("Authorization") String authHeader) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        return ResponseEntity.ok(ApiResponse.success(bookingService.getMyBookings(userId)));
    }

    /**
     * GET /api/bookings/{id}
     * Returns a specific booking by ID.
     * Students can only see their own bookings; admins can see any.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            Authentication authentication) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(ApiResponse.success(
                bookingService.getBookingById(id, userId, isAdmin)));
    }

    /**
     * DELETE /api/bookings/{id}
     * Cancel a booking. Students can only cancel their own; admins can cancel any.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            Authentication authentication) {
        Long userId = jwtUtil.extractUserId(authHeader.substring(7));
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        BookingResponse cancelled = bookingService.cancelBooking(id, userId, isAdmin);
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled.", cancelled));
    }
}
