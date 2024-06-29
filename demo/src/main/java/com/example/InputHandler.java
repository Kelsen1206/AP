package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler{
    private Game game;
    private GamePane gamePane;
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\";
    
    InputHandler(Game game, GamePane gamePane){
        this.game = game;
        this.gamePane = gamePane;
    }

    public void keyPressed(KeyEvent event){
        KeyCode keyCode = event.getCode();
        GameStatus status = game.getGameStatus();
        ButtonAction currentAction = ButtonAction.NO_ACTION;
        
        if(keyCode == KeyCode.ESCAPE){
            if(status == GameStatus.START_SCREEN){
                currentAction = ButtonAction.GO_TO_MAIN_MENU_SCREEN;
                game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
                try {
                    Image background2 = new Image(new FileInputStream(path + "background2.png"));
                    gamePane.setBackgroundImage(background2);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                GameMenu gameMenu = new GameMenu();
                gameMenu.showMenu(gamePane);
            }
        }
    }
}
