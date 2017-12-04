package ru.otus.hw.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Atm implements Serializable{
    private String uuid;
    private List<AtmCell> atmCells;

    public Atm(List<AtmCell> atmCells) {
        this.uuid = UUID.randomUUID().toString();
        this.atmCells = atmCells;
    }

    public List<AtmCell> getAtmCells() {
        return atmCells;
    }

    public void setAtmCells(List<AtmCell> atmCells) {
        this.atmCells = atmCells;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Atm{" +
                "atmCells=" + atmCells +
                '}';
    }
}
