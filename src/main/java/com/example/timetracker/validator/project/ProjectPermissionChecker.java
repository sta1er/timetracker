package com.example.timetracker.validator.project;

import com.example.timetracker.entity.project.Project;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.exception.project.ProjectDoesNotExistException;
import com.example.timetracker.exception.user.UserNotParticipantException;
import com.example.timetracker.exception.user.UserDoesNotExistException;
import com.example.timetracker.repository.ProjectRepository;
import com.example.timetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectPermissionChecker {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public void checkUserParticipation(long projectId, String username) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectDoesNotExistException("The project does not exist!"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException("The user does not exist!"));

        if (!project.getUsers().contains(user)) {
            throw new UserNotParticipantException("User is not a participant of the project!");
        }
    }
}
