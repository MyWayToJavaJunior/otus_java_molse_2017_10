package ru.otus.hw.service;

import one.util.streamex.StreamEx;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AtmServiceImpl implements AtmService{

    private Atm atm;

    public AtmServiceImpl() {
        List<AtmCell> atmCells = new ArrayList<>();
        atmCells.add(new AtmCell(1000,10));
        atmCells.add(new AtmCell(500,10));
        atm = new Atm(atmCells);
    }

    @Override
    public void putMoney(int money) {
        AtmCell atmCell = atm.getAtmCells().stream().filter(cell -> cell.getNominal() == money).findFirst().orElseThrow(() -> new RuntimeException());
        atmCell.increaseCount(1);
    }

    @Override
    public String giveMoney(int money) {
        int finalMoney = money;
        List<AtmCell> atmCells = atm.getAtmCells().stream().filter(cell->cell.getCount()>0).collect(Collectors.toList());
        Collections.sort(atmCells);
        List<AtmCell> resultAtmCells = new ArrayList<>();
        for (AtmCell atmCell: atmCells) {
            int i = money / atmCell.getNominal();
            if (i> atmCell.getCount()) i = atmCell.getCount();
            resultAtmCells.add(new AtmCell(atmCell.getNominal(),i));
            money = money - i* atmCell.getNominal();
            atmCell.descreaseCount(i);
        }
        if (money != 0) return "Выдача невозможна";
        return resultAtmCells.toString();
    }

    @Override
    public String showBalance() {
        int sum = atm.getAtmCells().stream().mapToInt(cell -> cell.getCount() * cell.getNominal()).sum();
        return "" + sum;
    }
}
