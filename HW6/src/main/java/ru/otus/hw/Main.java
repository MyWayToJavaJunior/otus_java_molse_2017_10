package ru.otus.hw;

import ru.otus.hw.service.AtmServiceImpl;

public class Main {

    public static void main(final String... args) {

        AtmServiceImpl atm = new AtmServiceImpl();
        System.out.println(atm.showBalance());
        try {
            atm.putMoney(1100);
        } catch (NoSuchMoneyNominal e) {
            System.out.println(e.toString());
        }
        System.out.println(atm.showBalance());
        System.out.println(atm.giveMoney(1500));
        System.out.println(atm.showBalance());
    }
}
