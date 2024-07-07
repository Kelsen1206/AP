package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginPage {
    private final String imagePath = "/images/";
    private final String fontPath = "/fonts/";
    private final String userDataFilePath = "/user_data.bin";
    private GamePane gamePane;
    private Game game;
    private Stage stage;
    private Map<String, String> userData;

    public LoginPage(GamePane gamePane, Game game, Stage stage) {
        this.gamePane = gamePane;
        this.game = game;
        this.stage = stage;
        this.userData = loadUserData();
    }

    public void show() {
        Font font = Font.loadFont(getClass().getResourceAsStream(fontPath + "ARCADE_N.ttf"), 16);
        Font errorFont = Font.loadFont(getClass().getResourceAsStream(fontPath + "ARCADE_N.ttf"), 8);

        StackPane loginPane = new StackPane();
        loginPane.setPrefSize(1000, 650);  // Set preferred size for the StackPane

        // Background image
        ImageView loginPage = new ImageView(new Image(getClass().getResourceAsStream(imagePath + "login page.png")));
        loginPage.setFitWidth(1000);
        loginPage.setFitHeight(650);

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
        usernameInput.setPrefSize(225, 40);
        GridPane.setConstraints(usernameInput, 1, 0);

        // Password Label
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill : #FFFFFF");
        passwordLabel.setFont(font);
        GridPane.setConstraints(passwordLabel, 0, 1);

        // Password Input
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPrefSize(225, 40);
        GridPane.setConstraints(passwordInput, 1, 1);

        // Plain text field for showing password
        TextField passwordTextInput = new TextField();
        passwordTextInput.setPrefSize(225, 40);
        passwordTextInput.setManaged(false); // Initially hidden
        passwordTextInput.setVisible(false);
        GridPane.setConstraints(passwordTextInput, 1, 1);

        // Show Password Checkbox
        CheckBox showPasswordCheckBox = new CheckBox("Show Password");
        showPasswordCheckBox.setStyle("-fx-text-fill : #FFFFFF");
        showPasswordCheckBox.setFont(errorFont);
        GridPane.setConstraints(showPasswordCheckBox, 1, 2);

        // Toggle password visibility when checkbox is checked/unchecked
        showPasswordCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordTextInput.setText(passwordInput.getText());
                passwordTextInput.setManaged(true);
                passwordTextInput.setVisible(true);
                passwordInput.setManaged(false);
                passwordInput.setVisible(false);
            } else {
                passwordInput.setText(passwordTextInput.getText());
                passwordInput.setManaged(true);
                passwordInput.setVisible(true);
                passwordTextInput.setManaged(false);
                passwordTextInput.setVisible(false);
            }
        });

        // Error message label
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill : #FFFFFF;");
        errorMessage.setFont(errorFont);
        GridPane.setConstraints(errorMessage, 1, 3);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setPrefSize(60, 35);
        loginButton.setFont(errorFont);
        loginButton.setOnMouseEntered(event -> loginButton.setStyle("-fx-cursor: hand;"));
        loginButton.setOnMouseExited(event -> loginButton.setStyle("-fx-cursor: default;"));
        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = showPasswordCheckBox.isSelected() ? passwordTextInput.getText() : passwordInput.getText();
            if (validateLogin(username, password)) {
                IntroductionPage introductionPage = new IntroductionPage(game, stage);
                try {
                    introductionPage.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                errorMessage.setText("Invalid username or password.");
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setPrefSize(60, 35);
        backButton.setFont(errorFont);
        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-cursor: hand;"));
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-cursor: default;"));
        backButton.setOnAction(e -> {
            game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
            gamePane.setBackgroundImage(new Image(getClass().getResourceAsStream(imagePath + "background2.png")));
            GameMenu gameMenu = new GameMenu(game, stage);
            gameMenu.showMenu();
        }); // Handle going back to the previous scene
        
        buttonBox.getChildren().addAll(loginButton, backButton);
        
         // VBox to hold the grid and buttons
        VBox vbox = new VBox(20);  // 20 is the spacing between grid and buttons
        vbox.setAlignment(Pos.CENTER);  // Center the VBox content

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, passwordTextInput, showPasswordCheckBox, errorMessage);

        vbox.getChildren().addAll(grid, buttonBox);

        loginPane.getChildren().addAll(loginPage, vbox);

        Scene loginScene = new Scene(loginPane, 1000, 650);
        stage.setScene(loginScene);
        stage.show();
    }

    private boolean validateLogin(String username, String password) {
        if (userData.containsKey(username)) {
            String encryptedPassword = encryptPassword(password);
            return encryptedPassword.equals(userData.get(username));
        }
        return false;
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
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
        try (InputStream is = getClass().getResourceAsStream(userDataFilePath);
             ObjectInputStream ois = is != null ? new ObjectInputStream(is) : null) {
            if (ois != null) {
                data = (Map<String, String>) ois.readObject();
            } else {
                System.out.println("Could not find the user data file: " + userDataFilePath);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
