package com.example.timetracker.dto.time.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeRecordDto {

    private Long id;
    @NotBlank(message = "Record description cannot be blank!")
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull(message = "Start time cannot be null!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @NotNull(message = "End time cannot be null!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private long userId;
    @NotNull(message = "Project id cannot be null!")
    private long projectId;

}
