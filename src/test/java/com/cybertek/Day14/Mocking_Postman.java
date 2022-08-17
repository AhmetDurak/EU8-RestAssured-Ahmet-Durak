package com.cybertek.Day14;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Mocking_Postman {
    @Test
    public void mockingPostAPI(){
        given().baseUri("https://77e1a932-7278-4263-a1b9-1a28f27b853a.mock.pstmn.io")
                .accept(ContentType.JSON)
            .when()
                .get("/customer")
                .prettyPrint();
    }
}
