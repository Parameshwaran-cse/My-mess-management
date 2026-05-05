package com.mess.management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for updating the current user's profile.
 * All fields are optional — only non-null values are applied.
 */
@Data
public class UpdateUserRequest {

    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @Email(message = "Please provide a valid email address")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be exactly 10 digits")
    private String phone;

    /** Provide new password only if you want to change it */
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
