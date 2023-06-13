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

public class DeleteUserSpecs {
    public static RequestSpecification userDeleteSuccessRequestSpec = with()
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON)
            .filter(withCustomTemplates())
            .basePath(API_USERS);

    public static ResponseSpecification userDeleteSuccessResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(STATUS_CODE_NO_CONTENT)
            .build();
}
