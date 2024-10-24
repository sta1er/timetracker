package com.example.timetracker.exception.project;

import com.example.timetracker.exception.ValidationException;

public class ProjectDoesNotExistException extends ValidationException {
    public ProjectDoesNotExistException(String message) {
        super(message);
    }
}
