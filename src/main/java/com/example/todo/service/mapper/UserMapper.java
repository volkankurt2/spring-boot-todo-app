package com.example.todo.service.mapper;


import com.example.todo.dto.UserDTO;
import com.example.todo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        if (users == null) {
            return null;
        } else {
            return users.stream()
                    .filter(Objects::nonNull)
                    .map(this::userToUserDTO)
                    .collect(Collectors.toList());
        }
    }

    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        } else {
            return UserDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt()).build();
        }
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        if (userDTOs == null) {
            return null;
        } else {
            return userDTOs.stream()
                    .filter(Objects::nonNull)
                    .map(this::userDTOToUser)
                    .collect(Collectors.toList());
        }

    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());

            return user;
        }
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }

        return User.builder().id(id).build();
    }
}
