package com.example.wordle.controller;

import com.example.wordle.dto.UserRequestedDto;
import com.example.wordle.dto.UserResponseDto;
import com.example.wordle.mapper.UserMapper;
import com.example.wordle.model.User;
import com.example.wordle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/getAllUsers")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/getUserByLogin/{login}")
    public ResponseEntity<UserResponseDto> getUserByLogin(@PathVariable String login) {
        User user= userService.getUserByLogin(login);
        UserResponseDto userResponseDto = UserMapper.objectToResponseDto(user);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/registerNewUser")
    public ResponseEntity<UserResponseDto> registerNewUser(@RequestBody UserRequestedDto userRequestedDto) {
        return new ResponseEntity<>(userService.registerNewUser(userRequestedDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable(name = "id") Long userId, @RequestBody UserRequestedDto userRequestedDto) {
        return ResponseEntity.ok().body(userService.updateUser(userId, userRequestedDto));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserRequestedDto userRequestedDto) {
        return userService.loginUser(userRequestedDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<UserResponseDto> logoutUser(@RequestBody UserRequestedDto userRequestedDto) {
        return userService.logoutUser(userRequestedDto);
    }

}
