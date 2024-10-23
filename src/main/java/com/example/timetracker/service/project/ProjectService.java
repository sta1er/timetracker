package com.example.timetracker.service.project;

import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.entity.project.Project;
import com.example.timetracker.mapper.ProjectMapper;
import com.example.timetracker.repository.ProjectRepository;
import com.example.timetracker.service.user.UserService;
import com.example.timetracker.validator.project.ProjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectValidator projectValidator;
    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto, String requesterUsername) {
        userService.isAdmin(requesterUsername);
        projectValidator.validateProjectName(projectDto.getName());

        Project project = projectMapper.toEntity(projectDto);
        project = projectRepository.save(project);

        // todo: добавить создателя проекта как участника проекта

        return projectMapper.toDto(project);
    }

}
