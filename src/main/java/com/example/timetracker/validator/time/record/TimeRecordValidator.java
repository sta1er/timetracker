package com.example.timetracker.validator.time.record;

import com.example.timetracker.dto.time.record.TimeRecordDto;
import com.example.timetracker.entity.record.TimeRecord;
import com.example.timetracker.exception.time.record.AccessDeniedException;
import com.example.timetracker.exception.time.record.InvalidTimeRecordException;
import com.example.timetracker.exception.time.record.TimeRecordDoesNotExistException;
import com.example.timetracker.repository.TimeRecordRepository;
import com.example.timetracker.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TimeRecordValidator {

    private final TimeRecordRepository timeRecordRepository;
    private final UserService userService;

    public void validateTimeRecordDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidTimeRecordException("End time must be after start time!");
        }
    }

    public void checkIsOwnerOrAdministrator(long timeRecordId, String requesterUsername) {
        TimeRecord timeRecord = timeRecordRepository.findById(timeRecordId)
                .orElseThrow(() -> new TimeRecordDoesNotExistException("The time record does not exist!"));

        if (!timeRecord.getUser().getUsername().equals(requesterUsername) && !userService.isAdmin(requesterUsername)) {
            throw new AccessDeniedException("You do not have permission to delete this time record!");
        }

    }
}
