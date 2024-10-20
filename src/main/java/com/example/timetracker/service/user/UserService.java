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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public UserDto createUser(UserDto userDto) {
        userValidator.validate(userDto);

        User user = userMapper.toEntity(userDto);
        user.setRole(UserRole.ROLE_USER);
        //todo: passwordEncoder

        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    private User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserDoesNotExist("The user does not exist!"));
    }
}
