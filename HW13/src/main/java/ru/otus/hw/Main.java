package ru.otus.hw;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.hw.base.DBService;
import ru.otus.hw.model.AddressDataSet;
import ru.otus.hw.model.PhoneDataSet;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.service.DBServiceHibernateImpl;
import ru.otus.hw.servlet.AdminServlet;
import ru.otus.hw.servlet.LoginServlet;

import java.util.Collections;


public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";
    private final static DBService dbService = new DBServiceHibernateImpl();

    public static void main(final String... args) throws Exception {

        new Main().run();
    }

    private void run() throws Exception {

        caliente();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new AdminServlet(dbService)), "/admin");
        context.addServlet(LoginServlet.class, "/login");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();

    }

    private void caliente() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    while (true) {
                        dbService.save(new UserDataSet("Иванов Иван",25, new AddressDataSet("Тверская"), Collections.singletonList(new PhoneDataSet("111111111"))));
                        UserDataSet load = dbService.load(1, UserDataSet.class);
                        Thread.sleep(100);
                        UserDataSet load2 = dbService.load(1, UserDataSet.class);
                        UserDataSet load3 = dbService.load(1, UserDataSet.class);
                        UserDataSet load4 = dbService.load(1, UserDataSet.class);
                    }

                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }


}


