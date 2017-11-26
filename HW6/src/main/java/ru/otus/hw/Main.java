package ru.otus.hw;

import ru.otus.hw.service.AtmServiceImpl;

public class Main {

    public static void main(final String... args) {

        AtmServiceImpl atm = new AtmServiceImpl();
        System.out.println(atm.showBalance());
        atm.putMoney(1000);
        System.out.println(atm.showBalance());
        System.out.println(atm.giveMoney(15000));
        System.out.println(atm.showBalance());
    }
}
