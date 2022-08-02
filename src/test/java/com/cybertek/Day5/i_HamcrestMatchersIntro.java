package com.cybertek.Day5;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class i_HamcrestMatchersIntro {
    @DisplayName("Matcher Demo")
    @Test
    public void test1() {
        assertThat(5 + 5, is(10));
        assertThat(5 + 5, equalTo(10));
        //matchers have 2 overloaded version
        //first that accept actual value
        //second taht accept another matchers
        //below examples is method is accepting another matchers equal to make it readable
        assertThat(5 + 5, is(equalTo(10)));

        assertThat(5 + 5, not(9));
        assertThat(5 + 5, is(not(9)));
        assertThat(5 + 5, is(not(equalTo(11))));

        //number comparison
        //greaterThan()
        //greaterThanOrEqualTo()
        //lessThan()
        //lessThanOrEqualTo()
        assertThat(5 + 5, is(greaterThan(9)));
    }

    @DisplayName("Assertion with String")
    @Test
    public void test2() {
        String text = "B22 is learning Hamcrest";

        //checking for equality is same as numbers
        assertThat(text, is("B22 is learning Hamcrest"));
        assertThat(text, equalTo("B22 is learning Hamcrest"));
        assertThat(text, is(equalTo("B22 is learning Hamcrest")));

        //check if this text starts with B22
        assertThat(text, startsWith("B22"));
        //now do it in case-insensitive manner
        assertThat(text, startsWithIgnoringCase("b22"));
        //ends-with
        assertThat(text, endsWith("rest"));

        //check if text contains String learning
        assertThat(text, containsString("learning"));
        //with ignoring case
        assertThat(text, containsStringIgnoringCase("LEARNING"));

        String str = "   ";
        assertThat(str, blankString());
        assertThat(str.trim(), emptyString());
    }

    @DisplayName("Hamcrest for Collection")
    @Test
    public void testCollection() {

        List<Integer> listOfNumbers = Arrays.asList(1, 4, 5, 6, 32, 54, 66, 77, 45, 23);

        //check size of the list
        assertThat(listOfNumbers, hasSize(10));
        //check if this list hasItem 77
        assertThat(listOfNumbers, hasItem(77));
        //check if this list hasItems 77,54,23
        assertThat(listOfNumbers, hasItems(77, 54, 23));

        //check if all numbers greater than 0
        assertThat(listOfNumbers, everyItem(greaterThan(0)));

    }
}
