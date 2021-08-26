package com.whackdackery.openrota.app.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.whackdackery.openrota.ComponentTest;
import com.whackdackery.openrota.app.common.exception.wrapper.Api404;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.whackdackery.openrota.app.user.UserMocks.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class UserComponentTest extends ComponentTest {

    public static final String BASE_URI = "api/users";
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
        when(dao.getUserWithRoles(1)).thenReturn(Optional.of(userWithRoles()));

        given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .body(is(mapper.writeValueAsString(userWithRoles())))
                .statusCode(200);
    }

    @Test
    void getUserReturnsNoUsers() throws JsonProcessingException {
        when(dao.getUserWithRoles(1)).thenReturn(Optional.empty());

        Api404 responseMessage = new Api404("User ID 1 not found");

        given()
                .when()
                .get(BASE_URI + "/1")
                .then()
                .body(is(mapper.writeValueAsString(responseMessage)))
                .statusCode(404);
    }

}
