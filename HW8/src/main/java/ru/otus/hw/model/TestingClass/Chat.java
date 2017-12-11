package ru.otus.hw.model.TestingClass;

import java.util.List;

public class Chat {

    private List<Message> messages;
    private Participants[] paricipants;

    public Chat(List<Message> messages, Participants[] paricipants) {
        this.messages = messages;
        this.paricipants = paricipants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Participants[] getParicipants() {
        return paricipants;
    }

    public void setParicipants(Participants[] paricipants) {
        this.paricipants = paricipants;
    }
}
