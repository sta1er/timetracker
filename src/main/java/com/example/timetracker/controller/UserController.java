package com.example.timetracker.controller;

import com.example.timetracker.dto.user.ChangePasswordRequest;
import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.security.CurrentUserService;
import com.example.timetracker.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CurrentUserService currentUserService;

    @PostMapping("/auth/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody @Valid UserDto user) {
        return userService.createUser(user);
    }

    @GetMapping("/users/{id}")
    public UserDto getUserBy(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/change-password")
    public UserDto changePassword(@RequestBody ChangePasswordRequest request) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return userService.changePassword(request.getUserId(), request.getNewPassword(), requesterUsername);
    }

    @PutMapping("/users/grant-admin/{userId}")
    public UserDto grantAdminRole(@PathVariable long userId) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return userService.grantAdminRole(userId, requesterUsername);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        String requesterUsername = currentUserService.getCurrentUsername();
        userService.deleteUser(id, requesterUsername);
    }
}
