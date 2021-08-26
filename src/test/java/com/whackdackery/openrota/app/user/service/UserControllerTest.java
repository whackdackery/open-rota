package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.UserWithRoles;
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
        when(service.getAllUsers()).thenReturn(singleUserInList());

        ResponseEntity<List<User>> allUsersResponse = controller.getAllUsers();

        assertThat(allUsersResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(allUsersResponse.getBody()).size().isEqualTo(1);
        assertThat(allUsersResponse.getBody()).isEqualTo(singleUserInList());
    }

    @Test
    void getAllUsersReturnsMultipleUsers() {
        when(service.getAllUsers()).thenReturn(threeUsersInList());

        ResponseEntity<List<User>> allUsersResponse = controller.getAllUsers();

        assertThat(allUsersResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(allUsersResponse.getBody()).size().isEqualTo(3);
        assertThat(allUsersResponse.getBody()).isEqualTo(threeUsersInList());
    }

    @Test
    void getUserReturnsUser() {
        when(service.getUserWithRoles(1)).thenReturn(userWithRoles());

        ResponseEntity<UserWithRoles> userResponse = controller.getUserWithRoles(1);

        assertThat(userResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(userResponse.getBody()).isEqualTo(userWithRoles());
    }

}