package com.example.timetracker.mapper;

import com.example.timetracker.dto.time.record.TimeRecordDto;
import com.example.timetracker.entity.record.TimeRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TimeRecordMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "project.id", target = "projectId")
    TimeRecordDto toDto(TimeRecord timeRecord);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "project", ignore = true)
    TimeRecord toEntity(TimeRecordDto timeRecordDto);
}
