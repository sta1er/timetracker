package com.example.timetracker.mapper;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
