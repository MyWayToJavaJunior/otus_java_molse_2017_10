package ru.otus.hw;

import ru.otus.hw.base.DBService;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.service.DBServiceImpl;

public class Main {

    public static void main(final String... args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        UserDataSet test = new UserDataSet("XXXX",25);

        try (DBService dbService = new DBServiceImpl()) {
            /*System.out.println(dbService.getMetaData());
            dbService.save(test);
            UserDataSet load = dbService.load(6, UserDataSet.class);
            System.out.println(load);*/
            dbService.deleteAll(UserDataSet.class);

        }
    }


}
