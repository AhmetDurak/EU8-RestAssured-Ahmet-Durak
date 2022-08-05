package com.cybertek.Day7;

import com.cybertek.pojo.Spartan;
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


    @DisplayName("POST with Map to Spartan Class with POJO")
    @Test
    public void postMethod3(){

        //create one object from your pojo, send it as a JSON
        Spartan spartan = new Spartan();
        spartan.setName("Valar Moghulis");
        spartan.setGender("Female");
        spartan.setPhone(1732359831);

        System.out.println("Spartan = " + spartan);

        Response response = given().accept(ContentType.JSON).and() //what we are asking from api which is JSON response
                .contentType(ContentType.JSON) //what we are sending to api, which is JSON also
                .body(spartan).log().all() //SERIALIZATION
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


    /**
     * This method provides you, how you can post a Spartan and saving its id, and then you can get it to verify
     */
    @DisplayName("POST with Map to Spartan Class")
    @Test
    public void postMethod4(){
        //this example we implement serialization with creating spartan object sending as a request body
        //also implemented deserialization getting the id,sending get request and saving that body as a response

        //create one object from your pojo, send it as a JSON
        Spartan spartan = new Spartan();
        spartan.setName("BruceWayne");
        spartan.setGender("Male");
        spartan.setPhone(8877445596L);

        System.out.println("spartan = " + spartan);
        String expectedResponseMessage = "A Spartan is Born!";

        int idFromPost = given().accept(ContentType.JSON).and() //what we are asking from api which is JSON response
                .contentType(ContentType.JSON) //what we are sending to api, which is JSON also
                .body(spartan).log().all()
                .when()
                .post("/api/spartans")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is(expectedResponseMessage))
                .extract().jsonPath().getInt("data.id");

        System.out.println("idFromPost = " + idFromPost);
        //send a get request to id
        Spartan spartanPosted = given().accept(ContentType.JSON)
                .and().pathParam("id", idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all().extract().as(Spartan.class);

        assertThat(spartanPosted.getName(),is(spartan.getName()));
        assertThat(spartanPosted.getGender(),is(spartan.getGender()));
        assertThat(spartanPosted.getPhone(),is(spartan.getPhone()));
        assertThat(spartanPosted.getId(),is(idFromPost));

    }


    //Create one SpartanUtil class
    //create a static method that returns Map<String,Object>
    //use faker library(add as a dependency) to assign each time different information
    //for name,gender,phone number
    //then use your method for creating spartan as a map,dynamically.

    @DisplayName("POST with Map to Spartan Class with POJO")
    @Test
    public void postMethodFaker(){

        //create one object from your pojo, send it as a JSON
        Map<String,Object> spartan = generateSpartan();

        System.out.println("Spartan = " + spartan);

        Response response = given().accept(ContentType.JSON).and() //what we are asking from api which is JSON response
                .contentType(ContentType.JSON) //what we are sending to api, which is JSON also
                .body(spartan).log().all() //SERIALIZATION
                .when()
                .post("/api/spartans");

        //verify status code
        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));

        String expectedResponseMessage = "A Spartan is Born!";
        assertThat(response.path("success"),is(expectedResponseMessage));
        assertThat(response.path("data.name"),is(spartan.get("name")));
        assertThat(response.path("data.gender"),is(spartan.get("gender")));
        assertThat(response.path("data.phone"),is(spartan.get("phone")));

        response.prettyPrint();
    }
}


































