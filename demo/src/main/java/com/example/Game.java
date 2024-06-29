package com.example;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Game extends Application {
    private GameStatus gameStatus;
    private ImageLoader imageLoader;
    private GamePane gamePane;
    private GameMenu gameMenu;
    private InputHandler inputHandler;
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\";
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        imageLoader = new ImageLoader();
        gameStatus = GameStatus.START_SCREEN;
        gameMenu = new GameMenu();

        try {
            Image background1 = imageLoader.loadImage(path + "background1.png");
            gamePane = new GamePane(background1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        inputHandler = new InputHandler(this, gamePane);

        Scene scene = new Scene(gamePane, 1000, 650);
        
        scene.setOnKeyPressed(inputHandler::keyPressed);

        primaryStage.setTitle("The Lost Element");
        primaryStage.setScene(scene);
        primaryStage.show();
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
}
