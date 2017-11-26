package ru.otus.hw;

import ru.otus.hw.engine.TestEngine;
import ru.otus.hw.test.TestRunner;
import java.io.IOException;

public class Main {

    public static void main(final String... args) throws InterruptedException, IOException {

        TestEngine testEngine = new TestEngine(TestRunner.class);
        testEngine.runTests();

        TestEngine testEnginePackage = new TestEngine("ru.otus.hw.test");
        testEnginePackage.runTests();

    }




}
