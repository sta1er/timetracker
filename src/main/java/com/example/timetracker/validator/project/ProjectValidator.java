package com.example.timetracker.validator.project;

import com.example.timetracker.dto.project.ProjectDto;
import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.exception.project.ProjectNameAlreadyExistsException;
import com.example.timetracker.exception.project.ProjectNameInvalidException;
import com.example.timetracker.exception.user.UsernameAlreadyExistsException;
import com.example.timetracker.exception.user.UsernameInvalidException;
import com.example.timetracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ProjectValidator {

    private final ProjectRepository projectRepository;

    @Value("${validation.name-pattern}")
    private Pattern namePattern;

    public void validateProjectName(String name) {
        if (!namePattern.matcher(name).matches()) {
            throw new ProjectNameInvalidException("Project name can only contain letters and digits!");
        }
        if (projectRepository.existsByName(name)) {
            throw new ProjectNameAlreadyExistsException("Project name already exists!");
        }
    }
}
