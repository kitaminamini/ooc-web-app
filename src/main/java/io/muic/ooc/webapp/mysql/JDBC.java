package io.muic.ooc.webapp.mysql;
import io.muic.ooc.webapp.PasswordGenerator;

import java.sql.*;

public class JDBC {
    //  Database credentials
    static final String DB_URL = "jdbc:mysql://localhost:3306/ooc";
    static final String USER = "ooc";
    static final String PASS = "11223344";
//    static final String tableName = "oochw3";

    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static Connection connection = null;

    public Connection getConnection(){
        return connection;
    }


    public void connect(){
//        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            if (connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public ResultSet getAll() {
        String query = "SELECT * FROM user";
        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getUserInfo(String username) {
        String query = "SELECT * FROM user WHERE username=?;";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return resultSet;
    }

    public boolean usernameExist(String username){
        String query = "SELECT * FROM user WHERE username=?;";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return false;
    }


    public boolean isValidLogin(String username, String password){
        String query = "SELECT * FROM user WHERE username=?;";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String hashedPassword = resultSet.getString("password");
            System.out.println(hashedPassword);
            return PasswordGenerator.verifyHash(password, hashedPassword);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return false;
    }


    public void addUser(String username, String password, String  nickname){
        String query = "INSERT INTO user (username,password,nickname) VALUE(?,?,?);";
        try{
            password = PasswordGenerator.hash(password);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nickname);
            preparedStatement.executeUpdate();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }

    public void removeUser(String username){
        System.out.println("in removeUser");
        String query = "DELETE FROM user WHERE username=?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }

    public void editUser(String username, String password, String  nickname, String usernameToEdit){
        ResultSet RS = getUserInfo(usernameToEdit);

        String query = "UPDATE user SET username=?,password=?,nickname=? WHERE id=?;";
        try{
            int id;
            if (RS.first()){
                id = RS.getInt("id");
                System.out.println("ID: " + id);
            }else {
                System.out.println("not exist");
                // Say user does not exist;
                return;
            }


            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nickname);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }

//
//    public static void main(String[] args) {
//        Statement stmt = null;
//        try{
//            //STEP 2: Register JDBC driver
//            Class.forName("com.mysql.jdbc.Driver");
//
//            //STEP 3: Open a connection
//            System.out.println("Connecting to database...");
//
//
//            //STEP 4: Execute a query
//            System.out.println("Creating database...");
//            String query = "SELECT * FROM user WHERE username="+";";
//            preparedStatement = getConnection().prepareStatement(query);
//            preparedStatement.setString(1,"taro");
//            resultSet = preparedStatement.executeQuery().getString("username");
//
//            String sql = "CREATE DATABASE STUDENTS;";
//            preparedStatement.executeUpdate(sql);
//            System.out.println("Database created successfully...");
//        }catch(SQLException se){
//            //Handle errors for JDBC
//            se.printStackTrace();
//        }catch(Exception e){
//            //Handle errors for Class.forName
//            e.printStackTrace();
//        } finally{
//            //finally block used to close resources
//            try{
//                if(stmt!=null)
//                    stmt.close();
//            }catch(SQLException se2){
//            }// nothing we can do
//            try{
//                if(conn!=null)
//                    conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        System.out.println("Goodbye!");
//    }

}
