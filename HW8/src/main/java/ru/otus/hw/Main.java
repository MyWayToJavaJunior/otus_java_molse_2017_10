package ru.otus.hw;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.otus.hw.factory.JavaxJsonFactory;
import ru.otus.hw.model.TestingClass.Chat;
import ru.otus.hw.model.TestingClass.Message;
import ru.otus.hw.model.TestingClass.Participant;
import ru.otus.hw.parser.JsonParser;

public class Main {

    public static void main(final String... args) throws IllegalAccessException {
        JsonParser parser = JavaxJsonFactory.getInstance().createParser();
        List<Message> lists = new ArrayList<>(Arrays.asList(new Message("Hello1"),new Message("Hello2")));
        Participant[] participantses = new Participant[1];
        Participant user = new Participant(25,"Vasya",true,"TTTT");
        participantses[0] = user;
        Chat chat = new Chat(lists,participantses);
        Gson gson = new Gson();
        System.out.println(gson.toJson(123));
        System.out.println(gson.toJson(123.0d));
        System.out.println(gson.toJson("abc"));
        System.out.println(gson.toJson('c'));
        System.out.println(gson.toJson(null));
        System.out.println(gson.toJson(true));

        System.out.println(parser.toJson(123));
        System.out.println(parser.toJson(123.0d));
        System.out.println(parser.toJson("abc"));
        System.out.println(parser.toJson('c'));
        System.out.println(parser.toJson(null));
        System.out.println(parser.toJson(true));

    }



}
