package com.example.coursemanagement.services.impl;
import com.example.coursemanagement.enums.UserStatus;
import com.example.coursemanagement.models.Role;
import com.example.coursemanagement.models.User;
import com.example.coursemanagement.models.dto.UserDTO;
import com.example.coursemanagement.repositories.UserRepository;
import com.example.coursemanagement.services.RoleService;
import com.example.coursemanagement.services.UserService;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final String DEFAULT_AVATAR_PATH = "/data/images/c21f969b5f03d33d43e04f8f136e7682.png";
    private final RoleService roleService;
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
        return optionalUser.map(user-> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This User"));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if(StringUtils.hasText(userDTO.getAvatar())){
            userDTO.setAvatar(DEFAULT_AVATAR_PATH);
        }
        Role role = roleService.getRoleByName("Learner");
        user.setRole(role);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAvatar(userDTO.getAvatar());
        user.setStatus(UserStatus.ACTIVE);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String id) {
        UUID uuid = UUID.fromString(id);
        User existingUser = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This User"));
        modelMapper.map(userDTO, existingUser);
        existingUser.setRole(roleService.getRoleByName(userDTO.getRoleName()));
        existingUser.setStatus(UserStatus.valueOf(userDTO.getStatus()));
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(String id) {
        UUID uuid = UUID.fromString(id);
        User existingUser = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found This User"));
        userRepository.delete(existingUser);
    }
}
