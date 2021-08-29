package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.common.exception.wrapper.ValidationError;
import com.whackdackery.openrota.app.common.validation.PasswordConstraintValidator;
import com.whackdackery.openrota.app.user.domain.dto.UserUpdateDto;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserValidator {

    private final UserDao userDao;
    private final PasswordConstraintValidator passwordValidator;

    @Autowired
    public UserValidator(UserDao userDao, PasswordConstraintValidator passwordValidator) {
        this.userDao = userDao;
        this.passwordValidator = passwordValidator;
    }

    public List<ValidationError> validate(UserUpdateDto user) {
        List<ValidationError> errors = new ArrayList<>();

        validateUsername(user, errors);
        validateEmail(user, errors);
        validatePassword(user, errors);

        return errors;
    }

    private void validateUsername(UserUpdateDto user, List<ValidationError> errors) {
        if (user.getUsername() == null) {
            errors.add(new ValidationError("Username is required"));
        } else {
            if (userDao.getUserByUsername(user.getUsername()).isPresent()) {
                errors.add(new ValidationError(String.format("Username must be unique. %s is already assigned to another user", user.getUsername())));
            }
        }
    }

    private void validateEmail(UserUpdateDto user, List<ValidationError> errors) {
        if (user.getEmail() == null) {
            errors.add(new ValidationError("Email is required"));
        } else {
            if (userDao.getUserByEmail(user.getEmail()).isPresent()) {
                errors.add(new ValidationError(String.format("Email must be unique. %s is already assigned to another user", user.getEmail())));
            }
        }
    }

    private void validatePassword(UserUpdateDto user, List<ValidationError> errors) {
        if (user.getPassword() == null) {
            errors.add(new ValidationError("Password is required"));
        } else {
            RuleResult validationResult = passwordValidator.validate(user.getPassword());
            if (!validationResult.isValid()) {
                List<String> passwordValidationErrors = validationResult.getDetails().stream()
                        .map(RuleResultDetail::toString)
                        .collect(Collectors.toList());
                passwordValidationErrors.forEach((err) -> errors.add(new ValidationError(String.format("Password security error - %s", err))));
            }
        }
    }
}
