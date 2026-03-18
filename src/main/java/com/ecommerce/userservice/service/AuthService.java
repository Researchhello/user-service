package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.request.LoginRequest;
import com.ecommerce.userservice.dto.request.RegisterRequest;
import com.ecommerce.userservice.dto.response.JwtResponse;
import com.ecommerce.userservice.dto.response.UserResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    UserResponse registerUser(RegisterRequest registerRequest);
    boolean verifyEmail(String token);
}