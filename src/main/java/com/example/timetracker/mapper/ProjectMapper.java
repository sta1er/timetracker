package com.example.timetracker.mapper;

import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.entity.project.Project;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    ProjectDto toDto(Project project);
    Project toEntity(ProjectDto projectDto);
}
