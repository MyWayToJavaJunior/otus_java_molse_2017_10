package ru.otus.hw.service;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.otus.hw.base.DBService;
import ru.otus.hw.cache.CacheEngine;
import ru.otus.hw.cache.CacheEngineImpl;
import ru.otus.hw.cache.MyElement;
import ru.otus.hw.connection.ConnectionHelper;
import ru.otus.hw.model.DataSet;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.service.dao.UserDataSetDAO;
import ru.otus.hw.utils.ReflectionHelper;


import java.lang.ref.SoftReference;
import java.util.function.Function;

@Component
public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, Object> cache;

    public DBServiceHibernateImpl() {
        sessionFactory = ConnectionHelper.getHibernateSessionFactory();
        cache = new CacheEngineImpl<>(5, 3000, 0, false);
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

        Object id = ReflectionHelper.getFieldValue(user, "id");
        cache.put(new MyElement<>((long)ReflectionHelper.getFieldValue(user, "id"), new SoftReference<>(user)));

    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws IllegalAccessException {
        MyElement<Long, Object> longObjectMyElement = cache.get(id);

        if (longObjectMyElement != null) {
            SoftReference<T> value = (SoftReference<T>) longObjectMyElement.getValue();
            if (value != null) return value.get();
        }

        T userFromBase = runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            T user = (T) dao.read(id);
            return user;
        });
        System.out.println("Cache hit = " + cache.getHitCount());
        System.out.println("Cache miss = " + cache.getMissCount());
        cache.put(new MyElement<>(Long.valueOf(id), new SoftReference<>(userFromBase)));

        return userFromBase;
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
        cache.clean();
    }


    public CacheEngine<Long, Object> getCache() {
        return cache;
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