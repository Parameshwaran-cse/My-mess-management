package com.mess.management.controller;

import com.mess.management.dto.response.ApiResponse;
import com.mess.management.dto.response.BookingResponse;
import com.mess.management.dto.response.DashboardResponse;
import com.mess.management.dto.response.FeedbackResponse;
import com.mess.management.dto.response.UserResponse;
import com.mess.management.enums.BookingStatus;
import com.mess.management.enums.Role;
import com.mess.management.repository.BookingRepository;
import com.mess.management.repository.FeedbackRepository;
import com.mess.management.repository.MenuRepository;
import com.mess.management.repository.UserRepository;
import com.mess.management.service.BookingService;
import com.mess.management.service.FeedbackService;
import com.mess.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for admin-only operations.
 * ALL endpoints in this controller require ADMIN role.
 * Access is double-enforced: SecurityConfig + @PreAuthorize.
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BookingService bookingService;
    private final FeedbackService feedbackService;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final BookingRepository bookingRepository;
    private final FeedbackRepository feedbackRepository;

    /**
     * GET /api/admin/dashboard
     * Returns summary statistics for the admin dashboard.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {
        DashboardResponse stats = DashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalStudents(userRepository.findByRole(Role.STUDENT).size())
                .totalAdmins(userRepository.findByRole(Role.ADMIN).size())
                .totalMenus(menuRepository.count())
                .totalBookings(bookingRepository.count())
                .activeBookings(bookingRepository.countByStatus(BookingStatus.CONFIRMED))
                .cancelledBookings(bookingRepository.countByStatus(BookingStatus.CANCELLED))
                .totalFeedback(feedbackRepository.count())
                .build();
        return ResponseEntity.ok(ApiResponse.success("Dashboard data", stats));
    }

    /**
     * GET /api/admin/users
     * Returns a list of all registered users.
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
    }

    /**
     * DELETE /api/admin/users/{id}
     * Deletes a user account.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.successMessage("User deleted successfully."));
    }

    /**
     * PUT /api/admin/users/{id}/role
     * Changes a user's role (STUDENT ↔ ADMIN).
     *
     * Request param: role=ADMIN or role=STUDENT
     */
    @PutMapping("/users/{id}/role")
    public ResponseEntity<ApiResponse<UserResponse>> changeUserRole(
            @PathVariable Long id,
            @RequestParam String role) {
        UserResponse updated = userService.changeUserRole(id, role);
        return ResponseEntity.ok(ApiResponse.success("User role updated.", updated));
    }

    /**
     * GET /api/admin/bookings
     * Returns all bookings in the system.
     */
    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        return ResponseEntity.ok(ApiResponse.success(bookingService.getAllBookings()));
    }

    /**
     * GET /api/admin/feedback
     * Returns all feedback submitted by all users.
     */
    @GetMapping("/feedback")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getAllFeedback() {
        return ResponseEntity.ok(ApiResponse.success(feedbackService.getAllFeedback()));
    }
}
