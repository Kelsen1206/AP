package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SignUpPage {
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\";
    private final String userDataFile = path + "user_data.bin";
    private GamePane gamePane;
    private Game game;
    private Stage stage;
    private Map<String, String> userData;

    public SignUpPage(GamePane gamePane, Game game, Stage stage) {
        this.gamePane = gamePane;
        this.game = game;
        this.stage = stage;
        this.userData = loadUserData();
    }

    public void show() {
        Font font = Font.loadFont("file:" + path + "fonts\\ARCADE_N.ttf", 16);
        Font errorFont = Font.loadFont("file:" + path + "fonts\\ARCADE_N.ttf", 8);
        StackPane signUpPane = new StackPane();
        signUpPane.setPrefSize(1000, 650);  // Set preferred size for the StackPane

        // Background image
        ImageView signUpPage = new ImageView(new Image("file:" + path + "images\\sign up page.png"));
        signUpPage.setFitWidth(1000);
        signUpPage.setFitHeight(650);

        // GridPane for login components
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(30));
        grid.setVgap(20);
        grid.setHgap(20);

        // Username Label
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill : #FFFFFF");
        usernameLabel.setFont(font);
        GridPane.setConstraints(usernameLabel, 0, 0);

        // Username Input
        TextField usernameInput = new TextField();
        usernameInput.setPrefSize(280, 40);
        GridPane.setConstraints(usernameInput, 1, 0);

        // Password Label
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill : #FFFFFF");
        passwordLabel.setFont(font);
        GridPane.setConstraints(passwordLabel, 0, 1);

        // Password Input
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPrefSize(280, 40);
        GridPane.setConstraints(passwordInput, 1, 1);

        // Confirm Password Label
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setStyle("-fx-text-fill : #FFFFFF");
        confirmPasswordLabel.setFont(font);
        GridPane.setConstraints(confirmPasswordLabel, 0, 2);

        // Confirm Password Input
        PasswordField confirmPasswordInput = new PasswordField();
        confirmPasswordInput.setPrefSize(280, 40);
        GridPane.setConstraints(confirmPasswordInput, 1, 2);

        // Error message label
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill : #FFFFFF;");
        errorMessage.setFont(errorFont);
        GridPane.setConstraints(errorMessage, 1, 3);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Sign Up Button
        Button signUpButton = new Button("Sign Up");
        signUpButton.setPrefSize(80, 35);
        signUpButton.setFont(errorFont);
        signUpButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String confirmPassword = confirmPasswordInput.getText();
            
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                errorMessage.setText("All fields must be filled.");
            } else if (!password.equals(confirmPassword)) {
                errorMessage.setText("Password do not match.");
            } else if(userData.containsKey(username)) {
                errorMessage.setText("Username already exists.");
            } else{
                errorMessage.setText("Sign up successful.");
                userData.put(username, encryptPassword(password));
                saveUserData();
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setPrefSize(60, 35);
        backButton.setFont(errorFont);
        backButton.setOnAction(e -> {
            game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
            gamePane.setBackgroundImage(new Image("file:" + path + "images\\background2.png"));
            GameMenu gameMenu = new GameMenu(game, stage);
            gameMenu.showMenu();
        }); // Handle going back to the previous scene
        
        buttonBox.getChildren().addAll(signUpButton, backButton);
        
         // VBox to hold the grid and buttons
        VBox vbox = new VBox(20);  // 20 is the spacing between grid and buttons
        vbox.setAlignment(Pos.CENTER);  // Center the VBox content

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, confirmPasswordLabel, confirmPasswordInput, errorMessage);

        vbox.getChildren().addAll(grid, buttonBox);

        signUpPane.getChildren().addAll(signUpPage, vbox);

        Scene signUpScene = new Scene(signUpPane, 1000, 650);
        stage.setScene(signUpScene);
        stage.show();
    }

    private String encryptPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> loadUserData() {
        Map<String, String> data = new HashMap<>();
        File file = new File(userDataFile);
        System.out.println("Loading user data from: " + userDataFile); // Debug statement
        if (file.exists()) {
            System.out.println("File exists. Reading data..."); // Debug statement
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                data = (Map<String, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exist. Creating new file..."); // Debug statement
            try {
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    System.out.println("Creating parent directories..."); // Debug statement
                    parentDir.mkdirs();
                }
                if (file.createNewFile()) {
                    System.out.println("user_data.bin file created successfully.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private void saveUserData() {
        System.out.println("Saving user data to: " + userDataFile); // Debug statement
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userDataFile))) {
            oos.writeObject(userData);
            System.out.println("User data saved successfully."); // Debug statement
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

