package ru.otus.hw.model.TestingClass;

import java.util.List;

public class Chat {

    private List<Message> messages;
    private Participant[] paricipants;

    public Chat(List<Message> messages) {
        this.messages = messages;
    }

    public Chat(Participant[] paricipants) {
        this.paricipants = paricipants;
    }

    public Chat(List<Message> messages, Participant[] paricipants) {
        this.messages = messages;
        this.paricipants = paricipants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Participant[] getParicipants() {
        return paricipants;
    }

    public void setParicipants(Participant[] paricipants) {
        this.paricipants = paricipants;
    }
}
