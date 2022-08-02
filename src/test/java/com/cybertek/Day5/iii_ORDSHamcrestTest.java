package com.cybertek.Day5;

import com.cybertek.utilities.ConfigurationReader;
import com.cybertek.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class iii_ORDSHamcrestTest extends HRTestBase {
    //send a get request to employees endpoint with query parameter job_id IT_PROG
    //verify each job_id is IT_PROG
    //verify first names are .... (find proper method to check list against list)
    //verify emails without checking order (provide emails in different order,just make sure it has same emails)
    //expected names

    @DisplayName("GET request to employees IT_PROG endpoint and chaining")
    @Test
    public void employeesTest() {
        List<String> names = Arrays.asList("Alexander", "Bruce", "David", "Valli", "Diana");

        given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\": \"IT_PROG\"}")
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .body("items.job_id", everyItem(equalTo("IT_PROG")))
                .body("items.first_name", containsInRelativeOrder("Alexander", "Bruce", "David", "Valli", "Diana")) //contains with order
                .body("items.email", containsInAnyOrder("VPATABAL", "DAUSTIN", "BERNST", "AHUNOLD", "DLORENTZ")) //contains without order
                .body("items.first_name", equalTo(names)); // equality of lists assertion
    }

    @Test
    public void employeesTest2() {
        //we want to chain and also get response object


        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\": \"IT_PROG\"}")
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .body("items.job_id", everyItem(equalTo("IT_PROG")))
                .extract().jsonPath();
        //extract() --> method that allow us to get response object after we use then() method.
        //assert that we have only 5 firstnames
        assertThat(jsonPath.getList("items.first_name"), hasSize(5));

        //assert firstnames order
        assertThat(jsonPath.getList("items.first_name"), containsInRelativeOrder("Alexander", "Bruce", "David", "Valli", "Diana"));

    }
}
