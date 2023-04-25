package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class OrderList {

    private TableView<Order> orderTable;
    public VBox createTable(ObservableList<Order> data)
    {
        //Columns
        TableColumn id= new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

//        TableColumn name=new TableColumn("NAME");
//        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn quantity=new TableColumn("QUANTITY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));




        orderTable=new TableView<>();
        orderTable.getColumns().addAll(id,quantity);
        orderTable.setItems(data);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //the extra column will be removed with the help of Resizing policy

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(orderTable);
        return vBox;
    }
    public VBox getAllOrders(ObservableList<Order> itemsOrdered)
    {
        ObservableList<Order> data=Order.getAllOrders();
        return createTable(data);
    }
    public Order getSelectedOrder()
    {
        return orderTable.getSelectionModel().getSelectedItem();
    }
    public VBox getOrderInCart(ObservableList<Order> data)
    {
        return createTable(data);
    }

}
