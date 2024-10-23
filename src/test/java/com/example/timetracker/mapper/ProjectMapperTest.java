package com.example.timetracker.mapper;

import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.entity.project.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectMapperTest {

    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    private final LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 12, 0);
    private final  LocalDateTime updatedAt = LocalDateTime.of(2024, 1, 2, 12, 0);

    private Project project;
    private ProjectDto projectDto;

    @BeforeEach
    public void setUp() {
        project = Project.builder()
                .id(1L)
                .name("testname")
                .description("test description")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        projectDto = ProjectDto.builder()
                .id(1L)
                .name("testname")
                .description("test description")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    @Test
    @DisplayName("Test mapping from Entity to DTO")
    public void testProjectToProjectDto() {
        ProjectDto resultProjectDto = projectMapper.toDto(project);
        assertEquals(projectDto, resultProjectDto);
    }

    @Test
    @DisplayName("Test mapping from DTO to Entity")
    public void testProjectDtoToProject() {
        Project resultProject = projectMapper.toEntity(projectDto);
        assertEquals(project, resultProject);
    }

}