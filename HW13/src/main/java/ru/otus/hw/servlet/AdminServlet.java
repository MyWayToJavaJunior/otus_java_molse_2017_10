package ru.otus.hw.servlet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.hw.base.DBService;
import ru.otus.hw.model.AddressDataSet;
import ru.otus.hw.model.PhoneDataSet;
import ru.otus.hw.model.UserDataSet;
import ru.otus.hw.utils.ReflectionHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configurable
public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    @Autowired
    private DBService dbService;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    public AdminServlet() {
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        Object cache = ReflectionHelper.getFieldValue(dbService, "cache");
        pageVariables.put("hit", ReflectionHelper.getFieldValue(cache,"hit"));
        pageVariables.put("miss", ReflectionHelper.getFieldValue(cache,"miss"));
        pageVariables.put("maxElements", ReflectionHelper.getFieldValue(cache,"maxElements"));

        return pageVariables;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(ck -> ck.getName().equals("MOLSE"))
                .findFirst();

        if (cookie.isPresent()) {
            dbService.save(new UserDataSet("Иванов Иван",25, new AddressDataSet("Тверская"), Collections.singletonList(new PhoneDataSet("111111111"))));
            try {
                UserDataSet load = dbService.load(1, UserDataSet.class);
                Thread.sleep(100);
                UserDataSet load2 = dbService.load(1, UserDataSet.class);
                UserDataSet load3 = dbService.load(1, UserDataSet.class);
                UserDataSet load4 = dbService.load(1, UserDataSet.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Map<String, Object> pageVariables = createPageVariablesMap(request);

            response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }


    }



}
