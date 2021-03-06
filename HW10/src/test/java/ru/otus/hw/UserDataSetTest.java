package ru.otus.hw;

import org.hibernate.LazyInitializationException;
import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.otus.hw.base.DBService;
import ru.otus.hw.model.DataSet;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.service.DBServiceHibernateImpl;
import ru.otus.hw.service.DBServiceImpl;

import java.sql.SQLException;

public class UserDataSetTest {

    public static DBService dbService = new DBServiceImpl();

    @After
    public void after() throws IllegalAccessException {
      //  dbService.deleteAll(UserDataSet.class);
    }

    @Test
    public void testSave() throws SQLException, IllegalAccessException {
        UserDataSet user = new UserDataSet("XXXXXXXXXXXX",9999999);
        user.setId(100);
        dbService.save(user);
        UserDataSet load = dbService.load(100, UserDataSet.class);
        Assert.assertEquals(user,load);
    }

    @Test
    public void testDeleteAll() throws IllegalAccessException {
        UserDataSet user = new UserDataSet("XXXXXXXXXXXX",9999999);
        user.setId(100);
        dbService.save(user);
        dbService.deleteAll(UserDataSet.class);
        UserDataSet load = dbService.load(100, UserDataSet.class);
        Assert.assertNull(load);
    }

    @Test(expected = RuntimeException.class)
    public void testSendUncorrectClass() throws IllegalAccessException {
        dbService.deleteAll(DataSet.class);
        dbService.save(new DataSet() {
            @Override
            public long getId() {
                return super.getId();
            }
        });
    }

    @Test
    public void loadNotExisting() throws IllegalAccessException {
        UserDataSet load = dbService.load(111, UserDataSet.class);
        Assert.assertNull(load);
    }


}
