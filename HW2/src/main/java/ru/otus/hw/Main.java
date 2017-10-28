package ru.otus.hw;

import java.util.Collections;
import java.util.Random;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String... args) throws InterruptedException {
        EstimateStand<Object> stand = new EstimateStand<>();
       /* stand.printSize("");
        stand.printSize("Hello world");
        stand.printSize(new int[0]);
        stand.printSize(new Integer[0]);
        System.out.println("______List of int");
        stand.printSize(Collections.singletonList(1));
        stand.printSize(asList(1,2));
        stand.printSize(asList(1,2,3));
        stand.printSize(asList(1,2,3,4));
        stand.printSize(asList(1,2,3,4,5));
        System.out.println("_______List of String");
        stand.printSize(Collections.singletonList("I"));
        stand.printSize(asList("I","'ll"));
        stand.printSize(asList("I","'ll","back"));

        System.out.println("___________Big data from loop");
        Random randNumber = new Random();
        while (true) {
            int size = randNumber.nextInt(1_000_000);
            Object[] array = new Object[size];
            System.out.println("New array of size: " + array.length + " created");
            for (int i = 0; i < size; i++) {
                array[i] = new MyData("pass" + size);
            }
            System.out.println("Created " + size + " objects.");
            stand.printSize(array);
            Thread.sleep(2000); //wait for 1 sec
        }*/

       SomeData some = new SomeData("Hello");
        MyData data = new MyData(some);
        stand.printSize(data);


    }


    private static class MyData{

        private final SomeData someData;

        public MyData(SomeData someData) {
            this.someData = someData;
        }
    }

    private static class SomeData {

        private String some;

        public SomeData(String some) {
            this.some = some;
        }
    }
}
