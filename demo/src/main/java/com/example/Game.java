package com.example;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    private GameStatus gameStatus;
    private ImageLoader imageLoader;
    private Stage primaryStage;
    private final String imagePath = "/images/";
    private final String fontPath = "/fonts/";
    private Quacky quacky;
    private GamePane gamePane;
    private SoundManager soundManager;
    private SettingsMenu settingsMenu;
    private LevelManager levelManager;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        this.primaryStage = primaryStage;
        imageLoader = new ImageLoader();
        gameStatus = GameStatus.MAIN_SCREEN;

        quacky = new Quacky(50,500, 50, 950, soundManager);

        soundManager = SoundManager.getInstance();
        soundManager.loadSounds();

        soundManager.playBackgroundMusic();

        levelManager = new LevelManager(this, primaryStage, imageLoader);
        settingsMenu = new SettingsMenu(this, levelManager, primaryStage);

        showMainScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public GameStatus getGameStatus(){
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
        if (gameStatus == GameStatus.LEVEL_COMPLETED) {
            try {
                levelManager.startNextLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void showMainScreen(){
        try {
            Image background1 = imageLoader.loadImage(imagePath + "background1.png");
            gamePane = new GamePane(background1);

            Font messageFont = Font.loadFont(getClass().getResourceAsStream(fontPath + "ARCADE_N.ttf"), 16);
            Label messageLabel = new Label("Press any key to enter");
            messageLabel.setFont(messageFont);
            messageLabel.setStyle("-fx-text-fill: #FFFFFF;");

            // VBox to hold the message label
            VBox messageBox = new VBox(messageLabel);
            messageBox.setAlignment(Pos.BOTTOM_CENTER);
            messageBox.setPadding(new Insets(30)); // Add some padding to move the label up

            // StackPane to layer the game pane and message label
            StackPane root = new StackPane();
            root.getChildren().addAll(gamePane, messageBox);
            StackPane.setAlignment(messageBox, Pos.BOTTOM_CENTER);

            Scene gameScene = getScene(root, messageLabel, gamePane);

            primaryStage.setTitle("The Lost Element");
            primaryStage.setScene(gameScene);
            primaryStage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Scene getScene(StackPane root, Label messageLabel, GamePane gamePane) {
        Scene gameScene = new Scene(root, 1000, 650);

        // Fade transition for the message label
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), messageLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

        Runnable showMenuCallback = this::showGameMenu;
        Runnable restartGameCallback = this::restartGame;
        InputHandler inputHandler = new InputHandler(this, gamePane, primaryStage, showMenuCallback, quacky, restartGameCallback);

        gameScene.setOnKeyPressed(inputHandler::keyPressed);
        gameScene.setOnKeyReleased(inputHandler::keyReleased);
        return gameScene;
    }

    void showGameMenu() {
        soundManager.stopBackgroundMusic();

        GameMenu gameMenu = new GameMenu(this, primaryStage);
        gameMenu.showMenu();
    }

    private void restartGame() {
        gamePane.setGameStatus(GameStatus.GAME_RUNNING);
    }


    public void showSettingsMenu() {
        settingsMenu.showMenu();
    }

    public void initializeGame() throws IOException {
        levelManager.startNextLevel();
        settingsMenu = new SettingsMenu(this, levelManager, primaryStage);
    }
}