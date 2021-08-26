package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.common.exception.EntityNotFoundException;
import com.whackdackery.openrota.app.common.exception.RestExceptionHandler;
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
        List<User> allUsers = userDao.getAllUsers();
        if (allUsers == null || allUsers.isEmpty()){
            throw new EntityNotFoundException("No users found");
        }
        return allUsers;
    }

    public UserWithRoles getUserWithRoles(int userId){
        Optional<UserWithRoles> user = userDao.getUserWithRoles(userId);
        if (user.isEmpty()){
            throw new EntityNotFoundException(String.format("User ID %d not found", userId));
        }
        return user.get();
    }
}
