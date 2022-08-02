package com.cybertek.Day1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class SimpleGetRequest {

    String url = "http://54.89.215.84:8000/api/spartans";

    @Test
    public void test1(){
        Response response = RestAssured.get(url);

        //send a get request and save response inside the Response object
        System.out.println(response.statusCode());

        //print response header
        System.out.println(response.headers());

        //print response body
        //System.out.println(response.prettyPrint());
    }
}
