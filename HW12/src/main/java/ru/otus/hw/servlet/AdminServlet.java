package ru.otus.hw.servlet;


import ru.otus.hw.base.DBService;
import ru.otus.hw.utils.ReflectionHelper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by tully.
 */
public class AdminServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private DBService dbService;

    public AdminServlet(DBService dbService) {
        this.dbService = dbService;
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        Object cache = ReflectionHelper.getFieldValue(dbService, "cache");
        pageVariables.put("hit", ReflectionHelper.getFieldValue(cache,"hit"));
        pageVariables.put("miss", ReflectionHelper.getFieldValue(cache,"miss"));
        pageVariables.put("maxElements", ReflectionHelper.getFieldValue(cache,"maxElements"));


        //let's get login from session
        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);

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
            Map<String, Object> pageVariables = createPageVariablesMap(request);

            response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }


    }



}
