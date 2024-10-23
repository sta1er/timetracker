package com.example.timetracker.service.project;

import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.entity.project.Project;
import com.example.timetracker.exception.project.ProjectDoesNotExistException;
import com.example.timetracker.mapper.ProjectMapper;
import com.example.timetracker.repository.ProjectRepository;
import com.example.timetracker.service.user.UserService;
import com.example.timetracker.validator.project.ProjectPermissionChecker;
import com.example.timetracker.validator.project.ProjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectValidator projectValidator;
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectPermissionChecker projectPermissionChecker;
    private final ProjectParticipantManager projectParticipantManager;

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto, String requesterUsername) {
        userService.isAdmin(requesterUsername);
        projectValidator.validateProjectName(projectDto.getName());

        Project project = projectMapper.toEntity(projectDto);

        projectParticipantManager.addParticipant(project, requesterUsername);

        project = projectRepository.save(project);
        log.info("Created project with ID: {}", project.getId());

        return projectMapper.toDto(project);
    }

    @Transactional(readOnly = true)
    public ProjectDto getProjectById(long projectId, String requesterUsername) {
        projectPermissionChecker.checkUserParticipation(projectId, requesterUsername);
        return projectMapper.toDto(findProjectById(projectId));
    }

    @Transactional
    public void deleteProject(long projectId, String requesterUsername) {
        userService.isAdmin(requesterUsername);
        projectPermissionChecker.checkUserParticipation(projectId, requesterUsername);

        projectRepository.deleteById(projectId);
        log.info("Deleted project with ID: {}", projectId);
    }

    @Transactional
    public ProjectDto changeProjectName(long projectId, String newProjectName,String requesterUsername) {
        userService.isAdmin(requesterUsername);
        projectPermissionChecker.checkUserParticipation(projectId, requesterUsername);
        projectValidator.validateProjectName(newProjectName);

        Project project = findProjectById(projectId);
        project.setName(newProjectName);
        project = projectRepository.save(project);
        log.info("Changed name for project with ID: {}", projectId);

        return projectMapper.toDto(project);
    }

    @Transactional
    public ProjectDto changeProjectDescription(long projectId, String newProjectDescription,
                                               String requesterUsername) {

        userService.isAdmin(requesterUsername);
        projectPermissionChecker.checkUserParticipation(projectId, requesterUsername);

        Project project = findProjectById(projectId);
        project.setDescription(newProjectDescription);
        project = projectRepository.save(project);
        log.info("Changed description for project with ID: {}", projectId);

        return projectMapper.toDto(project);
    }

    @Transactional
    public ProjectDto addUserToProject(long projectId, String participationUsername, String requesterUsername) {
        userService.isAdmin(requesterUsername);
        projectPermissionChecker.checkUserParticipation(projectId, requesterUsername);

        Project project = findProjectById(projectId);
        projectParticipantManager.addParticipant(project, participationUsername);

        project = projectRepository.save(project);
        log.info("Add user: {} to project with ID: {}", requesterUsername, projectId);

        return projectMapper.toDto(project);
    }

    private Project findProjectById(long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() ->
                new ProjectDoesNotExistException("The project does not exist!"));
    }

}
