package ru.otus.hw.interfaces;

import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmDepartment;

public interface AtmDepartmentService {
    void restoreAtms();
    int getAtmsBalance();
    void setAtmDepartment(AtmDepartment atmDepartment);
    void saveAtmState(Atm atm);
}
