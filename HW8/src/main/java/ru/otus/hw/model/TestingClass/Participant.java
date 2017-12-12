package ru.otus.hw.model.TestingClass;

public class Participant extends People {

    private String nickName;

    public Participant(int age, String name, boolean isMale, String nickName) {
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
