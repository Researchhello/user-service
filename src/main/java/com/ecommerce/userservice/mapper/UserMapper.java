package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.response.UserResponse;
import com.ecommerce.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "roles", source = "user", qualifiedByName = "mapRoles")
    UserResponse toUserResponse(User user);
    
    @Named("mapRoles")
    default Set<String> mapRoles(User user) {
        return user.getRoles().stream()
            .map(role -> role.getName().name())
            .collect(Collectors.toSet());
    }
}