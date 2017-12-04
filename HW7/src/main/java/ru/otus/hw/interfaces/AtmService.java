package ru.otus.hw.interfaces;

import ru.otus.hw.exception.NoSuchMoneyNominal;
import ru.otus.hw.model.Atm;

public interface AtmService {

    void putOneNominal(int money) throws NoSuchMoneyNominal;
    String giveMoney(int money);
    int getBalance();
    void setAtm(Atm atm);
}
