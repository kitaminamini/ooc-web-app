/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

import io.muic.ooc.webapp.mysql.JDBC;

/**
 *
 * @author frostbyte
 */
public class SecurityService {


    public boolean isUsernameExist(String username) throws SQLException{
        JDBC jdbc = new JDBC();
        jdbc.connect();
        boolean isExist = jdbc.usernameExist(username);
        jdbc.getConnection().close();
        return isExist;
    }

    
    public boolean isAuthorized(HttpServletRequest request) throws SQLException{
        String username = (String) request.getSession()
                .getAttribute("username");
        // do checking
        JDBC jdbc = new JDBC();
        jdbc.connect();

        //STEP 4: Execute a query

        boolean isAutho = jdbc.usernameExist(username);
        jdbc.getConnection().close();
        return isAutho;
    }
    
    public boolean authenticate(String username, String password, HttpServletRequest request) throws SQLException{
        JDBC jdbc = new JDBC();
        jdbc.connect();

        //STEP 4: Execute a query
        boolean isAurthenticate = (jdbc.isValidLogin(username, password));
        jdbc.getConnection().close();
        return isAurthenticate;

    }
    
    public void logout(HttpServletRequest request) {
        request.getSession().setAttribute("username", null);
        request.getSession().invalidate();
    }
    
}
