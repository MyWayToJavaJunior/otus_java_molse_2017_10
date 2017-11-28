package ru.otus.hw.service;

import ru.otus.hw.exception.NoSuchMoneyNominal;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmCell;

import java.util.*;
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
    public void putOneNominal(int money) throws NoSuchMoneyNominal {
        AtmCell atmCell = atm.getAtmCells().stream().filter(cell -> cell.getNominal() == money).findFirst()
                .orElseThrow(() -> new NoSuchMoneyNominal("Банкомат не принимает купюры номинала: "+ money));
        atmCell.increaseCount(1);
    }

    @Override
    public String giveMoney(int money) {
        List<AtmCell> atmCells = atm.getAtmCells().stream().filter(cell->cell.getCount()>0).collect(Collectors.toList());
        Collections.sort(atmCells, (o1, o2) -> {
            if (o1.getNominal()> o2.getNominal()) return -1;
            else if (o1.getNominal()< o2.getNominal()) return 1;
            return 0;
        });

        Map<Integer,Integer> nominalCountMap = new HashMap<>();
        for (AtmCell atmCell: atmCells) {
            int count = money / atmCell.getNominal();
            if (count > atmCell.getCount()) { count = atmCell.getCount(); }
            nominalCountMap.put(atmCell.getNominal(),count);
            money = money - count* atmCell.getNominal();
        }
        if (money != 0) return "Выдача невозможна";
        atmCells.forEach(atmCell -> {
            if (nominalCountMap.containsKey(atmCell.getNominal())) {
                atmCell.descreaseCount(nominalCountMap.get(atmCell.getNominal()));
            }
        });
        return nominalCountMap.entrySet().stream().filter(map -> map.getValue() != 0)
                .map(map -> map.getValue() + "x" + map.getKey()).collect(Collectors.joining(","));

    }

    @Override
    public String showBalance() {
        int sum = atm.getAtmCells().stream().mapToInt(cell -> cell.getCount() * cell.getNominal()).sum();
        return "Текущий баланс: " + sum;
    }
}
