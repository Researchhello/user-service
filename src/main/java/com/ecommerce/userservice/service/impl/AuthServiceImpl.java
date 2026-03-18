package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.dto.request.LoginRequest;
import com.ecommerce.userservice.dto.request.RegisterRequest;
import com.ecommerce.userservice.dto.response.JwtResponse;
import com.ecommerce.userservice.dto.response.UserResponse;
import com.ecommerce.userservice.entity.AuthProvider;
import com.ecommerce.userservice.entity.Role;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.BadRequestException;
import com.ecommerce.userservice.exception.ResourceNotFoundException;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.repository.RoleRepository;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.security.jwt.JwtUtils;
import com.ecommerce.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    
    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));
        
        Set<String> roles = user.getRoles().stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toSet());
        
        return JwtResponse.builder()
            .token(jwt)
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .roles(roles)
            .build();
    }
    
    @Override
    @Transactional
    public UserResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already taken!");
        }
        
        // Create new user account
        User user = User.builder()
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .phoneNumber(registerRequest.getPhoneNumber())
            .provider(AuthProvider.LOCAL)
            .emailVerified(false)
            .build();
        
        // Assign default role
        Role userRole = roleRepository.findByName(Role.RoleType.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        
        User savedUser = userRepository.save(user);
        
        return userMapper.toUserResponse(savedUser);
    }
    
    @Override
    public boolean verifyEmail(String token) {
        // Implementation for email verification
        // This would typically validate a token sent via email
        return true;
    }
}