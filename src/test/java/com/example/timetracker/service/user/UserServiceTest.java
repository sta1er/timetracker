package com.example.timetracker.service.user;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.entity.user.UserRole;
import com.example.timetracker.exception.user.UserDoesNotExist;
import com.example.timetracker.mapper.UserMapper;
import com.example.timetracker.repository.UserRepository;
import com.example.timetracker.validator.user.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;
    private UserDto adminUserDto;

    private User admin;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(UserRole.USER);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testuser");
        userDto.setPassword("password");
        userDto.setRole("USER");

        adminUserDto = new UserDto();
        adminUserDto.setId(1L);
        adminUserDto.setUsername("testuser");
        adminUserDto.setPassword("password");
        adminUserDto.setRole("ADMIN");

        admin = new User();
        admin.setId(2L);
        admin.setUsername("adminUser");
        admin.setPassword("password");
        admin.setRole(UserRole.ADMIN);
    }

    @Test
    void testCreateUser() {
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        verify(userValidator).validate(userDto);
        verify(passwordEncoder).encode(userDto.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getUsername(), result.getUsername());
    }

    @Test
    void testGetUserByIdNegative() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExist.class, () -> userService.getUserById(1L));
    }

    @Test
    void testChangePassword() {
        String newPassword = "newPassword";
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
        when(userRepository.save(user)).thenReturn(user);

        UserDto result = userService.changePassword(1L, newPassword, "testuser");

        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getUsername(), result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void testChangePasswordNegative() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExist.class, () -> userService.changePassword(1L, "newPassword", "testuser"));
    }

    @Test
    void testGrantAdminRole() {
        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.of(admin));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(adminUserDto);

        UserDto result = userService.grantAdminRole(1L, "adminUser");

        assertNotNull(result);
        assertEquals(UserRole.ADMIN.name(), result.getRole());
        verify(userRepository).save(user);
    }

    @Test
    void testGrantAdminRoleNegative() {
        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExist.class, () -> userService.grantAdminRole(1L, "adminUser"));
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L, "testuser");
        verify(userRepository).deleteById(1L);
    }
}
