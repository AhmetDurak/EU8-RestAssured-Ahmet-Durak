package com.cybertek.Day7;

import com.cybertek.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class i_SpartanPostRequestDemo extends SpartanTestBase {
    /*
   Given accept type and Content type is JSON
   And request json body is:
   {
     "gender":"Male",
     "name":"Severus",
     "phone":8877445596
  }
   When user sends POST request to '/api/spartans'
   Then status code 201
   And content type should be application/json
   And json payload/response/body should contain:
   "A Spartan is Born!" message
   and same data what is posted
*/
    @Test
    public void postMethod1() {
        String requestJsonBody = "{\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"name\": \"Valar Moghulis\",\n" +
                "    \"phone\": 1732359831\n" +
                "}";
        Response response = given().accept(ContentType.JSON) //what we are asking from api which is JSON response
                .and().contentType(ContentType.JSON)//what we are sending to api, which is JSON also
                .body(requestJsonBody)
                .when()
                .post("/api/spartans");

        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),equalTo("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";
        assertThat(response.path("success"),is(expectedResponseMessage));
        assertThat(response.path("data.name"),is("Valar Moghulis"));
        assertThat(response.path("data.gender"),is("Female"));
        assertThat(response.path("data.phone"),is(1732359831));
    }

    @DisplayName("POST with Map to JSON")
    @Test
    public void postMethod2(){

        //create a map to keep request json body information
        Map<String,Object> requestJsonMap = new LinkedHashMap<>();
        requestJsonMap.put("name","Valar Moghulis");
        requestJsonMap.put("gender","Female");
        requestJsonMap.put("phone",1732359831);

        Response response = given().accept(ContentType.JSON).and() //what we are asking from api which is JSON response
                .contentType(ContentType.JSON) //what we are sending to api, which is JSON also
                .body(requestJsonMap).log().all() //SERIALIZATION
                .when()
                .post("/api/spartans");

        //verify status code
        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";
        assertThat(response.path("success"),is(expectedResponseMessage));
        assertThat(response.path("data.name"),is("Valar Moghulis"));
        assertThat(response.path("data.gender"),is("Female"));
        assertThat(response.path("data.phone"),is(1732359831));

        response.prettyPrint();
    }
}


































