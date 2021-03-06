package ru.otus.hw.exception;

public class NoSuchMoneyNominal extends Exception {

    private final String message;
    public NoSuchMoneyNominal(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
