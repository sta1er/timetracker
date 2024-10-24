package com.example.timetracker.exception.user;

import com.example.timetracker.exception.ValidationException;

public class UserNotParticipantException extends ValidationException {
    public UserNotParticipantException(String message) {
        super(message);
    }
}
