package com.cybertek.utilities;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;


public abstract class SpartanTestBase {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we don't need to type each http method.
        baseURI = ConfigurationReader.getProperty("spartanBaseUrl");

        String dbUrl = "jdbc:oracle:thin:@54.89.215.84:1521:xe";
        String dbUsername = "SP";
        String dbPassword = "SP";

        //DBUtils.createConnection(dbUrl,dbUsername,dbPassword);
    }

    @AfterAll
    public static void teardown(){

        //DBUtils.destroy();
    }

    public static Map<String, Object> generateSpartan() {
        //Create one SpartanUtil class
        //create a static method that returns Map<String,Object>
        //use faker library(add as a dependency) to assign each time different information
        Faker faker = new Faker();
        String[] gender = {"Male", "Female"};
        Map<String, Object> spartan = new HashMap<>();
        //for name,gender,phone number
        spartan.put("name", faker.name().firstName());
        spartan.put("gender", gender[faker.random().nextInt(0, 1)]);
        spartan.put("phone", Long.parseLong(faker.phoneNumber().subscriberNumber(10)));
        //then use your method for creating spartan as a map,dynamically.
        return spartan;
    }

}
class SpartanTestData extends SpartanTestBase{
    @Test
    public void test1(){
        System.out.println(generateSpartan());
    }
}
