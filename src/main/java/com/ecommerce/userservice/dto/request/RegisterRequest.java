package com.ecommerce.userservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "User registration request")
public class RegisterRequest {

    @Schema(description = "User email address", example = "user@example.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "User password (min 8 chars, must contain digit, lowercase, uppercase, special char)",
            example = "Password@123", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
    private String password;

    @Schema(description = "User first name", example = "John", required = true)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "User last name", example = "Doe", required = true)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;
}