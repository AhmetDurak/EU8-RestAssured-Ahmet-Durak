package com.cybertek.Day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class iv_CBTrainingApiWithJsonPath {
    @BeforeAll
    public static void init() {
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://api.cybertektraining.com";

    }

    @DisplayName("GET Request to individual student")
    @Test
    public void test1() {
        //send a get request to student id 32881 as a path parameter and accept header application/json
        Response response = given().accept(ContentType.JSON)
                .when()
                .get("/student/32881");
        //verify status code=200 /content type=application/json;charset=UTF-8 /Content-Encoding = gzip
        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());

        //verify Date header exists
        assertTrue(response.headers().hasHeaderWithName("Date"));
        //assert that
            /*
                firstName Vera
                batch 14
                section 12
                emailAddress aaa@gmail.com
                companyName Cybertek
                state IL
                zipCode 60606

                using JsonPath
             */
        JsonPath jsonPath = response.jsonPath();
        assertEquals("Vera", jsonPath.getString("students[0].firstName"));
        assertEquals(14, jsonPath.getInt("students[0].batch"));
        assertEquals(12, jsonPath.getInt("students[0].section"));
        assertEquals("aaa@gmail.com", jsonPath.getString("students[0].contact.emailAddress"));
        assertEquals("Cybertek", jsonPath.getString("students[0].company.companyName"));
        assertEquals("IL", jsonPath.getString("students[0].company.address.state"));
        assertEquals(60606, jsonPath.getInt("students[0].company.address.zipCode"));
    }
}
