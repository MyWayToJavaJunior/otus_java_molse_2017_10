package ru.otus.hw;

import ru.otus.hw.exception.NoSuchMoneyNominal;
import ru.otus.hw.exception.NotFoundRestoredAtm;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmCell;
import ru.otus.hw.model.AtmDepartment;
import ru.otus.hw.service.AtmServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(final String... args) {

        Atm atm = new Atm(createRandomAtmCells());
        Atm atmSecond = new Atm(createRandomAtmCells());
        AtmDepartment atmDep = new AtmDepartment();
        atmDep.addAtm(atm);
        atmDep.addAtm(atmSecond);
        System.out.println("Текущий баланс всех банкоматов " + atmDep.getSummaryBalance());

        AtmService atmService = new AtmServiceImpl(atm);
        System.out.println("Текущий баланс " + atmService.getBalance());
        try {
            atmService.putOneNominal(3000);
        } catch (NoSuchMoneyNominal e) {
            System.out.println(e.toString());
        }
        System.out.println("Текущий баланс " + atmService.getBalance());
        System.out.println(atmService.giveMoney(99));
        System.out.println("Текущий баланс " + atmService.getBalance());
        System.out.println(atmService.giveMoney(2000));
        System.out.println("Текущий баланс " + atmService.getBalance());
        try {
            atmDep.restoreAtmState(atm);
        } catch (NotFoundRestoredAtm e) {
            System.out.println(e.toString());
        }
        System.out.println("Текущий баланс " + atmService.getBalance());
        System.out.println(atmService.giveMoney(2000));
        System.out.println("Текущий баланс всех банкоматов " + atmDep.getSummaryBalance());


    }

    private static List<AtmCell> createRandomAtmCells() {
        List<AtmCell> cells = new ArrayList<>();
        cells.add(new AtmCell(1000,10));
        cells.add(new AtmCell(500,10));
        return cells;
    }
}
