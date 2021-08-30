package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.common.exception.EntityNotFoundException;
import com.whackdackery.openrota.app.common.exception.ServiceException;
import com.whackdackery.openrota.app.common.exception.ValidationException;
import com.whackdackery.openrota.app.common.exception.wrapper.ValidationError;
import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.dto.UserUpdateDto;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.whackdackery.openrota.app.user.UserMocks.singleUser;
import static com.whackdackery.openrota.app.user.UserMocks.singleUserInList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final String USERNAME = "steve";
    public static final String EMAIL = "steve@email.com";

    @Mock
    UserDao userDao;
    @Mock
    UserValidator validator;
    @InjectMocks
    UserService service;

    @Test
    void getAllUsersReturnsUsers() {
        List<User> user = singleUserInList();
        when(userDao.getAllUsers()).thenReturn(user);

        List<User> allUsers = service.getAllUsers();

        assertThat(allUsers).isEqualTo(user);
    }

    @Test
    void getAllUsersReturnsNullUsers() {
        when(userDao.getAllUsers()).thenReturn(null);

        assertThatThrownBy(() -> service.getAllUsers()).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getAllUsersReturnsNoUsers() {
        when(userDao.getAllUsers()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> service.getAllUsers()).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getUserByIdReturnsUser() {
        User user = singleUser();
        when(userDao.getUserById(1)).thenReturn(Optional.of(user));

        User foundUser = service.getUserById(1);

        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void getUserByIdReturnsNoUsers() {
        when(userDao.getUserById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUserById(1)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getUserByUsernameReturnsUser() {
        User user = singleUser();
        when(userDao.getUserByUsername(USERNAME)).thenReturn(Optional.of(user));

        User foundUser = service.getUserByUsername(USERNAME);

        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void getUserByUsernameReturnsNoUsers() {
        when(userDao.getUserByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUserByUsername(USERNAME)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getUserByEmailReturnsUser() {
        User user = singleUser();
        when(userDao.getUserByEmail(EMAIL)).thenReturn(Optional.of(user));

        User foundUser = service.getUserByEmail(EMAIL);

        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void getUserByEmailReturnsNoUsers() {
        when(userDao.getUserByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUserByEmail(EMAIL)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void createUserSuccessfully() {
        UserUpdateDto user = setupUserDto();
        when(userDao.createUser(user)).thenReturn(99);
        when(validator.validate(user)).thenReturn(Collections.emptyList());

        int createdUserId = service.createUser(user);
        assertThat(createdUserId).isEqualTo(99);
    }

    @Test
    void createUserFailsValidation() {
        UserUpdateDto user = setupUserDto();
        when(validator.validate(user)).thenReturn(Collections.singletonList(new ValidationError("Generic error")));

        assertThatThrownBy(() -> {
            service.createUser(user);
        }).isInstanceOf(ValidationException.class);
    }

    @Test
    void failToCreateUserSomehow() {
        UserUpdateDto user = setupUserDto();
        when(userDao.createUser(user)).thenReturn(0);
        assertThatThrownBy(() -> {
            service.createUser(user);
        }).isInstanceOf(ServiceException.class);
    }

    private UserUpdateDto setupUserDto() {
        UserUpdateDto user = new UserUpdateDto();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword("$t3v3");
        return user;
    }
}