package com.example;

import javafx.stage.Stage;

import java.io.IOException;

public class LevelManager {
    private Game game;
    private Stage stage;
    private ImageLoader imageLoader;
    private String[] levels = {"MysticGrove", "CrystalHallows", "SnowyPeak", "AquaFjords", "TechCity", "Taylors"};
    private int currentLevelIndex = 0;

    public LevelManager(Game game, Stage stage, ImageLoader imageLoader) {
        this.game = game;
        this.stage = stage;
        this.imageLoader = imageLoader;
    }

    public void startNextLevel() throws IOException {
        if (currentLevelIndex < levels.length) {
            String levelName = levels[currentLevelIndex];
            switch (levelName) {
                case "MysticGrove":
                    MysticGrove mysticGrove = new MysticGrove(game, stage, imageLoader);
                    mysticGrove.createLevel();
                    mysticGrove.show();
                    break;
                case "CrystalHallows":
                    CrystalHallows crystalHallows = new CrystalHallows(game, stage, imageLoader);
                    crystalHallows.createLevel();
                    crystalHallows.show();
                    break;
                case "SnowyPeak":
                    SnowyPeak snowyPeak = new SnowyPeak(game, stage, imageLoader);
                    snowyPeak.createLevel();
                    snowyPeak.show();
                    break;
                case "AquaFjords":
                    AquaFjords aquaFjords = new AquaFjords(game, stage, imageLoader);
                    aquaFjords.createLevel();
                    aquaFjords.show();
                    break;
                case "TechCity":
                    TechCity techCity = new TechCity(game, stage, imageLoader);
                    techCity.createLevel();
                    techCity.show();
                    break;
                case "Taylors":
                    Taylors taylors = new Taylors(game, stage, imageLoader);
                    taylors.createLevel();
                    taylors.show();
                    break;
            }
            currentLevelIndex++;
        } else {
            // Game completed
            game.setGameStatus(GameStatus.GAME_COMPLETED);
        }
    }

    public void resumeCurrentLevel() {
        String currentLevel = levels[currentLevelIndex];
    }

    public void restartCurrentLevel() {
        String currentLevel = levels[currentLevelIndex];
    }
}