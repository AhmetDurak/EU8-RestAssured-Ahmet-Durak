package com.cybertek.Day4;

import com.cybertek.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ii_ORDSApiTestWithJsonPath extends HRTestBase {
    @DisplayName("GET request to Countries")
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/countries");

        //get the second country name with JsonPath
        JsonPath jsonPath = response.jsonPath();

        System.out.println(jsonPath.getString("items[1].country_name"));

        //get all country ids
        List<String> countryid = jsonPath.getList("items.country_id");
        System.out.println("countryId = " + countryid);

        //get all country names where their region id is equal to 2    //this path belongs to json structure
        List<Object> countryNamesWithRegionID = jsonPath.getList("items.findAll {it.region_id==2}.country_name");

        System.out.println("countryNamesWithRegionID = " + countryNamesWithRegionID);

    }

    @DisplayName("GET request to /employees with query param")
    @Test
    public void test2() {
        //we added limit query param to get 107 employees
        Response response = given().queryParam("limit", 107)
                .when().get("/employees");

        JsonPath jsonPath = response.jsonPath();
        response.prettyPrint();
        //get me all email of employees who is working as IT_PROG
        List<String> employeeITProgs = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println(employeeITProgs);

        //get me first name of employees who is making more than 10000
        List<String> empNames = jsonPath.getList("items.findAll {it.salary>10000}.first_name");
        System.out.println(empNames);

        //get the max salary first_name
        String kingFirstName = jsonPath.getString("items.max {it.salary}.first_name");
        String kingNameWithPathMethod = response.path("items.max {it.salary}.first_name");
        System.out.println("kingFirstName = " + kingFirstName);
        System.out.println("kingNameWithPathMethod = " + kingNameWithPathMethod);
    }
}
