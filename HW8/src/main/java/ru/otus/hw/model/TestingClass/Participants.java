package ru.otus.hw.model.TestingClass;

public class Participants extends People {

    private String nickName;

    public Participants(int age, String name, boolean isMale, String nickName) {
        super(age, name, isMale);
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
