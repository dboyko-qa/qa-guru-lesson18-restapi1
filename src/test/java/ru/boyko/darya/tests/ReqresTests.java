package ru.boyko.darya.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.boyko.darya.models.EmployeerUser;
import ru.boyko.darya.models.ResourceResponseModel;
import ru.boyko.darya.models.UserCreateResponseModel;
import ru.boyko.darya.models.UserGetResponseModel;
import ru.boyko.darya.specifications.Specs;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.boyko.darya.specifications.Specs.*;

public class ReqresTests {

    @BeforeEach
    public void beforeEach() {
        baseURI = "https://reqres.in";
    }

    @Test
    public void getSingleResourceTest() {
        Integer resourceId = 2;
        String resourceName = "fuchsia rose";
        ResourceResponseModel resourceResponse = step("Request resource", () ->
                given()
                        .spec(resourceRequestSpec)
                        .get("/" + resourceId)
                        .then()
                        .spec(resourceResponseSpec)
                        .extract().as(ResourceResponseModel.class)
        );

        step("Verify resource name", () ->
                assertEquals(resourceName, resourceResponse.getData().getName()));
    }

    @Test
    public void getUsersListTest() {
        Integer page = 1;
        Integer itemsPerPage = 6;
        Integer userIdForVerification = 1;

        UserGetResponseModel userResponse = step("Request users list", () ->
                given()
                        .spec(userRequestSpec)
                        .params("page", page)
                        .when()
                        .get()
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UserGetResponseModel.class)
        );

        step("Verify result", () -> assertAll(
                () -> assertEquals(page, userResponse.getPage()),
                () -> assertEquals(itemsPerPage, userResponse.getPer_page()),
                () -> assertEquals(itemsPerPage, userResponse.getData().size()),
                () -> assertEquals(userIdForVerification, userResponse.getData().get(userIdForVerification - 1).getId())
        ));
    }

    @Test
    public void createUserPostTest() {
        // method shows using Lombok plugin for sending and receiving data in REST commands
        String userName = "morpheus";
        String userJob = "leader";

        EmployeerUser employeerUser = new EmployeerUser();
        employeerUser.setName(userName);
        employeerUser.setJob(userJob);

        UserCreateResponseModel userCreateResponse = step("Send create user request",
                () -> given()
                        .spec(Specs.userPostRequestSpec)
                        .body(employeerUser)
                        .when()
                        .post()
                        .then()
                        .spec(Specs.userPostResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemes/create-user-scheme.json"))
                        .extract().as(UserCreateResponseModel.class));

        step("Verify result", () -> assertAll(
                () -> assertEquals(userName, userCreateResponse.getName()),
                () -> assertEquals(userJob, userCreateResponse.getJob())
        ));
    }

    @Test
    public void createUserPostEmptyBodyTest() {
        step("Send request and verify status code", () -> given()
                .spec(Specs.userRequestSpec)
                .when()
                .post()
                .then()
                .spec(Specs.userUnsupportedPostResponseSpec));
    }

    @Test
    public void deleteUserTest() {
        Integer userId = 1;
        step("Send request and verify status code", () -> given()
                .spec(userPostRequestSpec)
                .delete("/" + userId)
                .then()
                .spec(userDeleteSuccessResponseSpec)
        );
    }
}
