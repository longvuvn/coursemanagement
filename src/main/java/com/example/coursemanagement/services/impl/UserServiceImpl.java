package com.example.coursemanagement.services.impl;
import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.User;
import com.example.coursemanagement.models.dto.UserDTO;
import com.example.coursemanagement.repositories.UserRepository;
import com.example.coursemanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<User> optionalUser  = userRepository.findById(uuid);
        return optionalUser.map(user-> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    @Override
    public UserDTO CreateUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if(userDTO.getAvatar() == null || userDTO.getAvatar().isEmpty()){
            userDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        Role role = roleService.getRoleByName("Learner");
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setRole(role);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAvatar(userDTO.getAvatar());
        user.setStatus(UserStatus.ACTIVE);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
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
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void DeleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        User existingUser = userRepository.findById(uuid).orElse(null);
        userRepository.delete(existingUser);
    }
}
