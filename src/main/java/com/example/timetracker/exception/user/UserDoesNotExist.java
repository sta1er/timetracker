package com.example.timetracker.exception.user;

import com.example.timetracker.exception.ValidationException;

public class UserDoesNotExist extends ValidationException {
    public UserDoesNotExist(String message) {
        super(message);
    }
}
