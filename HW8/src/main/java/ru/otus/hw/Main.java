package ru.otus.hw;

import ru.otus.hw.factory.JavaxJsonFactory;
import ru.otus.hw.factory.SimpleJsonFactory;
import ru.otus.hw.model.TestingClass.Chat;
import ru.otus.hw.model.TestingClass.Message;
import ru.otus.hw.model.TestingClass.Participants;
import ru.otus.hw.parser.JsonParser;

import javax.json.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(final String... args) throws IllegalAccessException {
        JsonParser parser = JavaxJsonFactory.getInstance().createParser();
        List<Message> lists = new ArrayList<>(Arrays.asList(new Message("Hello1")));
        Participants[] participantses = new Participants[0];
        Chat chat = new Chat(lists,participantses);
        /*String jsonStr = parser.toJson(chat);
        System.out.println(jsonStr);*/



        JsonObjectBuilder chatbuilder = Json.createObjectBuilder();
        chatbuilder.addNull()
        JsonArrayBuilder messagesBuilder = Json.createArrayBuilder();
        JsonArrayBuilder participantsBuilder = Json.createArrayBuilder();

        for (Message message : chat.getMessages()) {
            JsonObjectBuilder messageBld = Json.createObjectBuilder();
            messageBld.add("message",message.getMessage());
            messagesBuilder.add(messageBld);
        }

        chatbuilder.add("messages", messagesBuilder);
        chatbuilder.add("participants", participantsBuilder);

        JsonObject chatJsonObj = chatbuilder.build();

        System.out.println("Employee JSON String\n"+chatJsonObj);

    }



}
