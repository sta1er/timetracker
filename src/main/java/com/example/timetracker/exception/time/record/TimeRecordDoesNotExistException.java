package com.example.timetracker.exception.time.record;

public class TimeRecordDoesNotExistException extends RuntimeException {
    public TimeRecordDoesNotExistException(String message) {
        super(message);
    }
}
