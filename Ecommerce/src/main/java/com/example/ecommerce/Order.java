package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrders(Customer customer, Product product) {
        String groupOrderId = "SELECT max(group_order_id) +1 id FROM orders";
        Dbconnection dbconnection = new Dbconnection();
        try {
            ResultSet rs = dbconnection.getQueryTable(groupOrderId);
            if (rs.next()) {
                String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES (" + rs.getInt("id") + "," +
                        " " + customer.getId() + "," + product.getId() + ")";
                return dbconnection.updateDatabase(placeOrder) != 0;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int placeMultipleOrders(Customer customer, ObservableList<Product> productList) {
        String groupOrderId = "SELECT max(group_order_id) +1 id FROM orders";
        Dbconnection dbconnection = new Dbconnection();
        try {
            ResultSet rs = dbconnection.getQueryTable(groupOrderId);
            int count = 0;
            if (rs.next()) {
                for (Product product : productList) {
                    String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES (" + rs.getInt("id") + "," +
                            " " + customer.getId() + "," + product.getId() + ")";
                    count += dbconnection.updateDatabase(placeOrder);
                }
                return count;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
//for order button
}

