package ru.otus.hw.model.TestingClass;

import lombok.Data;

@Data
public class Participants extends People {

    private String nickName;

    public Participants(int age, String name, boolean isMale, String nickName) {
        super(age, name, isMale);
        this.nickName = nickName;
    }
}
