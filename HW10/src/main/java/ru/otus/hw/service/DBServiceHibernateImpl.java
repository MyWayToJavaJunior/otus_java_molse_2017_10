package ru.otus.hw.service;

import org.hibernate.*;
import org.hibernate.query.Query;
import ru.otus.hw.base.DBService;
import ru.otus.hw.connection.ConnectionHelper;
import ru.otus.hw.model.DataSet;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.service.dao.UserDataSetDAO;


import java.util.function.Function;


public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        sessionFactory = ConnectionHelper.getHibernateSessionFactory();
    }

    @Override
    public String getMetaData() {
        return runInSession(session -> getMetaData());
    }

    @Override
    public <T extends DataSet> void save(T user) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save((UserDataSet) user);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws IllegalAccessException {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return (T) dao.read(id);
        });
    }

    @Override
    public <T extends DataSet> void deleteAll(Class<T> clazz) throws IllegalAccessException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String stringQuery = "delete from " + clazz.getName();
            Query query = session.createQuery(stringQuery);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}