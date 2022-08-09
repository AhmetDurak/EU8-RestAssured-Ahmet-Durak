package com.cybertek.Day12;

import com.cybertek.utilities.SpartanNewBase;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class i_OldRestAssuredTest extends SpartanNewBase {
    @Test
    public void getAllSpartan() {
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .log().all()
                .when()
                .get("/spartans")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(1))
                .body("id[5]", is(6))
                .log().all();
    }

    @Test
    public void test2(){
        /*
            in previous version of Rest-assured, the given when then style
            was actually written in given expect when formatted.
            it will assert all the result and give one answer and does not fail whole thing
            if first one fail unlike new structure.

         */
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
                .log().all()
                /**
                 * There is two difference between then() and expect()
                 * 1. then() makes hard assertion -> means that if first assertion fails, then fails the rest
                 * but expect() verify each assertion but actually hard assertion is better
                 * 2. log().all() method is different in expect()
                 * 3. The chaining order is different. expect() comes earlier than when() method.
                 */
                .expect()
                .statusCode(200)
                .and()
                .contentType("application/json")
                .body("id[0]",is(10))
                .body("id[5]",is(199))
                //.logDetail(LogDetail.ALL)   //log way using with expect()
                .when()
                .get("/spartans");
    }
}
