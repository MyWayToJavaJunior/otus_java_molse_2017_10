package ru.otus.hw.test.sub;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;
import ru.otus.hw.engine.Assert;

public class TestSubRunner {

    @Before
    public void beforeSecond(){
        System.out.println("sub beforeSecond");
    }

    @Test
    public void testFirst(){
        Assert.assertTrue(false);
    }

    @Test
    public void testsecond(){
        Assert.assertTrue(true);
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
