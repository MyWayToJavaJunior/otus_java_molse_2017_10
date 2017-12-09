package ru.otus.hw.model.TestingClass;

import lombok.Data;
import java.util.List;

@Data
public class Chat {

    private List<Message> messages;
    private Participants[] paricipants;

    public Chat(List<Message> messages, Participants[] paricipants) {
        this.messages = messages;
        this.paricipants = paricipants;
    }
}
