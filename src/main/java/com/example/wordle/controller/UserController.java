package com.example.wordle.controller;

import com.example.wordle.dto.LoginStatus;
import com.example.wordle.dto.UserRequestedDto;
import com.example.wordle.dto.UserResponseDto;
import com.example.wordle.mapper.UserMapper;
import com.example.wordle.model.User;
import com.example.wordle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping(path = "/getAllUsers")
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers().stream().map(UserMapper::objectToResponseDto).collect(Collectors.toList());
    }
    @PostMapping("/registerNewUser")
    public ResponseEntity<UserResponseDto> registerNewUser(@RequestBody UserRequestedDto userRequestedDto) {
        // convert DTO to entity
        User userRequest = UserMapper.requestedDtoToObject(userRequestedDto);
        User user = userService.registerNewUser(userRequest);
        // convert entity to DTO
        UserResponseDto userResponseDto = UserMapper.objectToResponseDto(user);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable(name = "id") Long userId, @RequestBody UserRequestedDto userRequestedDto) {
        // convert DTO to Entity
        User userRequest = UserMapper.requestedDtoToObject(userRequestedDto);

        User user = userService.updateUser(userId, userRequest);

        // entity to DTO
        UserResponseDto userResponseDto = UserMapper.objectToResponseDto(user);

        return ResponseEntity.ok().body(userResponseDto);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestBody UserRequestedDto userRequestedDto) {
        User userRequest = UserMapper.requestedDtoToObject(userRequestedDto);
        userService.deleteUser(userRequest);
    }
    @GetMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserRequestedDto userRequestedDto){
        User userRequest = UserMapper.requestedDtoToObject(userRequestedDto);
        User user = userService.getUserByLogin(userRequest.getLogin());
        UserResponseDto userResponseDto = UserMapper.objectToResponseDto(user);
        if (user.getPassword().equals(userRequest.getPassword())){
            userResponseDto.setStatus(LoginStatus.ZALOGOWANY);
            return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
        }
        else {
            userResponseDto.setStatus(LoginStatus.ZLEHASLO);
            return new ResponseEntity<>(userResponseDto, HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<UserResponseDto> logoutUser(@RequestBody UserRequestedDto userRequestedDto){
        User user = UserMapper.requestedDtoToObject(userRequestedDto);
        UserResponseDto userResponseDto = UserMapper.objectToResponseDto(user);
        if (userRequestedDto.getStatus() == LoginStatus.ZALOGOWANY) {
            userResponseDto.setStatus(LoginStatus.WYLOGOWANY);
        }
        return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
    }

}
