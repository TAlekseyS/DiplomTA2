package ru.netology.helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RepeatRequest {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static Response sendRequest(DataHelper.FormData formData, String path, int statusCode) {
        Response response = RestAssured.
                given()
                    .spec(requestSpec)
                    .body(formData)
                .when()
                    .post(path)
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(statusCode)
                .extract().response();

        return response;
    }
}