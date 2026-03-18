package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.response.UserResponse;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(
            summary = "Get current user",
            description = "Returns the currently authenticated user's details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestAttribute("userId") String userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Returns user details for the specified ID (Admin only)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable String id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Returns list of all users (Admin only)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update user",
            description = "Updates user details for the specified ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "User ID", required = true) @PathVariable String id,
            @Parameter(description = "Updated user details", required = true) @RequestBody User userDetails) {
        UserResponse updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user",
            description = "Deletes the user with the specified ID (Admin only)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin only")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "User ID", required = true) @PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-email")
    @Operation(
            summary = "Check email existence",
            description = "Checks if an email is already registered"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed"),
            @ApiResponse(responseCode = "400", description = "Invalid email format")
    })
    public ResponseEntity<Boolean> checkEmailExists(
            @Parameter(description = "Email to check", required = true)
            @RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}