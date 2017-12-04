package ru.otus.hw.service;

import ru.otus.hw.interfaces.AtmDepartmentService;
import ru.otus.hw.model.Atm;
import ru.otus.hw.model.AtmDepartment;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Optional;

public class AtmDepartmentServiceImpl implements AtmDepartmentService{

    private AtmDepartment atmDepartment;
    private static volatile AtmDepartmentServiceImpl instance;

    private AtmDepartmentServiceImpl() {}

    public static AtmDepartmentServiceImpl getInstance() {
        if (instance == null)
            synchronized (AtmDepartmentServiceImpl.class) {
                if (instance == null)
                    instance = new AtmDepartmentServiceImpl();
            }
        return instance;
    }

    public void setAtmDepartment(AtmDepartment atmDepartment) {
        this.atmDepartment = atmDepartment;
    }

    @Override
    public int getAtmsBalance() {
        return atmDepartment.getAtms().stream()
                .map(Atm::getAtmCells)
                .flatMap(Collection::stream)
                .mapToInt(cell -> cell.getNominal() * cell.getCount())
                .sum();
    }

    @Override
    public void saveAtmState(Atm atm) {
        try (FileOutputStream fileOut = new FileOutputStream("/tmp/" + atm.getUuid() + ".cer");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(atm);
            System.out.println("Резервное копирование состояния " + atm.getUuid() +" банкомата завершено");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreAtms() {
        atmDepartment.getAtms().forEach(this::restoreSingleAtmState);
    }

    private void restoreSingleAtmState(Atm atm) {
        Atm atmRestored = null;
        try (
            FileInputStream fileIn = new FileInputStream("/tmp/"+atm.getUuid()+".cer");
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            atmRestored = (Atm) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Atm class not found");
            c.printStackTrace();
        }
        if (atmRestored != null) {
            Atm finalAtmRestored = atmRestored;
            Optional<Atm> atmOptional = atmDepartment.getAtms().stream()
                    .filter(a -> a.getUuid().equals(finalAtmRestored.getUuid()))
                    .findFirst();
            if (atmOptional.isPresent()){
                atmOptional.get().setAtmCells(atmRestored.getAtmCells());
                System.out.println("Восстановление банкомата " +atm.getUuid()+ " завершено");
            }
            else {
                System.out.println("Восстановление банкомата " +atm.getUuid()+ "не возможно");
            }

        }
    }


}
