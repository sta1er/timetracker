package com.example.timetracker.service.time.record;

import com.example.timetracker.dto.time.record.TimeRecordDto;
import com.example.timetracker.entity.record.TimeRecord;
import com.example.timetracker.exception.time.record.TimeRecordDoesNotExistException;
import com.example.timetracker.mapper.TimeRecordMapper;
import com.example.timetracker.repository.TimeRecordRepository;
import com.example.timetracker.validator.project.ProjectPermissionChecker;
import com.example.timetracker.validator.time.record.TimeRecordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TimeRecordServiceTest {

    @Mock
    private TimeRecordRepository timeRecordRepository;
    @Mock
    private TimeRecordMapper timeRecordMapper;
    @Mock
    private TimeRecordValidator timeRecordValidator;
    @Mock
    private ProjectPermissionChecker projectPermissionChecker;
    @Mock
    private TimeRecordBuilder timeRecordBuilder;

    @InjectMocks
    private TimeRecordService timeRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTimeRecord() {
        TimeRecordDto timeRecordDto = new TimeRecordDto();
        TimeRecord timeRecord = new TimeRecord();
        timeRecord.setId(1L);

        when(timeRecordMapper.toEntity(any(TimeRecordDto.class))).thenReturn(timeRecord);
        when(timeRecordRepository.save(any(TimeRecord.class))).thenReturn(timeRecord);
        when(timeRecordMapper.toDto(any(TimeRecord.class))).thenReturn(timeRecordDto);
        when(timeRecordBuilder.createTimeRecord(any(), any())).thenReturn(timeRecord);
        doNothing().when(timeRecordValidator).validateTimeRecordDates(any(LocalDateTime.class), any(LocalDateTime.class));
        doNothing().when(projectPermissionChecker).checkUserParticipation(anyLong(), anyString());

        TimeRecordDto result = timeRecordService.createTimeRecord(timeRecordDto, "user");

        assertNotNull(result);
        verify(timeRecordRepository).save(any(TimeRecord.class));
    }

    @Test
    void testGetTimeRecordById() {
        TimeRecord timeRecord = new TimeRecord();
        TimeRecordDto timeRecordDto = new TimeRecordDto();

        when(timeRecordRepository.findById(anyLong())).thenReturn(Optional.of(timeRecord));
        when(timeRecordMapper.toDto(any(TimeRecord.class))).thenReturn(timeRecordDto);
        doNothing().when(projectPermissionChecker).checkUserParticipation(anyLong(), anyString());

        TimeRecordDto result = timeRecordService.getTimeRecordById(1L, 1L, "user");

        assertNotNull(result);
        verify(timeRecordRepository).findById(1L);
    }

    @Test
    void testGetTimeRecordByIdNegative() {
        when(timeRecordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TimeRecordDoesNotExistException.class, () -> timeRecordService.getTimeRecordById(1L, 1L, "user"));
    }

    @Test
    void testDeleteTimeRecord() {
        doNothing().when(timeRecordValidator).checkIsOwnerOrAdministrator(anyLong(), anyString());
        doNothing().when(timeRecordRepository).deleteById(anyLong());

        timeRecordService.deleteTimeRecord(1L, "user");

        verify(timeRecordRepository).deleteById(1L);
    }

    @Test
    void testChangeStartAndEndTime() {
        TimeRecord timeRecord = new TimeRecord();
        TimeRecordDto timeRecordDto = new TimeRecordDto();
        LocalDateTime newStartTime = LocalDateTime.now().minusHours(1);
        LocalDateTime newEndTime = LocalDateTime.now();

        when(timeRecordRepository.findById(anyLong())).thenReturn(Optional.of(timeRecord));
        when(timeRecordRepository.save(any(TimeRecord.class))).thenReturn(timeRecord);
        when(timeRecordMapper.toDto(any(TimeRecord.class))).thenReturn(timeRecordDto);
        doNothing().when(timeRecordValidator).checkIsOwnerOrAdministrator(anyLong(), anyString());
        doNothing().when(timeRecordValidator).validateTimeRecordDates(any(LocalDateTime.class), any(LocalDateTime.class));

        TimeRecordDto result = timeRecordService.changeStartAndEndTime(1L, "user", newStartTime, newEndTime);

        assertNotNull(result);
        verify(timeRecordRepository).save(any(TimeRecord.class));
        assertEquals(newStartTime, timeRecord.getStartTime());
        assertEquals(newEndTime, timeRecord.getEndTime());
    }
}
