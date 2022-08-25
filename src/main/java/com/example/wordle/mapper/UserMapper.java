package com.example.wordle.mapper;

import com.example.wordle.dto.UserRequestedDto;
import com.example.wordle.dto.UserResponseDto;
import com.example.wordle.model.User;
public class UserMapper{
    public static User requestedDtoToObject(UserRequestedDto userRequestedDto){
        return User.builder()
                .userId(userRequestedDto.getUserId())
                .login(userRequestedDto.getLogin())
                .password(userRequestedDto.getPassword())
                .build();
    }
    public static UserResponseDto objectToResponseDto(User user){
        return UserResponseDto.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
