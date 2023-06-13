package ru.boyko.darya.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static ru.boyko.darya.helpers.CustomAllureListener.withCustomTemplates;
import static ru.boyko.darya.helpers.Constants.*;

public class GetResourceSpecs {

    public static RequestSpecification resourceRequestSpec = with()
            .log().uri()
            .log().body()
            .filter(withCustomTemplates())
            .basePath(SINGLE_RESOURCE);

    public static ResponseSpecification resourceResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(STATUS_CODE_SUCCESS)
            .build();


}
