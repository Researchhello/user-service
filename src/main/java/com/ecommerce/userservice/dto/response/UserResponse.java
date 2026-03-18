package com.ecommerce.userservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User response")
public class UserResponse {

    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Email verified status", example = "false")
    private boolean emailVerified;

    @Schema(description = "Account creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "User roles", example = "[\"ROLE_USER\"]")
    private Set<String> roles;
}