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

public class PostUserSpecs {
    public static RequestSpecification userPostRequestSpec = with()
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates())
            .basePath(API_USERS);

    public static ResponseSpecification userPostResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(STATUS_CODE_CREATED)
            .build();

    public static RequestSpecification userEmptyPostRequestSpec = with()
            .log().uri()
            .log().body()
            .filter(withCustomTemplates())
            .basePath(API_USERS);

    public static ResponseSpecification userUnsupportedPostResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(STATUS_CODE_UNSUPPORTED_MEDIA_TYPE)
            .build();

}
