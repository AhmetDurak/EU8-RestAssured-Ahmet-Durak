package com.cybertek.utilities;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class BookitTestBase {
    public static RequestSpecification teacherReqSpec;
    public static RequestSpecification studentMemberReqSpec;
    public static RequestSpecification studentLeaderReqSpec;
    public static ResponseSpecification responseSpec;

    @BeforeAll
    public static void init() {
        baseURI = ConfigurationReader.getProperty("qa3_api_url");

//        teacherReqSpec = given()
//                .accept(ContentType.JSON)
//                .header("Authorization", getTokenByRole("teacher"))
//                .log().all();
//        studentMemberReqSpec = given()
//                .accept(ContentType.JSON)
//                .header("Authorization", getTokenByRole("student-member"))
//                .log().all();
//        studentLeaderReqSpec = given()
//                .accept(ContentType.JSON)
//                .header("Authorization", getTokenByRole("student-leader"))
//                .log().all();
//        responseSpec = expect()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .logDetail(LogDetail.ALL);
    }

    @AfterAll
    public static void teardown() {
        reset();
    }

    //optional we can also make RS dynamic
    public static ResponseSpecification getDynamicResSpec(int statusCode) {
        return expect()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .logDetail(LogDetail.ALL);
    }
    //optional we can also make RS dynamic
    public static ResponseSpecification getDynamicResSpec(int statusCode, String contentType) {
        return expect()
                .statusCode(statusCode)
                .contentType(contentType)
                .logDetail(LogDetail.ALL);
    }

    public static ResponseSpecification userCheck(String firstname,String lastname){
        return expect().contentType(ContentType.JSON)
                .body("firstName",is(firstname))
                .body("lastName",is(lastname))
                .logDetail(LogDetail.ALL);
    }

    public static RequestSpecification userReqSpec(String role){
        return given().accept(ContentType.JSON)
                .header("Authorization",getTokenByRole(role))
                .log().all();
    }

    //teacher , student-member,student-leader
    //it will take user info from conf.properties
    public static String getTokenByRole(String role) {
        //switch,  make sure you get correct user info
        //send request/get token/ return token
        String email, password;
        switch (role) {
            case "teacher":
                email = ConfigurationReader.getProperty("teacher_email");
                password = ConfigurationReader.getProperty("teacher_password");
                break;
            case "student-member":
                email = ConfigurationReader.getProperty("team_member_email");
                password = ConfigurationReader.getProperty("team_member_password");
                break;
            case "student-leader":
                email = ConfigurationReader.getProperty("team_leader_email");
                password = ConfigurationReader.getProperty("team_leader_password");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
        return "Bearer " + given().accept(ContentType.JSON)
                                    .queryParams("email",email,"password",password)
                                    .get("/sign")
                                    .then()
                                    .statusCode(200)
                                    .extract().response().path("accessToken");
    }
}
