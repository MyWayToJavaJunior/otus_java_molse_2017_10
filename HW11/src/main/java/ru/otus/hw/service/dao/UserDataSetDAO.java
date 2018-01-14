package ru.otus.hw.service.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.otus.hw.model.UserDataSet;



public class UserDataSetDAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        Transaction transaction = session.beginTransaction();
        session.save(dataSet);
        transaction.commit();
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }

}
