package com.example.timetracker.controller;

import com.example.timetracker.dto.project.ChangeProjectNameRequest;
import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.dto.time.record.ChangeTimeRequest;
import com.example.timetracker.dto.time.record.TimeRecordDto;
import com.example.timetracker.security.CurrentUserService;
import com.example.timetracker.service.time.record.TimeRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trackers")
@RequiredArgsConstructor
public class TimeRecordController {

    private final TimeRecordService timeRecordService;
    private final CurrentUserService currentUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeRecordDto createTimeRecord(@RequestBody @Valid TimeRecordDto timeRecordDto) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return timeRecordService.createTimeRecord(timeRecordDto, requesterUsername);
    }

    @GetMapping("/{timeRecordId}")
    public TimeRecordDto getTimeRecordById(@PathVariable long timeRecordId,
                                     @RequestParam long projectId) {

        String requesterUsername = currentUserService.getCurrentUsername();
        return timeRecordService.getTimeRecordById(timeRecordId, projectId, requesterUsername);
    }

    @GetMapping("/project/{projectId}")
    public List<TimeRecordDto> getAllTimeRecordByProjectId(@PathVariable long projectId) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return timeRecordService.getAllTimeRecordsByProjectId(projectId, requesterUsername);
    }

    @DeleteMapping("{timeRecordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimeRecord(@PathVariable long timeRecordId) {
        String requesterUsername = currentUserService.getCurrentUsername();
        timeRecordService.deleteTimeRecord(timeRecordId, requesterUsername);
    }

    @PutMapping("/change-times")
    public TimeRecordDto changeTimeRecordTimes(@RequestBody ChangeTimeRequest request) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return timeRecordService.changeStartAndEndTime(request.getTimeRecordId(), requesterUsername,
                request.getStartTime(), request.getEndTime());
    }
}
