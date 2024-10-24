package com.example.timetracker.exception.project;

import com.example.timetracker.exception.ValidationException;

public class ProjectNameAlreadyExistsException extends ValidationException {
    public ProjectNameAlreadyExistsException(String message) {
        super(message);
    }
}
