package com.example.timetracker.service.project;

import com.example.timetracker.entity.project.Project;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.exception.user.UserAlreadyParticipantException;
import com.example.timetracker.exception.user.UserDoesNotExistException;
import com.example.timetracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProjectParticipantManagerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectParticipantManager projectParticipantManager;

    private Project project;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project();
        project.setUsers(new ArrayList<>());

        user = new User();
        user.setUsername("testUser");
    }

    @Test
    void addParticipant_shouldAddUser_whenUserDoesNotExistInProject() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        projectParticipantManager.addParticipant(project, "testUser");

        assertTrue(project.getUsers().contains(user));
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void addParticipant_shouldThrowException_whenUserAlreadyExistsInProject() {
        project.getUsers().add(user);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyParticipantException.class, () -> {
            projectParticipantManager.addParticipant(project, "testUser");
        });

        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void addParticipant_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class, () -> {
            projectParticipantManager.addParticipant(project, "nonExistentUser");
        });

        verify(userRepository, times(1)).findByUsername("nonExistentUser");
    }

    @Test
    void addParticipant_shouldSetUserList_whenProjectHasNoUsers() {
        project.setUsers(null);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        projectParticipantManager.addParticipant(project, "testUser");

        assertNotNull(project.getUsers());
        assertTrue(project.getUsers().contains(user));

        verify(userRepository, times(1)).findByUsername("testUser");
    }
}
