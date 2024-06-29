package com.example;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameMenu {
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\";
    
    public void showMenu(StackPane gamePane) {
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        // Buttons
        Button loginButton = createImageButton("file:" + path + "Login button.png");
        Button signupButton = createImageButton("file:" + path + "Signup button.png");
        Button userguideButton = createImageButton("file:" + path + "Userguide button.png");
        Button settingsButton = createImageButton("file:" + path + "Settings.png");

        menuBox.getChildren().addAll(loginButton, signupButton, userguideButton, settingsButton);

        StackPane.setAlignment(menuBox, Pos.CENTER);
        gamePane.getChildren().add(menuBox);
    }

    private Button createImageButton(String imagePath) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-border-color: transparent;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"));

        button.setOnAction(event -> {
            // Handle button action here, if needed
        });
        return button;
    }
}
