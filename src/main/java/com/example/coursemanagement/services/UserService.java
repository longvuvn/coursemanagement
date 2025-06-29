package com.example.coursemanagement.services;

import com.example.coursemanagement.models.User;
import com.example.coursemanagement.models.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(String id);
    UserDTO CreateUser(UserDTO userDto);
    UserDTO UpdateUser(UserDTO userDto, String id);
    void DeleteUser(String id);
}
