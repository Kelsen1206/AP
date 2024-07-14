package com.example;

import javafx.stage.Stage;

public abstract class Level {
    protected Game game;
    protected Stage stage;
    protected ImageLoader imageLoader;

    public Level(Game game, Stage stage, ImageLoader imageLoader) {
        this.game = game;
        this.stage = stage;
        this.imageLoader = imageLoader;
    }

    public abstract void createLevel();
    public abstract void show();
    public abstract void restartGame();

    public void resetLevelState() {
        game.setGameStatus(GameStatus.GAME_RUNNING);
        // Reset any other level-specific states here
    }
}
