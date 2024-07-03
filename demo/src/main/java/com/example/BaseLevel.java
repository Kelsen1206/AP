package com.example;

import java.nio.file.Paths;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public abstract class BaseLevel {
    protected Game game;
    protected Stage stage;
    protected GamePane gamePane;
    protected Scene scene;

    public BaseLevel(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.gamePane = new GamePane(null);  // Initialized with null
        this.scene = new Scene(gamePane, 1000, 650);
    }

    protected void setBackgroundImage(String imagePath) {
        try {
            String formattedPath = Paths.get(imagePath).toUri().toString();
            Image backgroundImage = new Image(formattedPath);
            gamePane.setBackgroundImage(backgroundImage);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    protected void setLevelBackgroundImage(String imagePath) {
        try {
            String formattedPath = Paths.get(imagePath).toUri().toString();
            Image backgroundImage = new Image(formattedPath);
            gamePane.setLevelBackgroundImage(backgroundImage);
        } catch (Exception e) {
            System.err.println("Error loading level background image: " + e.getMessage());
        }
    }

    public abstract void createLevel();

    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}
