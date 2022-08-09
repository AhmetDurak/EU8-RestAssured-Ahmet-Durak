package com.cybertek.Day11;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class i_TestLifeCycleAnnotations {

    //beforeClass is testNg version of beforeAll, same logic
    @BeforeAll
    public static void init(){
        System.out.println("Before all is running");
    }
    //beforeMethod is testNg version of beforeEach, same logic
    @BeforeEach
    public void initEach(){
        System.out.println("\tBefore each is running");
    }

    @AfterEach
    public void closeEach(){
        System.out.println("\tAfter each is running");
    }

    @Test
    public void test1(){
        System.out.println("Test 1 is running");
    }

    @Disabled
    @Test
    public void test2(){
        System.out.println("Test 2 is running");
    }

    @AfterAll
    public static void close(){
        System.out.println("After all is running");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first_param","second_param","third_param"})
    public void paramTest(String param){
        System.out.println(param);
    }

}
