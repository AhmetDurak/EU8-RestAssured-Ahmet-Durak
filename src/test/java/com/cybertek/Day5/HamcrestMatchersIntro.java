package com.cybertek.Day5;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

public class HamcrestMatchersIntro {
    @DisplayName("Matcher Demo")
    @Test
    public void test1(){
        assertThat(5+5,is(10));
        assertThat(5+5,equalTo(10));
        //matchers have 2 overloaded version
        //first that accept actual value
        //second taht accept another matchers
        //below examples is method is accepting another matchers equal to make it readable
        assertThat(5+5,is(equalTo(10)));

        assertThat(5+5, not(9));
        assertThat(5+5, is(not(9)));
        assertThat(5+5, is(not(equalTo(10))));

        //number comparison
        //greaterThan()
        //greaterThanOrEqualTo()
        //lessThan()
        //lessThanOrEqualTo()
        assertThat(5+5,is(greaterThan(9)));
    }
}
