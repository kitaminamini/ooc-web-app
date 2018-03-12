/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.service.SecurityService;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gigadot
 */
public class HomeServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/index.jsp";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doget");
        try{
            boolean authorized = securityService.isAuthorized(request);
            if (authorized) {
                // do MVC in here
//                String username = (String) request.getSession().getAttribute("username");
//                request.setAttribute("username", username);
//                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
//                rd.include(request, response);
                response.sendRedirect("/userlist");
            } else {
                response.sendRedirect("/login");
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("Logout")!=null){
            securityService.logout(request);
            response.sendRedirect("/login");
        }
    }
}
