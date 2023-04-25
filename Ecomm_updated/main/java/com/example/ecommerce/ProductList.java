package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.w3c.dom.CDATASection;

public class ProductList {
    private TableView<Product> productTable;

    public VBox createTable(ObservableList<Product> data){
        //columns
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE  ");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


//        //Data
//
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.add(new Product(2, "HP Laptop", 10000));
//        data.add(new Product(3, "HP Pro Laptop", 109000));

        productTable = new TableView<>();
        productTable.getColumns().addAll(id, name, price);
        productTable.setItems(data);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //extra columns will be removed from table

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10)); // to set padding around table
        vBox.getChildren().addAll(productTable);
        return vBox;
    }

//     public VBox getDummyTable(){
//         //Data
//
//         ObservableList<Product> data = FXCollections.observableArrayList();
//         data.add(new Product(2, "HP Laptop", 10000));
//         data.add(new Product(3, "HP Pro Laptop", 109000));
//         return createTable(data);
//     }

     public VBox getAllProducts(){
         ObservableList<Product> data = Product.getAllproducts();
         return createTable(data);
     }

     public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
     }
     public  VBox getProductsInCart(ObservableList<Product> data){
        return  createTable(data);
     }

}
