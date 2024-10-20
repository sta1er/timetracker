package com.example.timetracker.mapper;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.entity.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 12, 0);
    private final  LocalDateTime updatedAt = LocalDateTime.of(2024, 1, 2, 12, 0);

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {

        user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password123")
                .role(UserRole.ROLE_USER)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .username("testuser")
                .password("password123")
                .role("ROLE_USER")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    @Test
    @DisplayName("Test mapping from Entity to DTO")
    public void testUserToUserDto() {
        UserDto resultUserDto = userMapper.toDto(user);
        assertEquals(userDto, resultUserDto);
    }

    @Test
    @DisplayName("Test mapping from DTO to Entity")
    public void testUserDtoToUser() {
        User resultUser = userMapper.toEntity(userDto);
        assertEquals(user, resultUser);
    }
}
