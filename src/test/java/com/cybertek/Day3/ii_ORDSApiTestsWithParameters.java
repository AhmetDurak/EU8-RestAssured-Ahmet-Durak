package com.cybertek.Day3;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ii_ORDSApiTestsWithParameters {
    //BeforeAll is an annotation equals to @BeforeClass in testNg, we use with static method name
    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://54.89.215.84:1000/ords/hr";
    }
        /*
        Given accept type is Json
        And parameters: q = {"region_id":2}
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
     */

    @DisplayName("GET request to /countries with Query Param")
    @Test
    public void test1(){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("q","{\"region_id\":2}");
        Response response = given()
                .accept(ContentType.JSON)
                    .and().queryParams(queryMap)
                    .log().all()
                .when()
                    .get("/countries");

        assertEquals(200, response.statusCode());
        //assertEquals("application/json",response.header("HeaderName"));
        assertEquals("application/json",response.contentType());
        assertTrue(response.body().asString().contains("United States of America"));
    }

    /*
        Send a GET request to employees and get only employees who works as a IT_PROG

     */

    @DisplayName("GET request to /employees with Query Param")
    @Test
    public void test2(){
        Map<String,Object> queryMap  = new HashMap<>();
        queryMap.put("q","{\"job_id\": \"IT_PROG\"}");

        Response response = given()
                .accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .log().all()
                .when()
                .get("/employees");

        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.header("Content-type"));
        assertTrue(response.body().asString().contains("IT_PROG"));
    }
}
