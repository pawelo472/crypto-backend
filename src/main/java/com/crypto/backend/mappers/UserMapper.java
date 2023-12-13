package com.crypto.backend.mappers;

import com.crypto.backend.dtos.SignUpDto;
import com.crypto.backend.dtos.UserDto;
import com.crypto.backend.entites.User;
import com.crypto.backend.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")

public interface UserMapper {


    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "apikey", ignore = true)
    @Mapping(target = "secretapikey", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
