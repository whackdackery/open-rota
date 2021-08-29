package com.whackdackery.openrota.app.common.exception;

import com.whackdackery.openrota.ComponentTest;
import com.whackdackery.openrota.app.common.exception.wrapper.ApiError;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTest extends ComponentTest {

    public static final String BASE_URI = "/api";

    void handleMissingServletRequestParameter() {

    }

    void handleConstraintViolation() {

    }

    @Test
    void handleMethodArgumentTypeMismatch() {
        ApiError apiError = given()
                .when()
                .get(BASE_URI + "/users/stringvalue")
                .then()
                .statusCode(400)
                .extract()
                .as(ApiError.class);

        assertThat(apiError.getErrors().get(0)).contains("should be of type");
    }

    @Test
    void handleHttpRequestMethodNotSupported() {
        ApiError apiError = given()
                .when()
                .patch(BASE_URI + "/users")
                .then()
                .statusCode(405)
                .extract()
                .as(ApiError.class);

        assertThat(apiError.getErrors().get(0)).contains("method is not supported");
    }

    @Test
    void handleHttpMediaTypeNotSupported() {

        ApiError apiError = given()
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .when()
                .get(BASE_URI + "/users")
                .then()
                .statusCode(415)
                .extract()
                .as(ApiError.class);

        assertThat(apiError.getErrors().get(0)).contains("Supported media types are application/json");
    }

    @Test
    void handleNoHandlerFoundException() {
        ApiError apiError = given()
                .when()
                .get(BASE_URI + "/steve")
                .then()
                .statusCode(404)
                .extract()
                .as(ApiError.class);

        assertThat(apiError.getErrors().get(0)).contains("No request handler found");
    }
}