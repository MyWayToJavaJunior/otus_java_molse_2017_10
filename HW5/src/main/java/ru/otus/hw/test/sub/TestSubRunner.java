package ru.otus.hw.test.sub;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

public class TestSubRunner {

    @Before
    public void beforeSecond(){
        System.out.println("sub beforeSecond");
    }

    @Test
    public void testFirst(){
        System.out.println("sub testFirst");
    }

    @Test
    public void testsecond(){
        System.out.println("sub testsecond");
    }

    @Test
    public void testthird(){
        System.out.println("sub testthird");
    }


    @After
    public void afterSecond(){
        System.out.println("sub afterSecond");
    }
}
