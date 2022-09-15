package com.example.wordle.service;

import com.example.wordle.dto.LoginStatus;
import com.example.wordle.dto.UserRequestedDto;
import com.example.wordle.dto.UserResponseDto;
import com.example.wordle.model.User;
import com.example.wordle.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Test
    public void register_new_user_to_add() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", null);

        UserResponseDto userResponseDto = new UserResponseDto(1L, "test", "test", null);
        //when
        when(userRepository.save(any(User.class))).thenReturn(user);
        //then
        UserResponseDto userResponseDtoTest = userService.registerNewUser(userRequestedDto);
        assertEquals(userResponseDto.getUserId(), userResponseDtoTest.getUserId());
        assertEquals(userResponseDto.getPassword(), userResponseDtoTest.getPassword());
        assertEquals(userResponseDto.getLogin(), userResponseDtoTest.getPassword());
        assertEquals(userResponseDto.getStatus(), userResponseDtoTest.getStatus());
    }

    @Test(expected = IllegalStateException.class)
    public void register_new_user_to_exception() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", null);
        //when
        when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        //then
        userService.registerNewUser(userRequestedDto);
    }

    @Test
    public void get_all_users_to_list() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        UserResponseDto userResponseDto1 = new UserResponseDto(1L, "test", "test", null);
        UserResponseDto userResponseDto2 = new UserResponseDto(2L, "test", "test", null);
        UserResponseDto userResponseDto3 = new UserResponseDto(3L, "test", "test", null);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        userResponseDtoList.add(userResponseDto1);
        userResponseDtoList.add(userResponseDto2);
        userResponseDtoList.add(userResponseDto3);
        User user1 = new User(1L, "test", "test");
        User user2 = new User(2L, "test", "test");
        User user3 = new User(3L, "test", "test");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        //when
        when(userRepository.findAll()).thenReturn(userList);
        //then
        List<UserResponseDto> userResponseDtoListTest = userService.getAllUsers();
        for (int i = 0; i < userResponseDtoList.size(); i++) {
            assertEquals(userResponseDtoList.get(i).getUserId(), userResponseDtoListTest.get(i).getUserId());
            assertEquals(userResponseDtoList.get(i).getLogin(), userResponseDtoListTest.get(i).getLogin());
            assertEquals(userResponseDtoList.get(i).getPassword(), userResponseDtoListTest.get(i).getPassword());
            assertEquals(userResponseDtoList.get(i).getStatus(), userResponseDtoListTest.get(i).getStatus());
        }
    }

    @Test
    public void get_all_users_to_empty_list() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        //when
        when(userRepository.findAll()).thenReturn(null);
        //then
        List<UserResponseDto> userResponseDtoList = userService.getAllUsers();
        assertNull(userResponseDtoList);
    }

    @Test
    public void delete_user() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        //when
        userService.deleteUser(user.getUserId());
        //then
        verify(userRepository).deleteById(user.getUserId());
    }

    @Test
    public void get_user_by_login_to_user() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        //when
        when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        //then
        User userTest = userService.getUserByLogin(user.getLogin());
        assertEquals(user.getUserId(), userTest.getUserId());
        assertEquals(user.getLogin(), userTest.getLogin());
        assertEquals(user.getPassword(), userTest.getPassword());
    }

    @Test(expected = IllegalStateException.class)
    public void get_user_by_login_to_exception() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        //when
        assertThat(userRepository.findUserByLogin(user.getLogin())).isEmpty();
        //then
        userService.getUserByLogin(user.getLogin());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void update_user_to_no_user_exception() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", null);
        //when
        assertThat(userRepository.findById(user.getUserId())).isEmpty();
        //then
        exception.expect(IllegalStateException.class);
        exception.expectMessage("nie ma takiego usera");
        userService.updateUser(user.getUserId(), userRequestedDto);
    }

    @Test
    public void update_user_to_login_used_exception() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        User user2 = new User(2L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", null);
        //when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user2));
        //then
        exception.expect(IllegalStateException.class);
        exception.expectMessage("login zajety");
        userService.updateUser(user.getUserId(), userRequestedDto);
    }

    @Test
    public void update_user_to_update() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        //given
        User user = new User(1L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", null);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "test", "test", null);
        //when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        assertThat(userRepository.findUserByLogin(user.getLogin())).isEmpty();
        //then
        UserResponseDto userResponseDtoTest = userService.updateUser(user.getUserId(), userRequestedDto);
        assertEquals(userResponseDto.getUserId(), userResponseDtoTest.getUserId());
        assertEquals(userResponseDto.getPassword(), userResponseDtoTest.getPassword());
        assertEquals(userResponseDto.getLogin(), userResponseDtoTest.getLogin());
        assertEquals(userResponseDto.getStatus(), userResponseDtoTest.getStatus());
    }

    @Test
    public void login_user_to_accepted() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        UserService userServiceMock = Mockito.mock(UserService.class);
        //given
        User user = new User(1L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", null);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "test", "test", LoginStatus.LOGGED);
        //when
        when(userServiceMock.getUserByLogin(user.getLogin())).thenReturn(user);
        when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        //then
        ResponseEntity<UserResponseDto> userResponseDtoTest = userService.loginUser(userRequestedDto);
        assertEquals(userResponseDtoTest.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(userResponseDto.getUserId(), Objects.requireNonNull(userResponseDtoTest.getBody()).getUserId());
        assertEquals(userResponseDto.getPassword(), userResponseDtoTest.getBody().getPassword());
        assertEquals(userResponseDto.getLogin(), userResponseDtoTest.getBody().getLogin());
        assertEquals(userResponseDto.getStatus(), userResponseDtoTest.getBody().getStatus());
    }

    @Test
    public void login_user_to_conflict() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        UserService userServiceMock = Mockito.mock(UserService.class);
        //given
        User user = new User(1L, "test", "ppppp");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", null);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "test", "test", LoginStatus.WRONGPASSWORD);
        //when
        when(userServiceMock.getUserByLogin(user.getLogin())).thenReturn(user);
        when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        //then
        ResponseEntity<UserResponseDto> userResponseDtoTest = userService.loginUser(userRequestedDto);
        assertEquals(userResponseDtoTest.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(userResponseDto.getUserId(), Objects.requireNonNull(userResponseDtoTest.getBody()).getUserId());
        assertNotEquals(userResponseDto.getPassword(), userResponseDtoTest.getBody().getPassword());
        assertEquals(userResponseDto.getLogin(), userResponseDtoTest.getBody().getLogin());
        assertEquals(userResponseDto.getStatus(), userResponseDtoTest.getBody().getStatus());
    }

    @Test
    public void logout_user_if_logged() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        UserService userServiceMock = Mockito.mock(UserService.class);
        //given
        User user = new User(1L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", LoginStatus.LOGGED);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "test", "test", LoginStatus.NOTLOGGED);
        //when
        when(userServiceMock.getUserByLogin(user.getLogin())).thenReturn(user);
        when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        //then
        ResponseEntity<UserResponseDto> userResponseDtoTest = userService.logoutUser(userRequestedDto);
        assertEquals(userResponseDtoTest.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(userResponseDto.getUserId(), Objects.requireNonNull(userResponseDtoTest.getBody()).getUserId());
        assertEquals(userResponseDto.getPassword(), userResponseDtoTest.getBody().getPassword());
        assertEquals(userResponseDto.getLogin(), userResponseDtoTest.getBody().getLogin());
        assertEquals(userResponseDto.getStatus(), userResponseDtoTest.getBody().getStatus());
    }

    @Test
    public void logout_user_if_not_logged() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        UserService userServiceMock = Mockito.mock(UserService.class);
        //given
        User user = new User(1L, "test", "test");
        UserRequestedDto userRequestedDto = new UserRequestedDto(1L, "test", "test", LoginStatus.NOTLOGGED);
        UserResponseDto userResponseDto = new UserResponseDto(1L, "test", "test", null);
        //when
        when(userServiceMock.getUserByLogin(user.getLogin())).thenReturn(user);
        when(userRepository.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        //then
        ResponseEntity<UserResponseDto> userResponseDtoTest = userService.logoutUser(userRequestedDto);
        assertEquals(userResponseDtoTest.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(userResponseDto.getUserId(), Objects.requireNonNull(userResponseDtoTest.getBody()).getUserId());
        assertEquals(userResponseDto.getPassword(), userResponseDtoTest.getBody().getPassword());
        assertEquals(userResponseDto.getLogin(), userResponseDtoTest.getBody().getLogin());
        assertEquals(userResponseDto.getStatus(), userResponseDtoTest.getBody().getStatus());
    }

}
