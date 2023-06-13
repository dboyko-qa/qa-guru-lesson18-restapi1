package ru.boyko.darya.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static ru.boyko.darya.helpers.Constants.*;
import static ru.boyko.darya.helpers.CustomAllureListener.withCustomTemplates;

public class GetUserSpecs {
    public static RequestSpecification userRequestSpec = with()
            .log().uri()
            .log().body()
            .filter(withCustomTemplates())
            .basePath(API_USERS);

    public static ResponseSpecification userResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(STATUS_CODE_SUCCESS)
            .build();
}
