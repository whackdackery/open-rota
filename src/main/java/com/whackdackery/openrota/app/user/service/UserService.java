package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.UserWithRoles;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    public Optional<UserWithRoles> getUserWithRoles(Long userId){
        return userDao.getUserWithRoles(userId);
    }
}
