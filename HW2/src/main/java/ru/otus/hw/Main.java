package ru.otus.hw;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main {

    public static void main(String... args) throws InterruptedException {
        EstimateStand<Object> stand = new EstimateStand<>();
        stand.printSize("");
        stand.printSize("Hello world");
        stand.printSize(new int[0]);
        stand.printSize(new Integer[0]);
        System.out.println("______List of int");
        stand.printSize(Collections.singletonList(1));
        stand.printSize(Arrays.asList(1,2));
        stand.printSize(Arrays.asList(1,2,3));
        stand.printSize(Arrays.asList(1,2,3,4));
        stand.printSize(Arrays.asList(1,2,3,4,5));
        System.out.println("_______List of String");
        stand.printSize(Arrays.asList("I"));
        stand.printSize(Arrays.asList("I","'ll"));
        stand.printSize(Arrays.asList("I","'ll","back"));

        System.out.println("___________Big data from loop");
        Random randNumber = new Random();
        while (true) {
            int size = randNumber.nextInt(10_000_000);
            Object[] array = new Object[size];
            System.out.println("New array of size: " + array.length + " created");
            for (int i = 0; i < size; i++) {
                array[i] = new Object();
            }
            stand.printSize(array);
            System.out.println("Created " + size + " objects.");
            Thread.sleep(2000); //wait for 1 sec
        }


    }
}
