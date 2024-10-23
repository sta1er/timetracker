package com.example.timetracker.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProjectNameRequest {
    @NotNull(message = "Project id cannot be null!")
    private long projectId;
    @NotBlank(message = "Project name cannot be blank!")
    private String newProjectName;
}
