package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty quantity;


    public Order(int id, int quantity) {
        this.id = new SimpleIntegerProperty(id);
       // this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        //this.orderDate = orderDate;
        //this.orderStatus = new SimpleStringProperty(orderStatus);
    }

    public static ObservableList<Order> getAllOrders()
    {

        String selectAllProducts="SELECT orders.id, orders.quantity, orders.order_date, orders.order_status FROM orders";// join product on orders.product_id = product.id;";//WHERE customer_id="+customer.getId()+"";
        //System.out.println(selectAllProducts);
        return fetchOrderData(selectAllProducts);
    }

    public static ObservableList<Order> fetchOrderData(String query)
    {
        ObservableList<Order> data= FXCollections.observableArrayList();
        Dbconnection dbConnection=new Dbconnection();
        try {
            ResultSet rs=dbConnection.getQueryTable(query);
            while (rs.next())
            {
                Order product=new Order(rs.getInt("id"), rs.getInt("quantity"));
                data.add(product);
            }
            return data;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

//    public String getName() {
//        return name.get();
//    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    static boolean placeOrder(Customer customer, Product product){
        String groupOrderId = "select max(group_order_id)+1  id from orders";

        try
        {
            Dbconnection dbConnection = new Dbconnection();
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            if (rs.next()){
                String placeOrder = "insert into orders(group_order_id,customer_id,product_id) " +
                        "values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                //System.out.println(placeOrder);
                return dbConnection.updateDatabase(placeOrder)!= 0;


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    static int placeMultipleOrder(Customer customer, ObservableList<Product>  products){
        String groupOrderId = "select max(group_order_id)+1  id from orders";
        try
        {
            Dbconnection dbConnection = new Dbconnection();
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            int count = 0;
            if (rs.next()){
                for (Product product : products){
                    String placeOrder = "insert into orders(group_order_id,customer_id,product_id) " +
                            "values("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count+=dbConnection.updateDatabase(placeOrder);
                    //System.out.println(placeOrder);
                }

                return count;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}