package com.example.timetracker.mapper;

import com.example.timetracker.dto.time.record.TimeRecordDto;
import com.example.timetracker.entity.project.Project;
import com.example.timetracker.entity.record.TimeRecord;
import com.example.timetracker.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeRecordMapperTest {

    private final TimeRecordMapper timeRecordMapper = Mappers.getMapper(TimeRecordMapper.class);

    private final LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 12, 0);
    private final  LocalDateTime updatedAt = LocalDateTime.of(2024, 1, 2, 12, 0);
    private final LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 12, 0);
    private final  LocalDateTime endTime = LocalDateTime.of(2024, 2, 2, 12, 0);

    private TimeRecord timeRecord;
    private TimeRecordDto timeRecordDto;

    private TimeRecord expectedTimeRecord;

    private User user;
    private Project project;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .username("testUser")
                .build();

        project = Project.builder()
                .id(1L)
                .name("testProject")
                .build();

        timeRecord = TimeRecord.builder()
                .id(1L)
                .description("tracker")
                .updatedAt(updatedAt)
                .createdAt(createdAt)
                .startTime(startTime)
                .endTime(endTime)
                .user(user)
                .project(project)
                .build();

        timeRecordDto = TimeRecordDto.builder()
                .id(1L)
                .description("tracker")
                .updatedAt(updatedAt)
                .createdAt(createdAt)
                .startTime(startTime)
                .endTime(endTime)
                .userId(1L)
                .projectId(1L)
                .build();

        expectedTimeRecord = TimeRecord.builder()
                .id(1L)
                .description("tracker")
                .updatedAt(updatedAt)
                .createdAt(createdAt)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    @Test
    @DisplayName("Test mapping from Entity to DTO")
    public void testTimeRecordToTimeRecordDto() {
        TimeRecordDto resultTimeRecordDto = timeRecordMapper.toDto(timeRecord);
        assertEquals(timeRecordDto, resultTimeRecordDto);
    }

    @Test
    @DisplayName("Test mapping from DTO to Entity")
    public void testTimeRecordDtoToTimeRecord() {
        TimeRecord resultTimeRecord = timeRecordMapper.toEntity(timeRecordDto);
        assertEquals(expectedTimeRecord, resultTimeRecord);
    }

}