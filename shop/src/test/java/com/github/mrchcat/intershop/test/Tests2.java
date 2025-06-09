package com.github.mrchcat.intershop.test;

import com.github.mrchcat.intershop.AbstractTestContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class Tests2 extends AbstractTestContainerTest{

 @Test
    void test(){}


}
