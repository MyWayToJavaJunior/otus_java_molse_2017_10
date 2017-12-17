package ru.otus.hw;

import ru.otus.hw.base.DBService;
import ru.otus.hw.service.DBServiceImpl;

import java.util.List;

public class Main {

    public static void main(final String... args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = new DBServiceImpl()) {
            System.out.println(dbService.getMetaData());
            dbService.getAllUsers();
            /*dbService.addUsers("tully", "sully");
            System.out.println("UserName with id = 1: " + dbService.getUserName(1));
            List<String> names = dbService.getAllNames();
            System.out.println("All names: " + names.toString());
            List<UsersDataSet> users = dbService.getAllUsers();
            System.out.println("All users: " + users.toString());
            dbService.deleteTables();*/
        }
    }


}
