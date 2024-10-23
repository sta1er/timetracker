package com.example.timetracker.exception.user;

import com.example.timetracker.exception.ValidationException;

public class UserAlreadyParticipantException extends ValidationException {
    public UserAlreadyParticipantException(String message) {
        super(message);
    }
}
