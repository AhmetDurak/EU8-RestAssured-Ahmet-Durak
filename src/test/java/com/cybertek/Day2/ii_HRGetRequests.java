package com.cybertek.Day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class ii_HRGetRequests {
    //BeforeAll is an annotation equals to @BeforeClass in testNg, we use with static method name
    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "http://54.89.215.84:1000/ords/hr";
    }

    @DisplayName("get request to /regions")
    @Test
    public void test1(){
        Response response = get("/regions");

        System.out.println(response.statusCode());
    }

    /*
        Given accept type is application/json
        When user sends get request to /regions/2
        Then response status code must be 200
        and content type equals to application/json
        and response body contains   Americas
     */

    @DisplayName("get request to /regions/2")
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/regions/2");

        assertEquals(200,response.statusCode());

        assertEquals("application/json",response.contentType());

        System.out.println(response.prettyPrint());

        assertTrue(response.body().asString().contains("Americas"));
    }
}
