package com.example;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InputHandler {
    private Game game;
    private GamePane gamePane;
    private Stage stage;
    private Runnable showMenuCallBack;
    private Quacky quacky;
    private Runnable restartGameCallBack;

    public InputHandler(Game game, GamePane gamePane, Stage stage, Runnable showMenuCallBack, Quacky quacky, Runnable restartGameCallBack) {
        this.game = game;
        this.gamePane = gamePane;
        this.stage = stage;
        this.showMenuCallBack = showMenuCallBack;
        this.quacky = quacky;
        this.restartGameCallBack = restartGameCallBack;
    }

    public void keyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        GameStatus status = game.getGameStatus();

        if (status == GameStatus.MAIN_SCREEN) {
            game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
            showMenuCallBack.run();
        } else if (status == GameStatus.GAME_RUNNING) {
            // Handle Quacky's movement
            switch (keyCode) {
                case LEFT:
                    quacky.moveLeft();
                    break;
                case RIGHT:
                    quacky.moveRight();
                    break;
                case UP:
                    quacky.jump();
                    break;
                // Add more cases for other controls as needed
            }
        } else if (status == GameStatus.GAME_OVER) {
            if (keyCode == KeyCode.SPACE) {
                restartGameCallBack.run();
                gamePane.setGameStatus(GameStatus.GAME_RUNNING);
            }
        }
    }

    public void keyReleased(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        System.out.println("Key released: " + keyCode);
        GameStatus status = game.getGameStatus();

        if (status == GameStatus.GAME_RUNNING) {
            switch (keyCode) {
                case LEFT:
                case RIGHT:
                    quacky.stopMoving();
                    break;
                // Add more cases for other controls as needed
            }
        }
    }
}
