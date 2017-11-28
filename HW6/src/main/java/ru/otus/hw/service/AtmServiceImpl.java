package ru.otus.hw.service;

import ru.otus.hw.NoSuchMoneyNominal;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public void putMoney(int money) throws NoSuchMoneyNominal {
        AtmCell atmCell = atm.getAtmCells().stream().filter(cell -> cell.getNominal() == money).findFirst()
                .orElseThrow(() -> new NoSuchMoneyNominal("Банкомат не принимает купюры номинала: "+ money));
        atmCell.increaseCount(1);
    }

    @Override
    public String giveMoney(int money) {
        List<AtmCell> atmCells = atm.getAtmCells().stream().filter(cell->cell.getCount()>0).collect(Collectors.toList());
        Collections.sort(atmCells);
        List<AtmCell> resultAtmCells = new ArrayList<>();
        for (AtmCell atmCell: atmCells) {
            int count = money / atmCell.getNominal();
            if (count > atmCell.getCount()) count = atmCell.getCount();
            resultAtmCells.add(new AtmCell(atmCell.getNominal(),count));
            money = money - count* atmCell.getNominal();
            atmCell.descreaseCount(count);
        }
        if (money != 0) return "Выдача невозможна";
        return "Выданы:" + resultAtmCells.stream().filter(t->t.getCount() != 0).collect(Collectors.toList()).toString();
    }

    @Override
    public String showBalance() {
        int sum = atm.getAtmCells().stream().mapToInt(cell -> cell.getCount() * cell.getNominal()).sum();
        return "Текущий баланс: " + sum;
    }
}
