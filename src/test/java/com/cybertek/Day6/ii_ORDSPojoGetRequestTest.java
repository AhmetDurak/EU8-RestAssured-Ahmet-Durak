package com.cybertek.Day6;

import com.cybertek.pojo.Region;
import com.cybertek.utilities.HRTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ii_ORDSPojoGetRequestTest extends HRTestBase {
    @Test
    public void testHRAPI(){
        JsonPath jsonPath = get("/regions").then().statusCode(200).extract().jsonPath();

        Region region1 = jsonPath.getObject("items[0]", Region.class);
        System.out.println("region1 = " + region1);

        System.out.println("getRegion_id() = " + region1.getRegion_id());
        System.out.println("getRegion_name() = " + region1.getRegion_name());
        System.out.println("getHref() = " + region1.getLinks().get(0).getHref());
    }
}
