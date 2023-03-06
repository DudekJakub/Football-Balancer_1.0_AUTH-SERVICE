package com.dudek.authservice.mapper;

import com.dudek.authservice.model.dto.user.UserDto;
import com.dudek.authservice.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public List<UserDto> userCollectionToSimpleDtoList(Collection<User> userCollection) {
        return userCollection.stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }

    public UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .sex(user.getSex())
                .email(user.getEmail())
                .userRole(user.getRole())
                .build();
    }
}
