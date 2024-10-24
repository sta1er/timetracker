package com.example.timetracker.exception.user;

public class UserNotAdminException extends RuntimeException {

    public UserNotAdminException(String message) {
        super(message);
    }
}
