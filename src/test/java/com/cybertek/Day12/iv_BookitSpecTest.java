package com.cybertek.Day12;

import com.cybertek.utilities.BookitTestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class iv_BookitSpecTest extends BookitTestBase {
    @Test
    public void test1(){
        // send a get request to /api/users/me endpoint as a teacher
        // verify status code and content type
        given().spec(userReqSpec("teacher"))
                .when()
                .get("/api/users/me")
                .then()
                .spec(getDynamicResSpec(200));
    }

    @Test
    public void test2(){
        // send a get request to /api/users/me endpoint as a student-member
        // verify status code and content type
    }
}
