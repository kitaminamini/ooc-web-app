package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.Routable;
import io.muic.ooc.webapp.User;
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
import java.util.ArrayList;
import java.util.List;

public class UserListServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    private List<User> makeUserList() throws SQLException{
        List<User> userList = new ArrayList<>();

        JDBC jdbc = new JDBC();
        jdbc.connect();
        ResultSet resultSet = jdbc.getAll();
        while(resultSet.next()){
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String nickname = resultSet.getString("nickname");
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNickname(nickname);
            userList.add(user);
            //isValid = resultSet.next();
        }


        jdbc.getConnection().close();
        return userList;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("in doget of ulst");
        try{

            if (securityService.isAuthorized(request)){
                List<User> ulist = makeUserList();
                System.out.println(ulist);

                String username = (String) request.getSession().getAttribute("username");
                JDBC jdbc = new JDBC();
                jdbc.connect();
                ResultSet resultSet =jdbc.getUserInfo(username);
                resultSet.next();
                String nickname = resultSet.getString("nickname");
                jdbc.getConnection().close();

                request.setAttribute("loginNickname", nickname);
                request.setAttribute("users",ulist);

                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/userlist.jsp");
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
        System.out.println("in post of userlist");
        try{
            String username = request.getParameter("username");
            String loggingInUser = (String) request.getSession()
                    .getAttribute("username");

            if (loggingInUser.equals(username)){
                String error = "You can not remove yourself.";
                request.setAttribute("error", error);
                response.sendRedirect("/userlist");
            }

            else{
                JDBC jdbc = new JDBC();
                jdbc.connect();
                jdbc.removeUser(username);
                jdbc.getConnection().close();
                String error = "Successfully removed user.";
                request.setAttribute("error", error);
                response.sendRedirect("/userlist");
            }

        }catch (SQLException se){
            se.printStackTrace();
        }


    }

    @Override
    public String getMapping() {
        return "/userlist";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
