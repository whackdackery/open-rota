package com.whackdackery.openrota.app.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whackdackery.openrota.ComponentTest;
import com.whackdackery.openrota.app.common.exception.wrapper.Api404;
import com.whackdackery.openrota.app.common.exception.wrapper.ApiError;
import com.whackdackery.openrota.app.user.domain.dto.UserUpdateDto;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.whackdackery.openrota.app.user.UserMocks.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserComponentTest extends ComponentTest {

    public static final String BASE_URI = "api/users";
    public static final String USERNAME = "Steve";
    public static final String EMAIL = "steve@email.com";
    public static final String PASSWORD = "St3ve1sc00l";

    @MockBean
    UserDao dao;

    @Test
    void getUsersReturnsUsers() throws JsonProcessingException {
        when(dao.getAllUsers()).thenReturn(threeUsersInList());

        given()
                .when()
                .get(BASE_URI)
                .then()
                .body(is(mapper.writeValueAsString(threeUsersInList())))
                .statusCode(200);
    }

    @Test
    void getUsersReturnsNoUsers() throws JsonProcessingException {
        when(dao.getAllUsers()).thenReturn(null);

        Api404 responseMessage = new Api404("No users found");

        given()
                .when()
                .get(BASE_URI)
                .then()
                .body(is(mapper.writeValueAsString(responseMessage)))
                .statusCode(404);
    }

    @Test
    void getUserReturnsUser() throws JsonProcessingException {
        when(dao.getUserById(1)).thenReturn(Optional.of(singleUser()));

        given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .body(is(mapper.writeValueAsString(Optional.of(singleUser()))))
                .statusCode(200);
    }

    @Test
    void getUserReturnsNoUsers() throws JsonProcessingException {
        when(dao.getUserById(1)).thenReturn(Optional.empty());

        Api404 responseMessage = new Api404("User ID 1 not found");

        given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .body(is(mapper.writeValueAsString(responseMessage)))
                .statusCode(404);
    }

    @Test
    void createUserCreatesUser() throws JsonProcessingException {
        UserUpdateDto user = new UserUpdateDto();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        when(dao.getUserByUsername(USERNAME)).thenReturn(Optional.empty());
        when(dao.getUserByEmail(EMAIL)).thenReturn(Optional.empty());
        when(dao.createUser(user)).thenReturn(99);

        given()
                .when()
                .body(mapper.writeValueAsString(user))
                .post(BASE_URI)
                .then()
                .statusCode(207)
                .body(is("99"));
    }

    @Test
    void createUserFailsValidation() throws JsonProcessingException {
        UserUpdateDto user = new UserUpdateDto();
        user.setUsername(null);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        when(dao.getUserByUsername(USERNAME)).thenReturn(Optional.empty());
        when(dao.getUserByEmail(EMAIL)).thenReturn(Optional.empty());
        when(dao.createUser(user)).thenReturn(99);

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation failed", "Username is required");

        given()
                .when()
                .body(mapper.writeValueAsString(user))
                .post(BASE_URI)
                .then()
                .statusCode(400)
                .body(is(mapper.writeValueAsString(apiError)));
    }

    @Test
    void genericExceptionIsCaught() throws JsonProcessingException {
        when(dao.getAllUsers()).thenThrow(NullPointerException.class);

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, null, "Unhandled error occurred");

        given()
                .when()
                .get(BASE_URI)
                .then()
                .body(is(mapper.writeValueAsString(apiError)))
                .statusCode(500);
    }

}
