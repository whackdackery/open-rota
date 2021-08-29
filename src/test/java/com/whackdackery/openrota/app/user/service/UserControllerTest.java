package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.dto.UserUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.whackdackery.openrota.app.user.UserMocks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService service;
    @InjectMocks
    UserController controller;

    @Test
    void getAllUsersReturnsSingleUserInList() {
        List<User> user = singleUserInList();
        when(service.getAllUsers()).thenReturn(user);

        ResponseEntity<List<User>> allUsersResponse = controller.getAllUsers();

        assertThat(allUsersResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(allUsersResponse.getBody()).size().isEqualTo(1);
        assertThat(allUsersResponse.getBody()).isEqualTo(user);
    }

    @Test
    void getAllUsersReturnsMultipleUsers() {
        List<User> users = threeUsersInList();
        when(service.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> allUsersResponse = controller.getAllUsers();

        assertThat(allUsersResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(allUsersResponse.getBody()).size().isEqualTo(3);
        assertThat(allUsersResponse.getBody()).isEqualTo(users);
    }

    @Test
    void createUserReturnsNewUserId() {
        UserUpdateDto user = new UserUpdateDto();
        user.setUsername("steve");
        user.setEmail("steve@email.com");
        user.setPassword("$t3v3");
        when(service.createUser(user)).thenReturn(99);

        ResponseEntity<Integer> createdUserResponse = controller.createUser(user);

        assertThat(createdUserResponse.getStatusCode().value()).isEqualTo(207);
        assertThat(createdUserResponse.getBody()).isEqualTo(99);
    }

    @Test
    void getUserReturnsUser() {
        User user = singleUser();
        when(service.getUserById(1)).thenReturn(user);

        ResponseEntity<User> userResponse = controller.getUser(1);

        assertThat(userResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(userResponse.getBody()).isEqualTo(user);
    }

}