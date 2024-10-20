package com.example.timetracker.validator.user;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.exception.user.UsernameInvalidException;
import com.example.timetracker.exception.user.UsernameAlreadyExistsException;
import com.example.timetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    public void validate(UserDto user) {
        validateUsername(user.getUsername());
    }

    private void validateUsername(String username) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new UsernameInvalidException("Username can only contain letters and digits!");
        }
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
    }
}
