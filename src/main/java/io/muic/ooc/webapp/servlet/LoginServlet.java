package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.SecurityService;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import io.muic.ooc.webapp.Routable;

public class LoginServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String username = request.getParameter("username");
            String password = request.getParameter("password");
//            if(request.getParameter("Register")!=null){
//                response.sendRedirect("/register");
//            }
            if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
                if (securityService.authenticate(username, password, request)) {
                    request.getSession().setAttribute("username", username);
                    response.sendRedirect("/userlist");
                } else {
                    String error = "Wrong username or password.";
                    request.setAttribute("error", error);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                    rd.include(request, response);
                }
            } else {
                String error = "Username or password is missing.";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                rd.include(request, response);
            }
        }catch (SQLException se){}

    }

    @Override
    public String getMapping() {
        return "/login";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
