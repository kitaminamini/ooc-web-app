package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.PasswordGenerator;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditUserServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            if (securityService.isAuthorized(request)){
                //            System.out.println("in doget of edit");
                String username = request.getParameter("username");
//            System.out.println(username);
                request.setAttribute("usern", username);
                JDBC jdbc = new JDBC();
                jdbc.connect();
                ResultSet resultSet = jdbc.getUserInfo(username);
                resultSet.next();
                String password = resultSet.getString("password");
                String nickname = resultSet.getString("nickname");
                request.setAttribute("pass", password);
                request.setAttribute("nick", nickname);
                jdbc.getConnection().close();
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                rd.include(request, response);
            }

            else{
                String error = "You have not logged in.";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                rd.include(request, response);
            }

        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            if (securityService.isAuthorized(request)){

                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String nickname = request.getParameter("nickname");

                String usernameToEdit = request.getParameter("usernameToEdit");
                String loggingInUser = (String) request.getSession()
                        .getAttribute("username");

                if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password) && !StringUtils.isBlank(nickname)) {
//                System.out.println("params not blank");
                    if (!username.equals(usernameToEdit) && securityService.isUsernameExist(username)) {
                        String error = "This username is already used.";
                        request.setAttribute("error", error);
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                        rd.include(request, response);
                    }

                    else {
                        // if user change his own username
                        if (loggingInUser.equals(usernameToEdit)){
                            request.getSession().setAttribute("username", username);
                        }

                        JDBC jdbc = new JDBC();

                        jdbc.connect();
                        ResultSet resultSet = jdbc.getUserInfo(usernameToEdit);
                        resultSet.next();
                        String hashed = resultSet.getString("password");
                        if (!password.equals(hashed)){
                            password = PasswordGenerator.hash(password);
                        }
                        jdbc.getConnection().close();

                        jdbc.connect();
                        jdbc.editUser(username, password, nickname, usernameToEdit);
                        jdbc.getConnection().close();

                        String error = "Successfully edited the user information.";
                        request.setAttribute("error", error);
                        response.sendRedirect("/userlist");
//                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
//                    rd.include(request, response);
                    }

                } else {
                    String error = "Parameter is invalid or missing.";
                    request.setAttribute("error", error);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/edituser.jsp");
                    rd.include(request, response);
                }
            }

            else{
                String error = "You have not logged in.";
                request.setAttribute("error", error);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
                rd.include(request, response);
            }

        } catch (SQLException se) {
        }
    }

    @Override
    public String getMapping() {
        return "/edituser";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
