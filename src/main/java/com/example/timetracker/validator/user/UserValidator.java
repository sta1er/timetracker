package com.example.timetracker.validator.user;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.entity.user.UserRole;
import com.example.timetracker.exception.user.*;
import com.example.timetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public void validatePasswordChange(String requesterUsername, String targetUsername) {
        if (!requesterUsername.equals(targetUsername)) {
            throw new UserPasswordChangeException("You can only change your own password!");
        }
    }

    public void validateAdminRights(User requester) {
        if (!requester.getRole().equals(UserRole.ADMIN)) {
            throw new UserNotAdminException("You do not have admin rights to perform this action!");
        }
    }

    public void validateUserDeletion(long userId, String requesterUsername) {
        User requester = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new UserDoesNotExist("The user does not exist!"));

        User userToDelete = userRepository.findById(userId).orElseThrow(() ->
                new UserDoesNotExist("The user does not exist!"));

        if (!requester.getUsername().equals(userToDelete.getUsername()) &&
                !requester.getRole().equals(UserRole.ADMIN)) {
            throw new UserNotAdminException("You do not have rights to delete this user!");
        }
    }
}
