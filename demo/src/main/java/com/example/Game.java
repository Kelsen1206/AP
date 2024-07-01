package com.example;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Game extends Application {
    private GameStatus gameStatus;
    private ImageLoader imageLoader;
    private Stage primaryStage;
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\images\\";
    
    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        imageLoader = new ImageLoader();
        gameStatus = GameStatus.MAIN_SCREEN;

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
    }

    private void showMainScreen(){
        try {
            Image background1 = imageLoader.loadImage(path + "background1.png");
            GamePane gamePane = new GamePane(background1);
            Scene gameScene = new Scene(gamePane, 1000, 650);

            Runnable showMenuCallback = this::showGameMenu;
            InputHandler inputHandler = new InputHandler(this, gamePane, primaryStage, showMenuCallback);

            gameScene.setOnKeyPressed(inputHandler::keyPressed);

            primaryStage.setTitle("The Lost Element");
            primaryStage.setScene(gameScene);
            primaryStage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showGameMenu() {
        GameMenu gameMenu = new GameMenu(this, primaryStage);
        gameMenu.showMenu();
    }
}
