package com.example.timetracker.controller;

import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.security.CurrentUserService;
import com.example.timetracker.service.project.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final CurrentUserService currentUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody @Valid ProjectDto projectDto) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return projectService.createProject(projectDto, requesterUsername);
    }
}
