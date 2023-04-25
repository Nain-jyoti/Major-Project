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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UserInterface {
    GridPane loginPage;
    GridPane signupPage;
    HBox headerBar;
    HBox footerBar;
    Button signInButton;
    Button signUpButton;
    Label welcomeLabel;
    VBox body;
    Customer loggedInCustomer;
    ProductList productList=new ProductList();
    OrderList orderList=new OrderList();
    VBox productPage;
    Button placeOrderButton = new Button("Place Order");
    ObservableList<Product> itemsInCart= FXCollections.observableArrayList();
    ObservableList<Order> itemsOrdered = FXCollections.observableArrayList();
    public BorderPane createContent()
    {
        BorderPane root=new BorderPane();
        root.setPrefSize(800,600);
        root.setTop(headerBar);
        //root.getChildren().add(loginPage);//method to add nodes as children to Pane
        //root.setCenter(loginPage);
        body=new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);
        root.setBottom(footerBar);
        return  root;
    }
    //constructor is created to call the createLoginPage() function
    public UserInterface()
    {
        createLoginPage();
        createHeaderBar();
        createSignupPage();
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
                    signUpButton.setVisible(false);
                }
                else{
                    messgaeLabel.setText("Login fails !! please enter correct details");
                }
                footerBar.setVisible(true);
            }
        });
    }
    private void createSignupPage(){
        Text nameText = new Text("Full Name");
        Text mobileText = new Text("Mobile No.");
        Text emailText = new Text("Email");
        Text passwordText = new Text("Password");

        TextField name = new TextField();
        name.setPromptText("Type Your Name here!");
        TextField mobile = new TextField();
        mobile.setPromptText("Type Your Mobile Number here!");
        TextField email = new TextField();
        email.setPromptText("Type Your Email-Id Here!");
        PasswordField password = new PasswordField();
        password.setPromptText("Type Your Password Here!");


        Label messageLabel = new Label();

        Button signupButton = new Button("Signup");

        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (email.getText().isEmpty() || mobile.getText().isEmpty() || name.getText().isEmpty() || password.getText().isEmpty()){
                        messageLabel.setText("All fields are required");
                        return;
                    }

                    String uname = name.getText();
                    String uemail = email.getText();

                    String umobile = mobile.getText();
                    String hashedPassword=password.getText();

                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    //crete new customer and add to database
                    NonCustomer nonCustomer = new NonCustomer( uname,uemail,umobile,hashedPassword);
                    //adding to db
                    boolean res =  Signup.customerLogin(nonCustomer);
                    if (res){
                        showDialog("Registered Successfully");
                        body.getChildren().clear();
                        body.getChildren().add(productPage);
                    }else {
                        showDialog("Registration failed");
                    }
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        signupPage = new GridPane();
        signupPage.setVgap(10);

        signupPage.setStyle("-fx-border-color: black;-fx-border-width: 2pt;-fx-padding: 10pt;-fx-max-width: 300pt");
        signupPage.setAlignment(Pos.CENTER);
        signupPage.setHgap(10);
        signupPage.setVgap(10);
        signupPage.add(nameText,0,0);
        signupPage.add(name,1,0);
        signupPage.add(emailText,0,1);
        signupPage.add(email,1,1);
        signupPage.add(mobileText,0,2);
        signupPage.add(mobile,1,2);
        signupPage.add(passwordText,0,3);
        signupPage.add(password,1,3);
        signupPage.add(messageLabel,0,4);
        signupPage.add(signupButton,1,4);

    }
    private void createHeaderBar()
    {
        Button homeButton=new Button();
        Image image=new Image("C:\\Users\\DEL\\IdeaProjects\\Ecommerce\\src\\ecomm.png");
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

        TextField searchBar=new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setPrefWidth(250);

        Button seachButton=new Button("Search");

        signInButton =new Button("Sign In");
        signUpButton = new Button("Sign Up");

        welcomeLabel=new Label();

        Button cartButton=new Button("Cart");

        Button orderButton=new Button("Order");

        headerBar =new HBox();//for gap or spacing
        headerBar.setPadding(new Insets(10));
        headerBar.setStyle("-fx-background-color: lightblue;");
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,searchBar,seachButton,cartButton,orderButton,signInButton,signUpButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();//remove everything
                body.getChildren().add(loginPage);//put login page
                headerBar.getChildren().remove(signInButton);
               // signUpButton.setVisible(false);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage=productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                placeOrderButton.setDisable(false);
                signUpButton.setVisible(false);
                footerBar.setVisible(false);//all cases need to handled for this
            }
        });

        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox orderPage=orderList.getAllOrders(itemsOrdered);
                orderPage.setAlignment(Pos.CENTER);
                orderPage.setSpacing(10);
                //orderPage.getChildren().add(placeOrderButton);
                body.getChildren().add(orderPage);//(orderList.getAllOrders());


                footerBar.setVisible(false);//all cases need to handled for this
                placeOrderButton.setDisable(true);
            }
        });

        placeOrderButton.setAlignment(Pos.CENTER);
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //need list of products and a customer
                if (itemsInCart==null)
                {
                    //please select a product first to place an order
                    String message="Please add some products in the cart to place order!!!";
                    showWarningDialog(message);
                    return;
                }
                if (loggedInCustomer==null)
                {
                    String message="Please Login first to place an order!!!";
                    showWarningDialog(message);
                    return;
                }
                int count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                if (count!=0)
                {
                    showDialog("Order for "+count+" products placed Successfully!!");
                }
                else {
                    showDialog("Order Failed!!");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if (loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton)==-1)
                {
                    headerBar.getChildren().add(signInButton);
                }

            }
        });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(signupPage);
                footerBar.setVisible(false);
                headerBar.getChildren().remove(signInButton);
                headerBar.getChildren().remove(signUpButton);

            }
        });


    }
    private void createFooterBar()
    {
        Button buyNowButton=new Button("Buy Now");
        Button addToCartButton=new Button("Add to Cart");

        footerBar =new HBox();//for gap or spacing
        footerBar.setPadding(new Insets(10));
        footerBar.setStyle("-fx-background-color: lightblue;");
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

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
                boolean status= Order.placeOrder(loggedInCustomer, product);
                if(status==true){
                    showDialog("Order placed successfully !!");
                }
                else{
                    showDialog("Order Failed !!");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product =productList.getSelectedProduct();
                if (product==null)
                {
                    //please select a product first to place an order
                    String message="Please select a product first to add it to cart!!!";
                    showWarningDialog(message);
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected item has been added to the cart successfully.");
            }
        });
    }
    private void showWarningDialog(String message)
    {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Warning");
        alert.showAndWait();
    }
    private void showDialog(String message)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}