package com.example;

import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LevelManager {
    private Game game;
    private Stage stage;
    private ImageLoader imageLoader;
    private String[] levels = {"MysticGrove", "CrystalHallows", "SnowyPeak", "AquaFjords", "TechCity", "Taylors"};
    private int currentLevelIndex = 0;
    private Level currentLevel;

    public LevelManager(Game game, ImageLoader imageLoader) {
        this.game = game;
        this.imageLoader = imageLoader;
    }

    public void startNextLevel() throws IOException {
            if (currentLevelIndex < levels.length) {
                String levelName = levels[currentLevelIndex];
                currentLevel = createLevel(levelName);
                game.setGameStatus(GameStatus.GAME_RUNNING);
                currentLevel.createLevel();
                currentLevel.show();
                currentLevelIndex++;
            } else {
                // Game completed
                game.setGameStatus(GameStatus.GAME_COMPLETED);
            }
    }

    private Level createLevel(String levelName) throws FileNotFoundException {
        Stage currentStage = game.getCurrentStage();

        switch (levelName) {
            case "MysticGrove":
                return new MysticGrove(game, currentStage, imageLoader);
            case "CrystalHallows":
                return new CrystalHallows(game, currentStage, imageLoader);
            case "SnowyPeak":
                return new SnowyPeak(game, currentStage, imageLoader);
            case "AquaFjords":
                return new AquaFjords(game, currentStage, imageLoader);
            case "TechCity":
                return new TechCity(game, currentStage, imageLoader);
            case "Taylors":
                return new Taylors(game, currentStage, imageLoader);
            default:
                throw new IllegalArgumentException("Unknown level: " + levelName);
        }
    }

    public void resumeCurrentLevel() {
        if (currentLevel != null) {
            currentLevel.show();
        }
    }

    public void restartCurrentLevel() {
        if (currentLevel != null) {
            currentLevel.restartGame();
            currentLevel.show();
        }
    }

    public void setCurrentLevelIndex(int index) {
        currentLevelIndex = index;
    }
}