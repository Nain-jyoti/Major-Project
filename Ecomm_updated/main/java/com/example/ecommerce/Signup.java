package com.example.ecommerce;

public class Signup {

    public static boolean customerLogin(NonCustomer customer )  {
        Dbconnection dbConnection = new Dbconnection();
        String name= customer.getName();
        String password= customer.getPassword();
        String email= customer.getEmail();
        String mobile= customer.getMobile();

        String query;

        query = "INSERT INTO customer(name,email,mobile,password) VALUES('"+name+"','"+email+"','"+mobile+"','"+password+"')";
        int rs = dbConnection.updateDatabase(query);
        return rs != 0;
    }


}
