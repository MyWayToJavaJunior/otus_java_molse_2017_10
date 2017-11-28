package ru.otus.hw;

import ru.otus.hw.exception.NoSuchMoneyNominal;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.service.AtmServiceImpl;

public class Main {

    public static void main(final String... args) {

        AtmService atm = new AtmServiceImpl();
        System.out.println(atm.showBalance());
        try {
            atm.putOneNominal(3000);
        } catch (NoSuchMoneyNominal e) {
            System.out.println(e.toString());
        }
        System.out.println(atm.showBalance());
        System.out.println(atm.giveMoney(99));
        System.out.println(atm.showBalance());
        System.out.println(atm.giveMoney(2000));
        System.out.println(atm.showBalance());
    }
}
