package com.cybertek.Day6;

import com.cybertek.pojo.Employee;
import com.cybertek.pojo.Region;
import com.cybertek.pojo.Regions;
import com.cybertek.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ii_ORDSPojoGetRequestTest extends HRTestBase {
    @Test
    public void testHRAPI(){
        JsonPath jsonPath = get("/regions").then().statusCode(200).extract().jsonPath();

        Region region1 = jsonPath.getObject("items[0]", Region.class);
        System.out.println("region1 = " + region1);

        System.out.println("getRegion_id() = " + region1.getRegionId());
        System.out.println("getRegion_name() = " + region1.getRegionName());
        System.out.println("getHref() = " + region1.getLinkList().get(0).getHref());
    }

    @DisplayName("GET request to /employees and only get couple of values as a Pojo class")
    @Test
    public void employeeGet(){

        Employee employee1 = get("/employees").then().statusCode(200)
                .extract().jsonPath().getObject("items[0]", Employee.class);

        System.out.println(employee1);

    }

        /* send a get request to regions
        verify that region ids are 1,2,3,4
        verify that regions names Europe ,Americas , Asia, Middle East and Africa
        verify that count is 4
        try to use pojo as much as possible
        ignore non used fields

     */

    @DisplayName("GET request to region only some fields test")
    @Test
    public void regionPojoTest(){
        Regions regions = get("/regions")
                .then().statusCode(200).extract().response().as(Regions.class);


        //verify count is 4
        assertThat(regions.getCount(), is(4));
        //create empty list to store values
        List<String> regionNames = new ArrayList<>();
        List<Integer> regionIds = new ArrayList<>();

        //get list of regions out of regions object
        List<Region> items = regions.getRegionList();

        //loop through each of the region, save their ids and names to empty list that we prepare
        for (Region region: items){
            regionIds.add(region.getRegionId());
            regionNames.add(region.getRegionName());
        }
        System.out.println("regionIds = " + regionIds);
        System.out.println("regionNames = " + regionNames);

        //prepare expected result
        List<Integer> expectedRegionIds = Arrays.asList(1,2,3,4);
        List<String> expectedRegionNames = Arrays.asList("Europe", "Americas", "Asia", "Middle East and Africa");

        //compare two result
        assertThat(regionIds,is(expectedRegionIds));
        assertThat(regionNames,is(expectedRegionNames));
    }
}
