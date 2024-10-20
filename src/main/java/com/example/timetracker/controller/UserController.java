package com.example.timetracker.controller;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody @Valid UserDto user) {
        return userService.createUser(user);
    }

    /*@GetMapping("/{id}")
    public UserDto getUserById(@PathVariable @NotNull long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable @NotNull long id,
                                           @RequestBody UserDto updatedUser) {
        return userService.updateUser(id, updatedUser);;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @NotNull long id) {
        return userService.deleteUser(id);
    }*/
}
