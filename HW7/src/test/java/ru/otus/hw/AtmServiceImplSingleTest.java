package ru.otus.hw;
import org.junit.Before;
import org.junit.Test;
import ru.otus.hw.exception.NoSuchMoneyNominal;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmCell;
import ru.otus.hw.service.AtmServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AtmServiceImplSingleTest {

    private AtmService atmService;

    @Before
    public void init(){
        List<AtmCell> atmCells = new ArrayList<>();
        atmCells.add(new AtmCell(1000,10));
        atmCells.add(new AtmCell(500,10));
        Atm atm = new Atm(atmCells);
        atmService = AtmServiceImpl.getInstance();
        atmService.setAtm(atm);

    }

    @Test
    public void testInitShowBalance(){
        assertEquals(15000,atmService.getBalance());
        assertEquals("Выдача: 1x1000", atmService.giveMoney(1000));
        assertEquals(14000,atmService.getBalance());
    }

    @Test
    public void testPutOneMoneyCorrectly() throws NoSuchMoneyNominal {
        atmService.putOneNominal(1000);
        assertEquals(16000, atmService.getBalance());
        atmService.putOneNominal(500);
        assertEquals(16500, atmService.getBalance());
    }

    @Test(expected = NoSuchMoneyNominal.class)
    public void testPutOneMoneyWithNotExistingAtmCell() throws NoSuchMoneyNominal {
        atmService.putOneNominal(99);
    }

    @Test
    public void giveSmallAmountOfMoney(){
        assertEquals("Выдача: 1x1000", atmService.giveMoney(1000));
        assertEquals("Выдача: 3x1000", atmService.giveMoney(3000));
        assertEquals("Выдача: 1x500", atmService.giveMoney(500));
        assertEquals("Выдача: 1x500,1x1000", atmService.giveMoney(1500));
        assertEquals(9000, atmService.getBalance());
    }

    @Test
    public void giveAllSingleNominalOfMoney(){
        assertEquals("Выдача: 10x1000", atmService.giveMoney(10000));
        assertEquals("Выдача: 3x500", atmService.giveMoney(1500));
    }

    @Test
    public void giveAllMoneyAndTryGiveMoreAndMore(){
        assertEquals("Выдача: 10x500,10x1000", atmService.giveMoney(15000));
        assertEquals(0, atmService.getBalance());
        assertEquals("Выдача невозможна", atmService.giveMoney(15000));
    }

    @Test
    public void giveSumWithNotExistsNominal(){
        assertEquals("Выдача невозможна", atmService.giveMoney(1700));
        assertEquals(15000, atmService.getBalance());
    }

}
