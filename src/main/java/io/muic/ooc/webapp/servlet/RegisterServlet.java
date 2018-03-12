package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.mysql.JDBC;
import io.muic.ooc.webapp.service.SecurityService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do login post logic
        // extract username and password from request
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String nickname = request.getParameter("nickname");

            if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password) && !StringUtils.isBlank(nickname)) {
//                System.out.println("paras not blank");
                if (!securityService.isUsernameExist(username)){
                    JDBC jdbc = new JDBC();
                    jdbc.connect();

//                    String query = "INSERT INTO user (username,password,nickname) VALUES ("+username+","+password+","+nickname+");";
                    jdbc.addUser(username, password, nickname);
                    jdbc.getConnection().close();

                    String error = "You finished registration.\n" +
                            "Now you can login.";
                    request.setAttribute("error", error);
                    request.setAttribute("usern",username);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                    rd.include(request, response);
                }

                else {
                    String error = "This username is already used.";
                    request.setAttribute("error", error);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
                    rd.include(request, response);
                }


            } else {
                String error = "Parameter is invalid or missing.";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/register.jsp");
                rd.include(request, response);
            }
        } catch (SQLException se) {
        }
    }

    @Override
    public String getMapping() {
        return "/register";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}
