package com.mess.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for the Admin Dashboard summary statistics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private long totalUsers;
    private long totalStudents;
    private long totalAdmins;
    private long totalMenus;
    private long totalBookings;
    private long activeBookings;
    private long cancelledBookings;
    private long totalFeedback;
}
