package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.common.exception.EntityNotFoundException;
import com.whackdackery.openrota.app.common.exception.ServiceException;
import com.whackdackery.openrota.app.common.exception.ValidationException;
import com.whackdackery.openrota.app.common.exception.wrapper.ValidationError;
import com.whackdackery.openrota.app.user.domain.User;
import com.whackdackery.openrota.app.user.domain.dto.UserUpdateDto;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserValidator validator;

    @Autowired
    public UserService(UserDao userDao, UserValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        if (allUsers == null || allUsers.isEmpty()) {
            throw new EntityNotFoundException("No users found");
        }
        return allUsers;
    }

    public User getUserById(int userId) {
        Optional<User> user = userDao.getUserById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(String.format("User ID %d not found", userId));
        }
        return user.get();
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userDao.getUserByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(String.format("Username %s not found", username));
        }
        return user.get();
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userDao.getUserByEmail(email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(String.format("User with email %s not found", email));
        }
        return user.get();
    }

    public int createUser(UserUpdateDto userUpdateDto) {
        List<ValidationError> errors = validator.validate(userUpdateDto);
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed", errors);
        }

        int createdUserId = userDao.createUser(userUpdateDto);
        if (!(createdUserId > 0)) {
            throw new ServiceException("Failed to create User. Unknown error");
        }
        return createdUserId;
    }
}
