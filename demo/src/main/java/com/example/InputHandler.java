package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InputHandler{
    private Game game;
    private GamePane gamePane;
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\images\\";
    private Stage stage;
    private Runnable showMenuCallBack;

    public InputHandler(Game game, GamePane gamePane, Stage stage, Runnable showMenuCallBack) {
        this.game = game;
        this.gamePane = gamePane;
        this.stage = stage;
        this.showMenuCallBack = showMenuCallBack;
    }

    public void keyPressed(KeyEvent event){
        KeyCode keyCode = event.getCode();
        GameStatus status = game.getGameStatus();
        ButtonAction currentAction = ButtonAction.NO_ACTION;
        
        if(keyCode == KeyCode.ESCAPE){
            if(status == GameStatus.MAIN_SCREEN){
                currentAction = ButtonAction.GO_TO_MAIN_MENU_SCREEN;
                game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
                showMenuCallBack.run();
            }
        }
    }
}
