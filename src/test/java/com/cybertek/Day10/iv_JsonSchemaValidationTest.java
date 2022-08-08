package com.cybertek.Day10;

import com.cybertek.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class iv_JsonSchemaValidationTest extends SpartanTestBase {

    @DisplayName("GET request to verify one spartan against to schema")
    @Test
    public void schemaValidation(){

        given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id",10)
                .and()
                .auth().basic("admin","admin")
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                /**
                 * JSV will compare the json response with the schema
                 * If it is not valid, then it will return errors, but ignore warnings
                 * So, to find out the problem, you should check errors
                 */
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("i_SingleSpartanSchema.json"))
                .log().all();

    }
    @DisplayName("GET request to all spartans and verify schema")
    @Test
    public void allSpartanSchemaTest(){

        given()
                .accept(ContentType.JSON)
                .auth().basic("admin","admin")
                .when()
                .get("/api/spartans")
                .then()
                .statusCode(200)
                //what if you have your .json file not under resources following way -->
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cybertek/day10/allSpartansSchema.json")));

    }

    //homework
    //put your post json schema under day10
    //post one spartan using dynamic input(name,gender,phone)
    //verify your post response matching with json schema

}
