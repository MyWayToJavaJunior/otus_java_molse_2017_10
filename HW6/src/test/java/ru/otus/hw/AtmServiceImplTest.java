package ru.otus.hw;
import org.junit.Before;
import org.junit.Test;
import ru.otus.hw.exception.NoSuchMoneyNominal;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.service.AtmServiceImpl;

import static org.junit.Assert.assertEquals;

public class AtmServiceImplTest {

    private AtmService atm;

    @Before
    public void init(){
        atm = new AtmServiceImpl();

    }

    @Test
    public void testInitShowBalance(){
        assertEquals("Текущий баланс: 15000",atm.showBalance());
        assertEquals("1x1000", atm.giveMoney(1000));
        assertEquals("Текущий баланс: 14000",atm.showBalance());
    }

    @Test
    public void testPutOneMoneyCorrectly() throws NoSuchMoneyNominal {
        atm.putOneNominal(1000);
        assertEquals("Текущий баланс: 16000", atm.showBalance());
        atm.putOneNominal(500);
        assertEquals("Текущий баланс: 16500", atm.showBalance());
    }

    @Test(expected = NoSuchMoneyNominal.class)
    public void testPutOneMoneyWithNotExistingAtmCell() throws NoSuchMoneyNominal {
        atm.putOneNominal(99);
    }

    @Test
    public void giveSmallAmountOfMoney(){
        assertEquals("1x1000", atm.giveMoney(1000));
        assertEquals("3x1000", atm.giveMoney(3000));
        assertEquals("1x500", atm.giveMoney(500));
        assertEquals("1x500,1x1000", atm.giveMoney(1500));
        assertEquals("Текущий баланс: 9000", atm.showBalance());
    }

    @Test
    public void giveAllSingleNominalOfMoney(){
        assertEquals("10x1000", atm.giveMoney(10000));
        assertEquals("3x500", atm.giveMoney(1500));
    }

    @Test
    public void giveAllMoneyAndTryGiveMoreAndMore(){
        assertEquals("10x500,10x1000", atm.giveMoney(15000));
        assertEquals("Текущий баланс: 0", atm.showBalance());
        assertEquals("Выдача невозможна", atm.giveMoney(15000));
    }

    @Test
    public void giveSumWithNotExistsNominal(){
        assertEquals("Выдача невозможна", atm.giveMoney(1700));
        assertEquals("Текущий баланс: 15000", atm.showBalance());
    }

}
