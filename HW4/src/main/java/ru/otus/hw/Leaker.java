package ru.otus.hw;

import java.util.*;

public class Leaker
{
    private Map things = new HashMap();

    public void leak() {
        while (true) {
            things.put(new Date(), new Leak());
        }
    }

    private class Leak
    {
        private String[] data;

        public Leak() {

            data = new String[10000];
            Arrays.fill(data, UUID.randomUUID().toString());
        }
    }
}
