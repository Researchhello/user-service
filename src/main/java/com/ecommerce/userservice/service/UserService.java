package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.response.UserResponse;
import com.ecommerce.userservice.entity.User;

import java.util.List;

public interface UserService {
    UserResponse getUserById(String id);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(String id, User userDetails);
    void deleteUser(String id);
    boolean existsByEmail(String email);
}