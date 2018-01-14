package ru.otus.hw;

import ru.otus.hw.base.DBService;
import ru.otus.hw.model.AddressDataSet;
import ru.otus.hw.model.PhoneDataSet;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.service.DBServiceHibernateImpl;

import java.lang.ref.SoftReference;
import java.util.Collections;

public class Main {

    public static void main(final String... args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {


        try (DBService dbService = new DBServiceHibernateImpl()) {
            dbService.save(new UserDataSet("Иванов Иван",25, new AddressDataSet("Тверская"), Collections.singletonList(new PhoneDataSet("111111111"))));
            UserDataSet load = dbService.load(1, UserDataSet.class);
            UserDataSet load2 = dbService.load(1, UserDataSet.class);
            Thread.sleep(1000);
            System.gc();
            UserDataSet load3 = dbService.load(1, UserDataSet.class);
            System.out.println(load);



        }
    }


}
