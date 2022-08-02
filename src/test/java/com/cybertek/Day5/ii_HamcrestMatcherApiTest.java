package com.cybertek.Day5;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ii_HamcrestMatcherApiTest {
    /*
       given accept type is Json
       And path param id is 15
       When user sends a get request to spartans/{id}
       Then status code is 200
       And content type is Json
       And json data has following
           "id": 15,
           "name": "Meta",
           "gender": "Female",
           "phone": 1938695106
        */
    @DisplayName("OneSpartan with Hamcrest and chaining")
    @Test
    public void test1(){
        given()
                    .accept(ContentType.JSON)
                    .and().pathParams("id", 15)
                .when()
                    .get("http://54.89.215.84:8000/api/spartans/{id}")
                .then()
                    .statusCode(200)
                    .and()
                    .contentType("application/json")
                    .and()
                    .body(
                        "id",equalTo(15),
                        "name",equalTo("Meta"),
                        "gender", equalTo("Female"),
                        "phone", is(1938695106)
                        );
    }

    @DisplayName("CBTraining Teacher request with chaining and matchers")
    @Test
    public void teacherData(){
        given()
                .accept(ContentType.JSON)
                .and()
                .pathParams("id",20450)
                .when()
                .get("http://api.cybertektraining.com/teacher/{id}")
                .then()
                .statusCode(200)
                .and()
                .contentType("application/json;charset=UTF-8")
                .and()
                .header("Date",notNullValue())
                .and()
                .assertThat()
                .body("teachers[0].firstName",is("Alexander"))
                .body("teachers[0].lastName",is(equalTo("Syrup")))
                .body("teachers[0].gender",is("male"));
    }
    @DisplayName("GET request to teacher/all and chaining")
    @Test
    public void teachersTest(){
        //verify Alexander, Darleen, Sean inside the all teachers
        given()
                .accept(ContentType.JSON)
                .when()
                .get("http://api.cybertektraining.com/teacher/all")
                .then()
                .statusCode(200)
                .and()
                .body("teachers.firstName",hasItems("Alexander","Kristofer","Russell"));
    }
}

















