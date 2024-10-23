package com.example.timetracker.service.project;

import com.example.timetracker.entity.project.Project;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.exception.user.UserAlreadyParticipantException;
import com.example.timetracker.exception.user.UserDoesNotExistException;
import com.example.timetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectParticipantManager {

    private final UserRepository userRepository;

    public void addParticipant(Project project, String participationUsername) {
        User userToAdd = userRepository.findByUsername(participationUsername)
                .orElseThrow(() -> new UserDoesNotExistException("The user does not exist!"));


        if(project.getUsers() == null) {
            project.setUsers(List.of(userToAdd));
            return;
        }

        if (project.getUsers().contains(userToAdd)) {
            throw new UserAlreadyParticipantException("User is already a participant of the project!");
        }

        project.getUsers().add(userToAdd);
    }
}
