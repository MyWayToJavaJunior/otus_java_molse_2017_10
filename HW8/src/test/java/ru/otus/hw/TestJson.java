package ru.otus.hw;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.hw.factory.JavaxJsonFactory;
import ru.otus.hw.model.TestingClass.Chat;
import ru.otus.hw.model.TestingClass.Message;
import ru.otus.hw.factory.GsonFactory;
import ru.otus.hw.model.TestingClass.Participants;
import ru.otus.hw.parser.JsonParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestJson {
    private JsonParser parser;

    @Before
    public void init() {
        parser = JavaxJsonFactory.getInstance().createParser();
    }

    @Test public void messageClassTest() throws IllegalAccessException {
        Message mg = new Message("Hello");
        Gson gson = new Gson();
        String json = parser.toJson(mg);
        Assert.assertEquals("{\"message\":\"Hello\"}",json);
        Assert.assertEquals(gson.fromJson(json,Message.class).getMessage(),mg.getMessage());
    }

    @Test public void primitiveArrayTest() throws IllegalAccessException {
        int[] arr = new int[5];
        Arrays.fill(arr,5);
        String jsonStr = parser.toJson(arr);
        Assert.assertEquals("[5,5,5,5,5]",jsonStr);
    }

    @Test public void arrayListMessageTest() throws IllegalAccessException {
        List<Message> lists = new ArrayList<>(Arrays.asList(new Message("Hello1"),new Message("Hello2")));
        String jsonStr = parser.toJson(lists);
        Assert.assertTrue(jsonStr.contains(":\"Hello2\""));
        Gson gson = new Gson();
        Message[] arr = gson.fromJson(jsonStr, Message[].class);
        Assert.assertEquals(arr[0].getMessage(),"Hello1");
    }

    @Test public void chatComplexTest() throws IllegalAccessException {
        List<Message> lists = new ArrayList<>(Arrays.asList(new Message("Hello1"),new Message("Hello2")));
        Participants[] participantses = new Participants[2];
        participantses[0] = new Participants(17,"Ivan",true,"Dubolom");
        participantses[1] = new Participants(23,"Lera",false,"Dyrka");
        Chat chat = new Chat(lists,participantses);
        String jsonStr = parser.toJson(chat);
        Assert.assertTrue(jsonStr.contains("\"paricipants\":[{\""));
        Assert.assertTrue(jsonStr.contains("\"isMale\":false"));
        Gson gson = new Gson();
        Chat chatRestored = gson.fromJson(jsonStr, Chat.class);
        Assert.assertEquals(chatRestored.getParicipants().length,2);
        Assert.assertEquals(chatRestored.getMessages().size(),2);
        Assert.assertEquals(chatRestored.getMessages().get(0).getMessage(),"Hello1");
        Assert.assertEquals(chatRestored.getParicipants()[1].getNickName(),"Dyrka");
        Assert.assertEquals(chatRestored,chat);
    }

}