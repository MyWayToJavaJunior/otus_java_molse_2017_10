package ru.otus.hw.interfaces;

import ru.otus.hw.NoSuchMoneyNominal;

public interface AtmService {

    void putMoney(int money) throws NoSuchMoneyNominal;
    String giveMoney(int money);
    String showBalance();
}
