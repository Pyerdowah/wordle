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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@WebServlet("/LoginServlet")
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
    @PostMapping("/loginUser")
    public void loginUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException{
        String login = httpServletRequest.getParameter("login");
        String password = httpServletRequest.getParameter("pwd");
        User user = userService.getUserByLogin(login);
        if (user.getPassword().equals(password)){
            //get the old session and invalidate
            HttpSession oldSession = httpServletRequest.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            //generate a new session
            HttpSession newSession = httpServletRequest.getSession(true);
            //setting session to expiry in 5 mins
            newSession.setMaxInactiveInterval(5*60);
            Cookie message = new Cookie("message", "Welcome");
            httpServletResponse.addCookie(message);
            httpServletResponse.sendRedirect("/LoginSuccess.jsp");
        }
        else {
            RequestDispatcher rd = httpServletRequest.getServletContext().getRequestDispatcher("/loginPage.html");
            PrintWriter out = httpServletResponse.getWriter();
            out.println("<font color=red>Either username or password is wrong.</font>");
            rd.include(httpServletRequest, httpServletResponse);
        }
    }
    /*
    @RequestMapping({"/", "/welcome"})
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }
    @GetMapping(value = "/addNewUser")
    public ModelAndView addNewUser() {
        return new ModelAndView("addUser", "user", new User());
    }
    @PostMapping(value = "/addNewUser")
    public ModelAndView processRegistration(@ModelAttribute("user") User user) {
        userService.registerNewUser(user);
        List<User> users = userService.getAllUsers();
        return addToView(users);
    }
    @GetMapping(value = "/getUsers")
    public ModelAndView getEmployees() {
        List<User> users = userService.getAllUsers();
        return addToView(users);
    }
    public ModelAndView addToView(List<User> users){
        ModelAndView model = new ModelAndView("getUsers");
        model.addObject("users", users);
        return model;
    }*/
}
