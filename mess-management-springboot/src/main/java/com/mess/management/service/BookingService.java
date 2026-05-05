package com.mess.management.service;

import com.mess.management.dto.request.BookingRequest;
import com.mess.management.dto.response.BookingResponse;
import com.mess.management.entity.Booking;
import com.mess.management.entity.Menu;
import com.mess.management.enums.BookingStatus;
import com.mess.management.exception.BadRequestException;
import com.mess.management.exception.ResourceNotFoundException;
import com.mess.management.exception.UnauthorizedException;
import com.mess.management.repository.BookingRepository;
import com.mess.management.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Business logic for meal bookings/ordering.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MenuRepository menuRepository;

    // ─── Create Booking ──────────────────────────────────────────────────────

    public BookingResponse createBooking(BookingRequest request, Long userId, String username) {
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu", request.getMenuId()));

        // Prevent booking a past date
        if (request.getBookingDate().isBefore(java.time.LocalDate.now())) {
            throw new BadRequestException("Cannot book a meal for a past date.");
        }

        Booking booking = Booking.builder()
                .userId(userId)
                .username(username)
                .menuId(menu.getId())
                .mealType(menu.getMealType())
                .bookingDate(request.getBookingDate())
                .status(BookingStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created: user={}, menu={}, date={}", username, menu.getId(), request.getBookingDate());
        return BookingResponse.from(saved);
    }

    // ─── Get My Bookings ─────────────────────────────────────────────────────

    public List<BookingResponse> getMyBookings(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(BookingResponse::from)
                .toList();
    }

    // ─── Get Booking by ID ───────────────────────────────────────────────────

    public BookingResponse getBookingById(Long bookingId, Long requestingUserId, boolean isAdmin) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", bookingId));

        if (!isAdmin && !booking.getUserId().equals(requestingUserId)) {
            throw new UnauthorizedException("You can only view your own bookings.");
        }
        return BookingResponse.from(booking);
    }

    // ─── Cancel Booking ──────────────────────────────────────────────────────

    public BookingResponse cancelBooking(Long bookingId, Long requestingUserId, boolean isAdmin) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", bookingId));

        if (!isAdmin && !booking.getUserId().equals(requestingUserId)) {
            throw new UnauthorizedException("You can only cancel your own bookings.");
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("This booking is already cancelled.");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setUpdatedAt(LocalDateTime.now());
        Booking updated = bookingRepository.save(booking);
        log.info("Booking {} cancelled by user {}", bookingId, requestingUserId);
        return BookingResponse.from(updated);
    }

    // ─── Admin: All Bookings ─────────────────────────────────────────────────

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingResponse::from)
                .toList();
    }
}
