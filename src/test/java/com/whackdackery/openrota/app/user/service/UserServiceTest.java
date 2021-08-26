package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.common.exception.EntityNotFoundException;
import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.UserWithRoles;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.whackdackery.openrota.app.user.UserMocks.singleUserInList;
import static com.whackdackery.openrota.app.user.UserMocks.userWithRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserDao userDao;
    @InjectMocks
    UserService service;

    @Test
    void getAllUsersReturnsUsers() {
        when(userDao.getAllUsers()).thenReturn(singleUserInList());

        List<User> allUsers = service.getAllUsers();

        assertThat(allUsers).isEqualTo(singleUserInList());
    }

    @Test
    void getAllUsersReturnsNoUsers() {
        when(userDao.getAllUsers()).thenReturn(null);

        assertThatThrownBy(() -> service.getAllUsers()).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getUserReturnsUser() {
        when(userDao.getUserWithRoles(1)).thenReturn(Optional.of(userWithRoles()));

        UserWithRoles user = service.getUserWithRoles(1);

        assertThat(user).isEqualTo(userWithRoles());
    }

    @Test
    void getUserReturnsNoUsers() {
        when(userDao.getUserWithRoles(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getUserWithRoles(1)).isInstanceOf(EntityNotFoundException.class);
    }
}