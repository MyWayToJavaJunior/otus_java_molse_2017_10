package ru.otus.hw.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER_NAME = "username";
    public static final String PASS_PARAMETER_NAME = "password";


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String requestPassword = request.getParameter(PASS_PARAMETER_NAME);


        if ("admin".equals(requestLogin) && "admin".equals(requestPassword) ) {
            saveToCookie(response, requestLogin);
            response.setStatus(HttpServletResponse.SC_OK);;
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        System.out.println("teeeeeeeeest");

    }

    private void saveToCookie(HttpServletResponse response, String requestLogin) {
        response.addCookie(new Cookie("MOLSE", requestLogin));
    }
}
