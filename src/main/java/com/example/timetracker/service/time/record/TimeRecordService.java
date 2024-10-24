package com.example.timetracker.service.time.record;

import com.example.timetracker.dto.time.record.TimeRecordDto;
import com.example.timetracker.entity.record.TimeRecord;
import com.example.timetracker.exception.time.record.TimeRecordDoesNotExistException;
import com.example.timetracker.mapper.TimeRecordMapper;
import com.example.timetracker.repository.TimeRecordRepository;
import com.example.timetracker.validator.project.ProjectPermissionChecker;
import com.example.timetracker.validator.time.record.TimeRecordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeRecordService {

    private final TimeRecordRepository timeRecordRepository;
    private final TimeRecordMapper timeRecordMapper;
    private final TimeRecordValidator timeRecordValidator;
    private final ProjectPermissionChecker projectPermissionChecker;
    private final TimeRecordBuilder timeRecordBuilder;

    @Transactional
    public TimeRecordDto createTimeRecord(TimeRecordDto timeRecordDto, String requesterUsername) {
        timeRecordValidator.validateTimeRecordDates(timeRecordDto.getStartTime(), timeRecordDto.getEndTime());
        projectPermissionChecker.checkUserParticipation(timeRecordDto.getProjectId(), requesterUsername);

        TimeRecord timeRecord = timeRecordBuilder.createTimeRecord(timeRecordDto, requesterUsername);
        timeRecord = timeRecordRepository.save(timeRecord);
        log.info("Created time record with ID: {}", timeRecord.getId());

        return timeRecordMapper.toDto(timeRecord);
    }

    @Transactional(readOnly = true)
    public TimeRecordDto getTimeRecordById(long timeRecordId, long projectId, String requesterUsername) {
        projectPermissionChecker.checkUserParticipation(projectId, requesterUsername);
        return timeRecordMapper.toDto(findTimeRecordById(timeRecordId));
    }

    @Transactional(readOnly = true)
    public List<TimeRecordDto> getAllTimeRecordsByProjectId(long projectId, String requesterUsername) {
        projectPermissionChecker.checkUserParticipation(projectId, requesterUsername);

        List<TimeRecord> timeRecords = timeRecordRepository.findAllByProjectId(projectId);
        if(timeRecords == null) {
            return null;
        }

        return timeRecords.stream()
                .map(timeRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTimeRecord(long timeRecordId, String requesterUsername) {
        timeRecordValidator.checkIsOwnerOrAdministrator(timeRecordId, requesterUsername);
        timeRecordRepository.deleteById(timeRecordId);
        log.info("Deleted time record with ID: {}", timeRecordId);
    }

    @Transactional
    public TimeRecordDto changeStartAndEndTime(long timeRecordId, String requesterUsername,
                                               LocalDateTime startTime, LocalDateTime endTime) {

        timeRecordValidator.checkIsOwnerOrAdministrator(timeRecordId, requesterUsername);
        timeRecordValidator.validateTimeRecordDates(startTime, endTime);

        TimeRecord timeRecord = findTimeRecordById(timeRecordId);
        timeRecord.setStartTime(startTime);
        timeRecord.setEndTime(endTime);

        timeRecord = timeRecordRepository.save(timeRecord);
        log.info("Changed times for time record with ID: {}", timeRecordId);

        return timeRecordMapper.toDto(timeRecord);
    }

    private TimeRecord findTimeRecordById(long id) {
        return timeRecordRepository.findById(id).orElseThrow(() ->
                new TimeRecordDoesNotExistException("The time record does not exist!"));
    }
}
