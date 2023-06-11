package ru.boyko.darya.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.boyko.darya.models.User;
import ru.boyko.darya.models.UserCreateResponseModel;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.boyko.darya.helpers.CustomAllureListener.withCustomTemplates;

public class ReqresTests {

    public static final String SINGLE_RESOURCE = "/api/unknown";
    public static final String API_USERS = "api/users";
    public static final Integer STATUS_CODE_SUCCESS = 200;
    public static final Integer STATUS_CODE_CREATED = 201;
    public static final Integer STATUS_CODE_UNSUPPORTED_MEDIA_TYPE = 415;

     @BeforeEach
    public void beforeEach(){
        baseURI = "https://reqres.in";
        RestAssured.filters(withCustomTemplates()); //

    }

    @Test
    public void getSingleResourceTest() {
        Integer resourceId = 2;
        String resourceName = "fuchsia rose";
        get(SINGLE_RESOURCE + "/" + resourceId.toString())
                .then()
                .log().body()
                .statusCode(STATUS_CODE_SUCCESS)
                .body("data.name", is(resourceName));
    }

    @Test
    public void getUsersListTest(){
        Integer page = 1;
        Integer itemsPerPage = 6;

        given()
        .log().uri()
                .params("page", page.toString())
                .get(API_USERS)
                .then()
                .log().body()
                .statusCode(STATUS_CODE_SUCCESS)
                .body("page", equalTo(page))
                .body("per_page",is(itemsPerPage))
                .body("data.size()", is(itemsPerPage));
    }

    @Test
    public void createUserPostTest(){
        // method shows using Lombok plugin for sending and receiving data in REST commands
        String userName ="morpheus";
        String userJob = "leader";

        User user = new User();
        user.setName(userName);
        user.setJob(userJob);

        UserCreateResponseModel userCreateResponse = given()
                .log().uri()
                .log().body(true)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(API_USERS)
                .then()
                .log().body()
                .statusCode(STATUS_CODE_CREATED)
                .body(matchesJsonSchemaInClasspath("schemes/create-user-scheme.json"))
                .extract().as(UserCreateResponseModel.class);

        assertAll(() -> assertEquals(userName, userCreateResponse.getName()),
        () -> assertEquals(userJob, userCreateResponse.getJob()));
    }

    @Test
    public void createUserPostEmptyBodyTest() {
        given()
                .when()
                .post(API_USERS)
                .then()
                .log().body()
                .statusCode(STATUS_CODE_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void deleteUserTest(){
        Integer userId = 1;
        given()
                .delete(API_USERS + "/" + userId)
                .then()
                .statusCode(204);
    }
}
