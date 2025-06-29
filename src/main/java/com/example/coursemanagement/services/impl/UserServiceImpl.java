package com.example.coursemanagement.services.impl;
import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.User;
import com.example.coursemanagement.models.dto.UserDTO;
import com.example.coursemanagement.repositories.UserRepository;
import com.example.coursemanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final String DEFAULT_AVATAR_PATH = "/data/images/c21f969b5f03d33d43e04f8f136e7682.png";
    private final RoleServiceImpl roleService;


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<User> user = userRepository.findById(uuid);
        return user.map(this::userToUserDTO).orElse(null);
    }

    @Override
    public UserDTO CreateUser(UserDTO userDTO) {
        User user = new User();
        Instant now = Instant.now();
        if(userDTO.getAvatar() == null || userDTO.getAvatar().isEmpty()){
            userDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        Role role = roleService.getRoleByName("Learner");
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setRole(role);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        user.setAvatar(userDTO.getAvatar());
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO UpdateUser(UserDTO userDTO, String id) {
        UUID uuid = UUID.fromString(id);
        User existingUser = userRepository.findById(uuid).orElse(null);
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(roleService.getRoleByName(userDTO.getRoleName()));
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setAvatar(userDTO.getAvatar());
        existingUser.setStatus(UserStatus.valueOf(userDTO.getStatus()));
        User updatedUser = userRepository.save(existingUser);
        existingUser.setUpdatedAt(Instant.now());
        return userToUserDTO(updatedUser);
    }

    @Override
    public void DeleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        User existingUser = userRepository.findById(uuid).orElse(null);
        userRepository.delete(existingUser);
    }

    public UserDTO userToUserDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(String.valueOf(user.getId()));
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAvatar(user.getAvatar());
        dto.setPassword(user.getPassword());
        dto.setRoleName(user.getRole().getName());
        dto.setStatus(user.getStatus().name());
        dto.setCreatedAt(String.valueOf(user.getCreatedAt()));
        dto.setUpdatedAt(String.valueOf(user.getUpdatedAt()));
        return dto;
    }
}
