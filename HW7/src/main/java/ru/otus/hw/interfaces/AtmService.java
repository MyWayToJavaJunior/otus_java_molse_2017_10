package ru.otus.hw.interfaces;

import ru.otus.hw.exception.NoSuchMoneyNominal;

public interface AtmService {

    void putOneNominal(int money) throws NoSuchMoneyNominal;
    String giveMoney(int money);
    int getBalance();
}
