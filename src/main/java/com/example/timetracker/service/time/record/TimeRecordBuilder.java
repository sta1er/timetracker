package com.example.timetracker.service.time.record;

import com.example.timetracker.dto.time.record.TimeRecordDto;
import com.example.timetracker.entity.project.Project;
import com.example.timetracker.entity.record.TimeRecord;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.exception.project.ProjectDoesNotExistException;
import com.example.timetracker.exception.user.UserDoesNotExistException;
import com.example.timetracker.mapper.TimeRecordMapper;
import com.example.timetracker.repository.ProjectRepository;
import com.example.timetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeRecordBuilder {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TimeRecordMapper timeRecordMapper;

    public TimeRecord createTimeRecord(TimeRecordDto timeRecordDto, String requesterUsername) {
        User user = userRepository.findByUsername(requesterUsername)
                .orElseThrow(() -> new UserDoesNotExistException("The user does not exist!"));

        Project project = projectRepository.findById(timeRecordDto.getProjectId())
                .orElseThrow(() -> new ProjectDoesNotExistException("The project does not exist!"));

        TimeRecord timeRecord = timeRecordMapper.toEntity(timeRecordDto);
        timeRecord.setUser(user);
        timeRecord.setProject(project);

        return timeRecord;
    }
}
