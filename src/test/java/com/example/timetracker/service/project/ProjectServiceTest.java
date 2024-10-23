package com.example.timetracker.service.project;

import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.entity.project.Project;
import com.example.timetracker.exception.project.ProjectDoesNotExistException;
import com.example.timetracker.mapper.ProjectMapper;
import com.example.timetracker.repository.ProjectRepository;
import com.example.timetracker.service.user.UserService;
import com.example.timetracker.validator.project.ProjectPermissionChecker;
import com.example.timetracker.validator.project.ProjectValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private ProjectValidator projectValidator;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserService userService;
    @Mock
    private ProjectPermissionChecker projectPermissionChecker;
    @Mock
    private ProjectParticipantManager projectParticipantManager;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProject() {
        ProjectDto projectDto = new ProjectDto();
        Project project = new Project();

        when(userService.isAdmin(any())).thenReturn(true);
        doNothing().when(projectValidator).validateProjectName(anyString());
        when(projectMapper.toEntity(any(ProjectDto.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.toDto(any(Project.class))).thenReturn(projectDto);

        ProjectDto result = projectService.createProject(projectDto, "admin");

        assertNotNull(result);
        verify(projectRepository).save(any(Project.class));
        verify(projectParticipantManager).addParticipant(any(Project.class), eq("admin"));
    }

    @Test
    void testGetProjectById() {
        Project project = new Project();
        ProjectDto projectDto = new ProjectDto();

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectMapper.toDto(any(Project.class))).thenReturn(projectDto);
        doNothing().when(projectPermissionChecker).checkUserParticipation(anyLong(), anyString());

        ProjectDto result = projectService.getProjectById(1L, "user");

        assertNotNull(result);
        verify(projectRepository).findById(1L);
    }

    @Test
    void testGetProjectByIdNegative() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectDoesNotExistException.class, () -> projectService.getProjectById(1L, "user"));
    }

    @Test
    void testDeleteProject() {
        when(userService.isAdmin(any())).thenReturn(true);
        doNothing().when(projectPermissionChecker).checkUserParticipation(anyLong(), anyString());
        doNothing().when(projectRepository).deleteById(anyLong());

        projectService.deleteProject(1L, "admin");

        verify(projectRepository).deleteById(1L);
    }

    @Test
    void testChangeProjectName() {
        Project project = new Project();
        ProjectDto projectDto = new ProjectDto();

        when(userService.isAdmin(anyString())).thenReturn(true);
        doNothing().when(projectPermissionChecker).checkUserParticipation(anyLong(), anyString());
        doNothing().when(projectValidator).validateProjectName(anyString());
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.toDto(any(Project.class))).thenReturn(projectDto);

        ProjectDto result = projectService.changeProjectName(1L, "newName", "admin");

        assertNotNull(result);
        verify(projectRepository).save(any(Project.class));
        assertEquals("newName", project.getName());
    }

    @Test
    void addUserToProject_shouldAddUser_whenValidRequest() {
        Project project = new Project();
        ProjectDto projectDto = new ProjectDto();

        when(userService.isAdmin(anyString())).thenReturn(true);
        doNothing().when(projectPermissionChecker).checkUserParticipation(anyLong(), anyString());
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        doNothing().when(projectParticipantManager).addParticipant(any(Project.class), anyString());
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.toDto(any(Project.class))).thenReturn(projectDto);

        ProjectDto result = projectService.addUserToProject(1L, "user", "admin");

        assertNotNull(result);
        verify(projectRepository).save(any(Project.class));
        verify(projectParticipantManager).addParticipant(any(Project.class), eq("user"));
    }
}
