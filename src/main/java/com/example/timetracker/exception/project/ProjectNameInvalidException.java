package com.example.timetracker.exception.project;

import com.example.timetracker.exception.ValidationException;

public class ProjectNameInvalidException extends ValidationException {
    public ProjectNameInvalidException(String message) {
        super(message);
    }
}
