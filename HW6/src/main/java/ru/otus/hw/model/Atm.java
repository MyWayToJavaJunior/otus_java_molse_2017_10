package ru.otus.hw.model;

import java.util.List;

public class Atm {
    private List<AtmCell> atmCells;

    public Atm(List<AtmCell> atmCells) {
        this.atmCells = atmCells;
    }

    public List<AtmCell> getAtmCells() {
        return atmCells;
    }

    public void setAtmCells(List<AtmCell> atmCells) {
        this.atmCells = atmCells;
    }


    @Override
    public String toString() {
        return "Atm{" +
                "atmCells=" + atmCells +
                '}';
    }
}
