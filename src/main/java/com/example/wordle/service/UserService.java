package com.example.wordle.service;

import com.example.wordle.dto.LoginStatus;
import com.example.wordle.dto.UserRequestedDto;
import com.example.wordle.dto.UserResponseDto;
import com.example.wordle.mapper.UserMapper;
import com.example.wordle.model.User;
import com.example.wordle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto registerNewUser(UserRequestedDto userRequestedDto) {
        User userRequest = UserMapper.requestedDtoToObject(userRequestedDto);
        User newUser = userRepository.findUserByLogin(userRequest.getLogin()).orElse(null);
        if (newUser != null) {
            throw new IllegalStateException("login zajety");
        }
        userRepository.save(userRequest);
        return UserMapper.objectToResponseDto(userRequest);
    }

    public List<UserResponseDto> getAllUsers() {
        if (userRepository.findAll() == null) {
            return null;
        }
        return userRepository.findAll().stream()
                .map(UserMapper::objectToResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login).orElse(null);
        if (user == null) {
            throw new IllegalStateException("nie ma takiego usera");
        }
        return user;
    }

    public ResponseEntity<UserResponseDto> loginUser(UserRequestedDto userRequestedDto){
        User userRequest = UserMapper.requestedDtoToObject(userRequestedDto);
        User user = getUserByLogin(userRequest.getLogin());
        UserResponseDto userResponseDto = UserMapper.objectToResponseDto(user);
        if (user.getPassword().equals(userRequest.getPassword())) {
            userResponseDto.setStatus(LoginStatus.LOGGED);
            return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
        } else {
            userResponseDto.setStatus(LoginStatus.WRONGPASSWORD);
            return new ResponseEntity<>(userResponseDto, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<UserResponseDto> logoutUser(UserRequestedDto userRequestedDto) {
        User user = UserMapper.requestedDtoToObject(userRequestedDto);
        UserResponseDto userResponseDto = UserMapper.objectToResponseDto(user);
        if (userRequestedDto.getStatus() == LoginStatus.LOGGED) {
            userResponseDto.setStatus(LoginStatus.NOTLOGGED);
        }
        return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
    }

    public UserResponseDto updateUser(Long userId, UserRequestedDto userRequestedDto) {
        User userRequest = UserMapper.requestedDtoToObject(userRequestedDto);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalStateException("nie ma takiego usera");
        }
        Optional<User> helper = userRepository.findUserByLogin(userRequest.getLogin());
        if (helper.isPresent() && !Objects.equals(helper.get().getUserId(), user.getUserId())) {
            throw new IllegalStateException("login zajety");
        }
        user.setLogin(userRequest.getLogin());
        user.setPassword(userRequest.getPassword());
        userRepository.update(user);
        return UserMapper.objectToResponseDto(user);
    }
}
