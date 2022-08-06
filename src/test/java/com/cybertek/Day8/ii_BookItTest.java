package com.cybertek.Day8;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ii_BookItTest {
    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "https://cybertek-reservation-api-qa3.herokuapp.com";

    }

    //create BookItUtil then create a method, that accepts email and password return token Bearer +yourToken as a String
    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNTExIiwiYXVkIjoic3R1ZGVudC10ZWFtLW1lbWJlciJ9.hUk04eVldpuCW_7GxU-VxKhhvuvOFon7ZdVf8kr_1l4";

    @DisplayName("GET all campuses")
    @Test
    public void testAuth1(){
        //how to pass bearer token for bookit ? use header method to give as key value header
        given()
                .header("Authorization",getToken())
                .and().accept(ContentType.JSON)
                .when()
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .log().all();
    }

    private String getToken(/*String username, String password*/){
        String token = given().accept(ContentType.JSON)
                .and().queryParams("email", "ryuldashev@gmail.com", "password", "rockoyuldashev")
                .and().log().all()
                .when()
                .get("/sign")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("accessToken");

        return "Bearer " + token;
    }
}
