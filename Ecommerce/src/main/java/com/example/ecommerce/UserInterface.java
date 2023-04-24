package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.SQLOutput;

public class UserInterface {

    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    Button signInButton;
    Label welcomeLabel;
    VBox body;
    Customer loggedInCustomer;

    ProductList productList = new ProductList();
    VBox productPage;
    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 400);
        //root.getChildren().add(loginPage); // method to add it on main screen
        root.setTop(headerBar);
        //root.setCenter(loginPage);

        body = new VBox(); //making body for center page -->login and product page
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);

        productPage = productList.getAllProducts();
        body.getChildren().add(productPage); //adding page in the body

        root.setBottom(footerBar);


        return root;
    }

    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void createLoginPage(){
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField();
        userName.setPromptText("Type Your Username here");
        PasswordField password = new PasswordField();
        password.setPromptText("Type Your password here");
        Label messgaeLabel = new Label("Hi");

        Button loginButton = new Button(" Login");


        loginPage = new GridPane();
        // loginPage.setStyle("-fx-background-color:grey");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(messgaeLabel, 0, 2);
        loginPage.add(loginButton, 1, 2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                //messgaeLabel.setText(name);
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name, pass);
                if(loggedInCustomer != null){
                    messgaeLabel.setText("Welcome " + loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome-" + loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
                else{
                    messgaeLabel.setText("Login fails !! please enter correct details");
                }
            }
        });

    }


    private void createHeaderBar(){

        Button hmeButton = new Button();
        Image image = new Image("C:\\Users\\DEL\\IdeaProjects\\Ecommerce\\src\\ecomm.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        hmeButton.setGraphic(imageView);


        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setPrefWidth(150);

        Button searchButton = new Button("Search");

        signInButton = new Button("Sign In");
        welcomeLabel = new Label();

        Button cartButton = new Button("Cart");

        Button orderButton = new Button("Orders");

        headerBar = new HBox( ); // 18-min
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(hmeButton, searchButton, signInButton, cartButton, orderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); //remove everything
                body.getChildren().add(loginPage); // login page calls
                headerBar.getChildren().remove(signInButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER); // set place order button in cart at center
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false); //all cases need to be handled, i.e make visible where ever required
            }
        });

         placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 //nned list of product and customer
                 Product product= productList.getSelectedProduct();
                 if(itemsInCart==null){
                     //select product first
                     showDialog("Please select product in cart !");
                     return;
                 }
                 if(loggedInCustomer==null){
                     showDialog("Please Login to place order");
                     return;
                 }
                 int count = Order.placeMultipleOrders(loggedInCustomer, itemsInCart);
                 if(count != 0){
                     showDialog("Order placed successfully for "+count+" products !!");
                 }
                 else{
                     showDialog("Order Failed !!");
                 }
             }
         });

         hmeButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(productPage);
                 footerBar.setVisible(true);
                 if(loggedInCustomer == null && headerBar.getChildren().indexOf(signInButton)==-1){
                         headerBar.getChildren().add(signInButton);
                 }

             }
         });



    }

    private void createFooterBar(){


        Button buyNowButton = new Button("Buy Now");
        Button addToCart = new Button("Add to Cart");

        footerBar = new HBox( ); // 18-min
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCart);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product= productList.getSelectedProduct();
                if(product==null){
                    //select product first
                    showDialog("Please select product to place order !");
                    return;
                }
                if(loggedInCustomer==null){
                    showDialog("Please Login to place order");
                    return;
                }
                boolean status= Order.placeOrders(loggedInCustomer, product);
                if(status==true){
                    showDialog("Order placed successfully !!");
                }
                else{
                    showDialog("Order Failed !!");
                }
            }
        });

        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product= productList.getSelectedProduct();
                if(product==null){
                    //select product first
                    showDialog("Please select product to add to cart !");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Successfully added to cart !");
            }
        });

    }
    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}


