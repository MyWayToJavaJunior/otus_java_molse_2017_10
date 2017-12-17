package ru.otus.hw;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.hw.base.DBService;
import ru.otus.hw.connection.ConnectionHelper;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.service.DBServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDataSetTest {

    @Test
    public void testSave() throws SQLException, IllegalAccessException {
        UserDataSet user = new UserDataSet("XXXXXXXXXXXX",9999999);
        user.setId(5555);
        DBService dbService = new DBServiceImpl();
        dbService.save(user);

        UserDataSet load = dbService.load(5555, UserDataSet.class);
        Assert.assertEquals(user,load);
    }

    @Test
    public void loadNotExistingUser() throws IllegalAccessException {
        DBService dbService = new DBServiceImpl();
        UserDataSet load = dbService.load(111, UserDataSet.class);
        Assert.assertNull(load);

    }
}
