package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserGuidePage {
    private final String imagePath = "/images/";
    private final String fontPath = "/fonts/";
    private GamePane gamePane;
    private Game game;
    private Stage stage;

    public UserGuidePage(GamePane gamePane, Game game, Stage stage) {
        this.gamePane = gamePane;
        this.game = game;
        this.stage = stage;
    }

    public void show(){
        Font errorFont = Font.loadFont(getClass().getResourceAsStream(fontPath + "ARCADE_N.ttf"), 8);

        StackPane userGuidePane = new StackPane();
        userGuidePane.setPrefSize(1000, 650);  // Set preferred size for the StackPane

        // Background image
        ImageView userGuidePage = new ImageView(new Image(getClass().getResourceAsStream(imagePath + "user guide page.png")));
        userGuidePage.setFitWidth(1000);
        userGuidePage.setFitHeight(650);

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

        // VBox to position the button at the bottom center
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setPadding(new Insets(70));
        vbox.setPrefHeight(650);
        vbox.getChildren().add(backButton);

        userGuidePane.getChildren().addAll(userGuidePage, vbox);

        Scene userGuideScene = new Scene(userGuidePane, 1000, 650);
        stage.setScene(userGuideScene);
        stage.show();

    }
}
