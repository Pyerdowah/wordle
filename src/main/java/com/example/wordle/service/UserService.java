package com.example.wordle.service;

import com.example.wordle.model.User;
import com.example.wordle.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User registerNewUser(User user){
        Optional<User> newUser = userRepository.findUserByLogin(user.getLogin());
        if (newUser.isPresent()) throw new IllegalStateException("login zajety");
        userRepository.save(user);
        return user;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void deleteUser(User user) {
        userRepository.deleteById(user.getUserId());
    }
    public User getUserByLogin(String login) {
        Optional<User> user = userRepository.findUserByLogin(login);
        if (user.isEmpty()){
            throw new IllegalStateException("nie ma takiego usera");
        }
        return user.get();
    }

    public User updateUser(Long userId, User userRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalStateException("nie ma takiego usera");
        }
        Optional<User> helper = userRepository.findUserByLogin(userRequest.getLogin());
        if (helper.isPresent() && !Objects.equals(helper.get().getUserId(), user.get().getUserId())) {
            throw new IllegalStateException("login zajety");
        }
        user.get().setLogin(userRequest.getLogin());
        user.get().setPassword(userRequest.getPassword());
        userRepository.update(user.get());
        return user.get();
    }
}
