package com.example.timetracker.exception.user;

import com.example.timetracker.exception.ValidationException;

public class UserDoesNotExistException extends ValidationException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
