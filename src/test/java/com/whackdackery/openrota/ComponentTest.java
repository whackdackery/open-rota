package com.whackdackery.openrota;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whackdackery.openrota.app.user.service.dao.UserDao;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OpenRotaApplication.class)
@ActiveProfiles("test")
public class ComponentTest {

    @LocalServerPort
    public int port;
    @Autowired
    public ObjectMapper mapper;

    @BeforeEach
    public void before() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .log(LogDetail.ALL)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .build();
    }


}
