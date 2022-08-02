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
    }
}
