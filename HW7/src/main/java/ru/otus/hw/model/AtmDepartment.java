package ru.otus.hw.model;

import ru.otus.hw.service.AtmDepartmentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AtmDepartment {
    private List<Atm> atms = new ArrayList<>();

    public void addAtm(Atm atm) {
        AtmDepartmentServiceImpl.getInstance().saveAtmState(atm);
        this.atms.add(atm);
    }

    public List<Atm> getAtms() {
        return atms;
    }

    public void setAtms(List<Atm> atms) {
        this.atms = atms;
    }

}
