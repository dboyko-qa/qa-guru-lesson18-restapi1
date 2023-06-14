package ru.boyko.darya.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static ru.boyko.darya.helpers.Constants.*;
import static ru.boyko.darya.helpers.CustomAllureListener.withCustomTemplates;

public class Specs {
    public static RequestSpecification baseRequestSpec = with()
            .log().uri()
            .log().body()
            .filter(withCustomTemplates());
    public static ResponseSpecification baseResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification resourceRequestSpec = with()
            .spec(baseRequestSpec)
            .basePath(SINGLE_RESOURCE);
    public static ResponseSpecification resourceResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(STATUS_CODE_SUCCESS)
            .build();
    public static RequestSpecification userRequestSpec = with()
            .spec(baseRequestSpec)
            .basePath(API_USERS);
    public static ResponseSpecification userResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(STATUS_CODE_SUCCESS)
            .build();
    public static RequestSpecification userPostRequestSpec = with()
            .spec(baseRequestSpec)
            .contentType(ContentType.JSON)
            .basePath(API_USERS);
    public static ResponseSpecification userPostResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(STATUS_CODE_CREATED)
            .build();

    public static ResponseSpecification userUnsupportedPostResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(STATUS_CODE_UNSUPPORTED_MEDIA_TYPE)
            .build();
    public static ResponseSpecification userDeleteSuccessResponseSpec = new ResponseSpecBuilder()
            .addResponseSpecification(baseResponseSpec)
            .expectStatusCode(STATUS_CODE_NO_CONTENT)
            .build();
}
