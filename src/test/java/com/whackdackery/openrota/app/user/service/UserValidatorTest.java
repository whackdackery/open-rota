package com.whackdackery.openrota.app.user.service;

import com.whackdackery.openrota.app.common.exception.wrapper.ValidationError;
import com.whackdackery.openrota.app.common.validation.PasswordConstraintValidator;
import com.whackdackery.openrota.app.user.domain.dto.UserUpdateDto;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.whackdackery.openrota.app.user.UserMocks.singleUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    public static final String DUPLICATE_USERNAME = "duplicate";
    public static final String DUPLICATE_EMAIL = "duplicate@email.com";
    public static final String USERNAME = "steve";
    public static final String EMAIL = "steve@email.com";
    public static final String VALID_PASSWORD = "St3ve1sc00l";
    public static final String PASSWORD_TOO_SHORT = "St3ve";
    public static final String PASSWORD_TOO_LONG = "St3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00lSt3ve1sc00l";
    public static final String PASSWORD_WITHOUT_LOWER_CASE = "ST3VE1SC00L";
    public static final String PASSWORD_WITHOUT_UPPER_CASE = "st3ve1sc00l";
    public static final String PASSWORD_WITHOUT_NUMERIC_DIGIT = "Steveiscool";
    public static final String PASSWORD_WITH_SPACES = "St3ve 1s c00l";

    @Mock
    UserDao userDao;
    @Spy
    PasswordConstraintValidator passwordValidator;
    @InjectMocks
    UserValidator validator;

    @Test
    void validateUsernamePresent() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        UserUpdateDto userUpdateDto = setupUserDto(null, EMAIL, VALID_PASSWORD);

        List<ValidationError> validationErrors = validator.validate(userUpdateDto);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).isEqualTo("Username is required");

    }

    @Test
    void validateUsernameIsUnique() {
        when(userDao.getUserByUsername(any())).thenReturn(Optional.of(singleUser()));
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        UserUpdateDto userUpdateDto = setupUserDto(DUPLICATE_USERNAME, EMAIL, VALID_PASSWORD);

        List<ValidationError> validationErrors = validator.validate(userUpdateDto);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage())
                .isEqualTo("Username must be unique. %s is already assigned to another user", DUPLICATE_USERNAME);
    }

    @Test
    void validateEmailPresent() {
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto userUpdateDto = setupUserDto(USERNAME, null, VALID_PASSWORD);

        List<ValidationError> validationErrors = validator.validate(userUpdateDto);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).isEqualTo("Email is required");
    }

    @Test
    void validateEmailIsUnique() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.of(singleUser()));
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto userUpdateDto = setupUserDto(USERNAME, DUPLICATE_EMAIL, VALID_PASSWORD);

        List<ValidationError> validationErrors = validator.validate(userUpdateDto);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage())
                .isEqualTo(String.format("Email must be unique. %s is already assigned to another user", DUPLICATE_EMAIL));
    }

    @Test
    void validatePasswordIsPresent() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto user = setupUserDto(USERNAME, EMAIL, null);

        List<ValidationError> validationErrors = validator.validate(user);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).isEqualTo("Password is required");
    }

    @Test
    void validatePasswordIsTooShort() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto user = setupUserDto(USERNAME, EMAIL, PASSWORD_TOO_SHORT);

        List<ValidationError> validationErrors = validator.validate(user);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).contains("Password security error - [TOO_SHORT]");
    }

    @Test
    void validatePasswordIsTooLong() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto user = setupUserDto(USERNAME, EMAIL, PASSWORD_TOO_LONG);

        List<ValidationError> validationErrors = validator.validate(user);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).contains("Password security error - [TOO_LONG]");
    }

    @Test
    void validatePasswordMissingUpperCase() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto user = setupUserDto(USERNAME, EMAIL, PASSWORD_WITHOUT_UPPER_CASE);

        List<ValidationError> validationErrors = validator.validate(user);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).contains("Password security error - [INSUFFICIENT_UPPERCASE]");
    }

    @Test
    void validatePasswordMissingLowerCase() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto user = setupUserDto(USERNAME, EMAIL, PASSWORD_WITHOUT_LOWER_CASE);

        List<ValidationError> validationErrors = validator.validate(user);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).contains("Password security error - [INSUFFICIENT_LOWERCASE]");
    }

    @Test
    void validatePasswordMissingNumericDigit() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto user = setupUserDto(USERNAME, EMAIL, PASSWORD_WITHOUT_NUMERIC_DIGIT);

        List<ValidationError> validationErrors = validator.validate(user);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).contains("Password security error - [INSUFFICIENT_DIGIT]");
    }

    @Test
    void validatePasswordHasWhitespace() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.empty());
        when(userDao.getUserByUsername(any())).thenReturn(Optional.empty());
        UserUpdateDto user = setupUserDto(USERNAME, EMAIL, PASSWORD_WITH_SPACES);

        List<ValidationError> validationErrors = validator.validate(user);

        assertThat(validationErrors).size().isEqualTo(1);
        assertThat(validationErrors.get(0).getMessage()).contains("Password security error - [ILLEGAL_WHITESPACE]");
    }

    @Test
    void validateAll() {
        when(userDao.getUserByEmail(any())).thenReturn(Optional.of(singleUser()));
        when(userDao.getUserByUsername(any())).thenReturn(Optional.of(singleUser()));
        UserUpdateDto userUpdateDto = setupUserDto(DUPLICATE_USERNAME, DUPLICATE_EMAIL, PASSWORD_TOO_SHORT);

        List<ValidationError> validationErrors = validator.validate(userUpdateDto);

        assertThat(validationErrors).size().isEqualTo(3);
        List<String> errorMessages = validationErrors.stream()
                .map(ValidationError::getMessage)
                .collect(Collectors.toList());

        assertThat(errorMessages).contains(String.format("Email must be unique. %s is already assigned to another user", DUPLICATE_EMAIL),
                String.format("Username must be unique. %s is already assigned to another user", DUPLICATE_USERNAME));
        assertThat(errorMessages).haveAtLeastOne(new Condition<>(errorMessages::contains, "Password security error - [TOO_SHORT]"));
    }

    private UserUpdateDto setupUserDto(String username, String email, String password) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUsername(username);
        userUpdateDto.setEmail(email);
        userUpdateDto.setPassword(password);

        return userUpdateDto;
    }
}