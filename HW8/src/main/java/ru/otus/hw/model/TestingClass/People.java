package ru.otus.hw.model.TestingClass;

import lombok.Data;

@Data
public class People {

    private int age;
    private String name;
    private boolean isMale;

    public People(int age, String name, boolean isMale) {
        this.age = age;
        this.name = name;
        this.isMale = isMale;
    }
}
