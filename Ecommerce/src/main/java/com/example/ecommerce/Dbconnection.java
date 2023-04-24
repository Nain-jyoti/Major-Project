package com.example.ecommerce;
import java.sql.*;

public class Dbconnection {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String username = "root";
    private static final String password = "Nain123";

    private Statement getStatement(){
        try{
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            return connection.createStatement();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){
        try {
            Statement statement = getStatement();
            return  statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateDatabase(String query){
        try {
            Statement statement = getStatement();
            return  statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        Dbconnection conn = new Dbconnection();
        ResultSet rs = conn.getQueryTable("SELECT * FROM customer");
        if(rs!=null){
            System.out.println("Connection Successful");
        }
        else System.out.println("Connection Failed !");
    }
}
