package ru.otus.hw.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw.model.AddressDataSet;
import ru.otus.hw.model.PhoneDataSet;
import ru.otus.hw.model.UserDataSet;

public class ConnectionHelper {

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
       configuration.setProperty("hibernate.format_sql", "true");
       configuration.setProperty("hibernate.comment_sql", "true");
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
