package com.example.timetracker.dto.project;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {
    private Long id;
    @NotBlank(message = "Project name cannot be blank!")
    private String name;
    @NotBlank(message = "Project description cannot be blank!")
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
