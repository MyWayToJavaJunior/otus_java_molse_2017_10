package ru.otus.hw.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw.model.AddressDataSet;
import ru.otus.hw.model.PhoneDataSet;
import ru.otus.hw.model.UserDataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/otus?user=root&password=root&serverTimezone=Europe/Moscow";

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   public static SessionFactory getHibernateSessionFactory() {
       Configuration configuration = new Configuration();

       configuration.addAnnotatedClass(UserDataSet.class);
       configuration.addAnnotatedClass(PhoneDataSet.class);
       configuration.addAnnotatedClass(AddressDataSet.class);


       configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
       configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
       configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/otus?serverTimezone=Europe/Moscow");
       configuration.setProperty("hibernate.connection.username", "root");
       configuration.setProperty("hibernate.connection.password", "root");
       configuration.setProperty("hibernate.show_sql", "true");
       configuration.setProperty("hibernate.hbm2ddl.auto", "create");
       configuration.setProperty("hibernate.connection.useSSL", "false");
       configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

       // HikariCP settings
       configuration.setProperty("hibernate.hikari.connectionTimeout", "20000");
       configuration.setProperty("hibernate.hikari.minimumIdle", "10");
       configuration.setProperty("hibernate.hikari.maximumPoolSize", "20");
       configuration.setProperty("hibernate.hikari.idleTimeout", "300000");

       StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
       builder.applySettings(configuration.getProperties());
       ServiceRegistry serviceRegistry = builder.build();

       return configuration.buildSessionFactory(serviceRegistry);
   }
}
