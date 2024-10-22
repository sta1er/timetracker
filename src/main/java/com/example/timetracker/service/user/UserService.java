package com.example.timetracker.service.user;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.entity.user.UserRole;
import com.example.timetracker.exception.user.UserDoesNotExist;
import com.example.timetracker.mapper.UserMapper;
import com.example.timetracker.repository.UserRepository;
import com.example.timetracker.validator.user.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
        userValidator.validate(userDto);

        User user = userMapper.toEntity(userDto);
        user.setRole(UserRole.USER);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(long id) {
        return userMapper.toDto(findUserById(id));
    }

    @Transactional
    public UserDto changePassword(long userId, String newPassword, String requesterUsername) {
        User user = findUserById(userId);
        userValidator.validatePasswordChange(requesterUsername, user.getUsername());

        user.setPassword(passwordEncoder.encode(newPassword));
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public  UserDto grantAdminRole(long userId, String requesterUsername) {
        User requester = findUserByUsername(requesterUsername);
        userValidator.validateAdminRights(requester);

        User userToUpdate = findUserById(userId);

        userToUpdate.setRole(UserRole.ADMIN);
        userToUpdate = userRepository.save(userToUpdate);

        return userMapper.toDto(userToUpdate);
    }

    @Transactional
    public void deleteUser(long userId, String requesterUsername) {
        userValidator.validateUserDeletion(userId, requesterUsername);
        userRepository.deleteById(userId);
    }

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserDoesNotExist("The user does not exist!"));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserDoesNotExist("The user does not exist!"));
    }
}
