package ru.otus.hw.model;

import ru.otus.hw.interfaces.AtmService;
import ru.otus.hw.service.AtmServiceImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtmDepartment {
    private List<Atm> atms = new ArrayList<>();

    public void addAtm(Atm atm) {
        saveAtmState(atm);
        this.atms.add(atm);
    }

    private void saveAtmState(Atm atm) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/tmp/"+atm.getUuid()+".cer");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(atm);
            out.close();
            fileOut.close();
            System.out.println("Резервное копирование состояния банкомата завершено");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void restoreAtmsState() {
        atms.forEach(this::restoreSingleAtmState);
    }

    private void restoreSingleAtmState(Atm atm) {
        Atm atmRestored = null;
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/"+atm.getUuid()+".cer");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            atmRestored = (Atm) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Atm class not found");
            c.printStackTrace();
        }
        if (atmRestored != null) {
            Atm finalAtmRestored = atmRestored;
            Optional<Atm> atmOptional = atms.stream()
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

    public int getSummaryBalance() {
        return atms.stream().map(atm -> {
            AtmService instance = AtmServiceImpl.getInstance();
            instance.setAtm(atm);
            return instance.getBalance();
        }).mapToInt(Integer::valueOf).sum();
    }
}
