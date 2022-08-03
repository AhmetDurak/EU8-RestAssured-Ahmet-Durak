package com.cybertek.Day6;

import com.cybertek.pojo.*;
import com.cybertek.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class i_SpartanPOJOGetRequest extends SpartanTestBase {
    @DisplayName("GET one spartan and convert it to Spartan Object")
    @Test
    public void oneSpartanPojo(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParams("id", 15)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        //De serialize --> JSON to POJO (java custom class)
        //2 different way to do this
        //1.using as() method
        //we convert json response to spartan object with the help of jackson
        //as() method uses jackson to de serialize(converting JSON to Java class)
        Spartan spartan15 = response.as(Spartan.class);
        System.out.println("spartan15 = " + spartan15);
        System.out.println("id = " + spartan15.getId());
        System.out.println("gender = " + spartan15.getGender());

        //second way of deserialize json to java
        //2.using JsonPath to deserialize to custom class
        JsonPath jsonPath = response.jsonPath();

        // Path is empty, but it will get the path from response already
        // But if you want to get another path, you can add a new path
        Spartan s15 = jsonPath.getObject("",Spartan.class);
        System.out.println(s15);
        System.out.println("s15.getName() = " + s15.getName());
        System.out.println("s15.getPhone() = " + s15.getPhone());
    }

    @DisplayName("Get one spartan from search endpoint resul and use POJO")
    @Test
    public void spartanSearchWithPojo(){
        //spartans/search?nameContains=a&gender=Male
        // send get request to above endpoint and save first object with type Spartan POJO
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().queryParams("nameContains", "a",
                        "gender", "Male")
                .when().get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        //get the first spartan from content list and put inside spartan object
        //THIS GETOBJECT() CLASS GIVES US AN UPPER HAND
        Spartan s1 = jsonPath.getObject("content[0]", Spartan.class);
        System.out.println("s1 = " + s1);
        System.out.println("s1.getName() = " + s1.getName());
        System.out.println("s1.getGender() = " + s1.getGender());
    }
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParams("nameContains", "a",
                        "gender", "Male")
                .when().get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().response();

        Search search = response.as(Search.class);

        System.out.println("First Spartan: " + search.getContent().get(0).getName());
    }
    @Test
    public void test4(){
        List<Spartan> spartanList = given().accept(ContentType.JSON)
                .and().queryParams("nameContains", "a",
                        "gender", "Male")
                .when().get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("content", Spartan.class);

        System.out.println("First Spartan: " + spartanList.get(0).getName());
    }
}
