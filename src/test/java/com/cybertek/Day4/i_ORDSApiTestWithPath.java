package com.cybertek.Day4;

import com.cybertek.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class i_ORDSApiTestWithPath extends HRTestBase {

    @DisplayName("GET request to countries with Path method")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParams("q","{\"region_id\": 2}")
                .when()
                .get("/countries");

        assertEquals(200,response.statusCode());

        //print limit,hasMore,offset,count result
        System.out.println("Limit: " + response.path("limit"));
        System.out.println("hasMore: " + response.path("hasMore"));
        System.out.println("offset: " + response.path("offset"));
        System.out.println("count: " + response.path("count"));

        //print first CountryId
        System.out.println("First Country_id: " + response.path("items[0].country_id"));

        //print second country_name
        System.out.println("Second Country_name: " + response.path("items[1].country_name"));

        //print "http://52.207.61.129:1000/ords/hr/countries/CA"
        System.out.println("Third href: " + response.path("items[2].links[0].href"));

        //get me all country names
        List<String> allCountryNames = response.path("items.country_name");
        System.out.println("allCountryNames = " + allCountryNames);

        //assert that all regions ids are equal to 2
        List<Integer> allRegion_id = response.path("items.region_id");
        for (int eachRegion_id: allRegion_id){
            System.out.println("eachRegion_id = " + eachRegion_id);
            assertEquals(2,eachRegion_id);
        }
    }

    @DisplayName("GET request to /employees with Query Param")
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParams("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());

        //make user we have only IT_PROG as a job_id
        List<String> allJobIds = response.path("items.job_id");
        for (String eachJobId: allJobIds){
            //System.out.println(eachJobId);
            assertEquals("IT_PROG",eachJobId);
        }

        //print name of each IT_PROGs
        List<String> eachFirstName = response.path("items.first_name");
        List<String> eachLastName = response.path("items.last_name");
        for (int i = 0; i < eachFirstName.size(); i++){
            System.out.println(eachFirstName.get(i) + " " + eachLastName.get(i));
        }
    }
}
