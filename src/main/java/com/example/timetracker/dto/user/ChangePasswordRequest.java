package com.example.timetracker.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotNull(message = "User id cannot be null!")
    private long userId;
    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 6, message = "Password must be at least 6 characters long!")
    private String newPassword;
}
