package com.example.timetracker.mapper;

import com.example.timetracker.dto.user.UserDto;
import com.example.timetracker.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(source = "username", target = "username")
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
