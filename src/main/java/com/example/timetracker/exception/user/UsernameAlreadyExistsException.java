package com.example.timetracker.exception.user;

import com.example.timetracker.exception.ValidationException;

public class UsernameAlreadyExistsException extends ValidationException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
