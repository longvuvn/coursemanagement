package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(String id);
    UserDTO createUser(UserDTO userDto);
    UserDTO updateUser(UserDTO userDto, String id);
    void deleteUser(String id);
}
