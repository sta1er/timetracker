package com.example.timetracker.controller;

import com.example.timetracker.dto.project.ChangeProjectDescriptionRequest;
import com.example.timetracker.dto.project.ChangeProjectNameRequest;
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

    @GetMapping("/{projectId}")
    public ProjectDto getProjectById(@PathVariable long projectId) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return projectService.getProjectById(projectId, requesterUsername);
    }

    @DeleteMapping("{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable long projectId) {
        String requesterUsername = currentUserService.getCurrentUsername();
        projectService.deleteProject(projectId, requesterUsername);
    }

    @PutMapping("/change-project-name")
    public ProjectDto changeProjectName(@RequestBody ChangeProjectNameRequest request) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return projectService.changeProjectName(request.getProjectId(),
                request.getNewProjectName(), requesterUsername);
    }

    @PutMapping("/change-project-description")
    public ProjectDto changeProjectDescription(@RequestBody ChangeProjectDescriptionRequest request) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return projectService.changeProjectDescription(request.getProjectId(),
                request.getNewProjectDescription(), requesterUsername);
    }

    @PutMapping("/{projectId}/addUser")
    public ProjectDto addUserToProject(@PathVariable long projectId, @RequestParam String username) {
        String requesterUsername = currentUserService.getCurrentUsername();
        return projectService.addUserToProject(projectId, username, requesterUsername);
    }


}
