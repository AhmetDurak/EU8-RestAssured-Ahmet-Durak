package com.cybertek.Day11;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class iii_CsvSourceParameterizedTest {
    // Test first number + second number = third number
    //            1, 3 , 4
    //            2, 3 , 5
    //            8, 7 , 15
    //            10, 9 , 19

    @ParameterizedTest
    @CsvSource({
            "1, 3, 4", // first_element is first_param, second will be second and third will be third
            "2, 3, 5",
            "8, 7, 15",
            "10, 9, 19"})
    public void csvTest(int num1, int num2, int sum) {
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
        System.out.println("sum = " + sum + "\n");

        assertThat(num1 + num2, is(sum));
    }

    // Write a parameterized test for this request
    // GET https://api.zippopotam.us/us/{state}/{city}
    /*
        "NY, New York",
        "CO, Denver",
        "VA, Fairfax",
        "VA, Arlington",
        "MA, Boston",
        "NY, New York",
        "MD, Annapolis"
     */
    //verify place name contains your city name
    //print number of places for each request

    @ParameterizedTest
    @CsvSource({
            "NY, New York",
            "CO, Denver",
            "VA, Fairfax",
            "VA, Arlington",
            "MA, Boston",
            "NY, New York",
            "MD, Annapolis"
    })
    public void zipCodeMultiInputTest(String state, String city){
        System.out.println("state = " + state);
        System.out.println("city = " + city);

        int placeNumber = given()
                .baseUri("https://api.zippopotam.us")
                .accept(ContentType.JSON)
                .pathParam("state", state)
                .pathParam("city", city)
                .log().uri()
                .when()
                .get("/us/{state}/{city}")
                .then()
                .statusCode(200)
                .and()
                .body("places.'place name'", everyItem(containsStringIgnoringCase(city)))
                //.log().body()
                .extract()
                .jsonPath().getList("places").size();

        System.out.println("placeNumber = " + placeNumber);
    }

}
