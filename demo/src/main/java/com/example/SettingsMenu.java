package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu {
    private Game game;
    private Stage stage;
    private LevelManager levelManager;

    public SettingsMenu(Game game, LevelManager levelManager, Stage stage) {
        this.game = game;
        this.levelManager = levelManager;
        this.stage = stage;
    }

    public void showMenu() {
        GamePane gamePane = new GamePane(new Image(getClass().getResource("/images/settings page.png").toString()));

        VBox menuBox = new VBox(5);
        menuBox.setAlignment(Pos.CENTER);

        // Buttons
        Button resumeButton = createImageButton("/images/Resume button.png");
        Button restartButton = createImageButton("/images/Restart button.png");
        Button optionsButton = createImageButton("/images/Options button.png");
        Button quitButton = createImageButton("/images/Quit button.png");

        resumeButton.setOnAction(event -> resumeGame());
        restartButton.setOnAction(event -> restartGame());
        optionsButton.setOnAction(event -> showOptionsPage());
        quitButton.setOnAction(event -> quitToMainMenu());

        menuBox.getChildren().addAll(resumeButton, restartButton, optionsButton, quitButton);

        StackPane.setAlignment(menuBox, Pos.CENTER);
        gamePane.getChildren().add(menuBox);

        Scene menuScene = new Scene(gamePane, 1000, 650);
        stage.setScene(menuScene);
        stage.show();
    }

    private Button createImageButton(String imagePath) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResource(imagePath).toString()));
        imageView.setFitHeight(100);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-border-color: transparent; -fx-cursor: hand;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: default;"));

        return button;
    }

    private void resumeGame() {
        game.setGameStatus(GameStatus.GAME_RUNNING);
        levelManager.resumeCurrentLevel();
    }

    private void restartGame() {
        game.setGameStatus(GameStatus.GAME_RUNNING);
        levelManager.restartCurrentLevel();
    }

    private void showOptionsPage() {
        // Implement options page logic here
    }

    private void quitToMainMenu() {
        game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
        game.showGameMenu();
    }
}