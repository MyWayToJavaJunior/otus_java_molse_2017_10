package ru.otus.hw.test;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

public class TestRunner {

    @Before
    public void beforeFirst(){
        System.out.println("beforeFirst");
    }

    @Before
    public void beforeSecond(){
        System.out.println("beforeSecond");
    }

    @Test
    public void testFirst(){
        System.out.println("testFirst");
    }

    @Test
    public void testsecond(){
        System.out.println("testsecond");
    }

    @After
    public void afterFirst(){
        System.out.println("afterFirst");
    }

    @After
    public void afterSecond(){
        System.out.println("afterSecond");
    }
}
