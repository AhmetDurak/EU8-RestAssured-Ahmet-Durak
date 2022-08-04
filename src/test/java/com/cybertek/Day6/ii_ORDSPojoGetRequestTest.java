package com.cybertek.Day6;

import com.cybertek.pojo.Employee;
import com.cybertek.pojo.Region;
import com.cybertek.utilities.HRTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
