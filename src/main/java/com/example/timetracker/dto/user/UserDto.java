package com.example.timetracker.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Username cannot be blank!")
    private String username;

    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 6, message = "Password must be at least 6 characters long!")
    private String password;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
