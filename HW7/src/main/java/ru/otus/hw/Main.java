package ru.otus.hw;

import ru.otus.hw.exception.NoSuchMoneyNominal;
import ru.otus.hw.interfaces.AtmDepartmentService;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmCell;
import ru.otus.hw.model.AtmDepartment;
import ru.otus.hw.service.AtmDepartmentServiceImpl;
import ru.otus.hw.service.AtmServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(final String... args) {

        Atm atmFirst = new Atm(createRandomAtmCells());
        Atm atmSecond = new Atm(createRandomAtmCells());
        AtmDepartment atmDep = new AtmDepartment();
        AtmDepartmentService atmDepartmentService = AtmDepartmentServiceImpl.getInstance();
        atmDepartmentService.setAtmDepartment(atmDep);
        atmDep.addAtm(atmFirst);
        atmDep.addAtm(atmSecond);
        System.out.println("Текущий баланс всех банкоматов " + atmDepartmentService.getAtmsBalance());

        AtmService atmService = AtmServiceImpl.getInstance();
        atmService.setAtm(atmFirst);
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
        atmService.setAtm(atmSecond);
        System.out.println(atmService.giveMoney(6700));
        System.out.println(atmService.giveMoney(1500));
        System.out.println(atmService.giveMoney(10000));
        System.out.println("Текущий баланс " + atmService.getBalance());
        atmDepartmentService.restoreAtms();
        System.out.println("Текущий баланс всех банкоматов " + atmDepartmentService.getAtmsBalance());
        System.out.println("Текущий баланс " + atmService.getBalance());
        System.out.println(atmService.giveMoney(5500));
        System.out.println("Текущий баланс всех банкоматов " + atmDepartmentService.getAtmsBalance());


    }

    private static List<AtmCell> createRandomAtmCells() {
        List<AtmCell> cells = new ArrayList<>();
        cells.add(new AtmCell(5000,random()));
        cells.add(new AtmCell(1000,random()));
        cells.add(new AtmCell(500,random()));
        cells.add(new AtmCell(50,random()));
        return cells;
    }

    private static int random() {
        return ThreadLocalRandom.current().nextInt(1,20);
    }


}
