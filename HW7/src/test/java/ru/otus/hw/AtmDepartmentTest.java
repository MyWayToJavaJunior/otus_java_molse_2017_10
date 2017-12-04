package ru.otus.hw;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.hw.interfaces.AtmDepartmentService;
import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmCell;
import ru.otus.hw.model.AtmDepartment;
import ru.otus.hw.service.AtmDepartmentServiceImpl;
import ru.otus.hw.service.AtmServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AtmDepartmentTest {

    private  AtmDepartmentService atmDepartmentService;
    private AtmService atmService;
    private Atm atm1;
    private Atm atm2;

    @Before
    public void init(){
        List<AtmCell> atmCells = new ArrayList<>();
        atmCells.add(new AtmCell(5000,2));
        atmCells.add(new AtmCell(1000,5));
        atmCells.add(new AtmCell(500,10));
        atm1 = new Atm(atmCells);
        atmCells = new ArrayList<>();
        atmCells.add(new AtmCell(5000,1));
        atmCells.add(new AtmCell(1000,3));
        atmCells.add(new AtmCell(500,4));
        atm2 = new Atm(atmCells);
        AtmDepartment atmDepartment = new AtmDepartment();
        atmDepartment.addAtm(atm1);
        atmDepartment.addAtm(atm2);
        atmDepartmentService = AtmDepartmentServiceImpl.getInstance();
        atmDepartmentService.setAtmDepartment(atmDepartment);

        atmService = AtmServiceImpl.getInstance();
    }

    @Test
    public void showAllBalanceTest(){
        Assert.assertEquals(30000,atmDepartmentService.getAtmsBalance());
        atmService.setAtm(atm1);
        atmService.giveMoney(10000);
        Assert.assertEquals(20000,atmDepartmentService.getAtmsBalance());
        atmService.giveMoney(10000);
        Assert.assertEquals(10000,atmDepartmentService.getAtmsBalance());
        atmService.setAtm(atm2);
        atmService.giveMoney(10000);
        Assert.assertEquals(0,atmDepartmentService.getAtmsBalance());
    }

    @Test
    public void testRestoredAtmBalance(){
        Assert.assertEquals(30000,atmDepartmentService.getAtmsBalance());
        atmService.setAtm(atm1);
        atmService.giveMoney(10000);
        atmService.setAtm(atm2);
        atmService.giveMoney(3500);
        Assert.assertEquals(16500,atmDepartmentService.getAtmsBalance());
        atmDepartmentService.restoreAtms();
        Assert.assertEquals(30000,atmDepartmentService.getAtmsBalance());
    }
}
