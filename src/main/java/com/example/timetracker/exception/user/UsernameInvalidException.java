package com.example.timetracker.exception.user;

import com.example.timetracker.exception.ValidationException;

public class UsernameInvalidException extends ValidationException {
    public UsernameInvalidException(String message) {
        super(message);
    }
}
