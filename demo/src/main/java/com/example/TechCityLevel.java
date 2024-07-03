package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;

public class TechCityLevel extends BaseLevel {
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\";
    private double characterX = 0;
    private ImageView characterImageView;
    private StackPane levelPane;

    // Sample map data (replace this with actual parsed data from your JS file)
    int[][] mapData = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public TechCityLevel(Game game, Stage stage) {
        super(game, stage);
    }

    @Override
    public void createLevel() {
        // Set background image
        setLevelBackgroundImage(path + "images\\TechCity.png");

        // Create tile map
        GridPane gridPane = new GridPane();
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                try {
                    String tilePath = Paths.get(path + "images\\tile_" + mapData[i][j] + ".png").toUri().toString();
                    ImageView tile = new ImageView(new Image(tilePath));
                    tile.setFitWidth(50);
                    tile.setFitHeight(50);
                    gridPane.add(tile, j, i);
                } catch (Exception e) {
                    System.err.println("Error loading tile image: " + e.getMessage());
                }
            }
        }

        levelPane = new StackPane();
        levelPane.getChildren().add(gridPane);

        // Add character image
        Image characterImage = new Image("file:" + path + "images\\Quacky.png"); // Replace with your character image path
        characterImageView = new ImageView(characterImage);
        characterImageView.setFitWidth(50);
        characterImageView.setFitHeight(50);
        levelPane.getChildren().add(characterImageView);

        gamePane.getChildren().add(levelPane);

        // Add key event handling for character movement
        scene.setOnKeyPressed(this::handleKeyPress);

        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(16), event -> update()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
            characterX += 5;
        } else if (event.getCode() == KeyCode.LEFT) {
            characterX -= 5;
        }
        updateBackground();
        updateCharacterPosition();
    }

    private void updateBackground() {
        gamePane.updateBackgroundPosition(characterX);
    }

    private void updateCharacterPosition() {
        characterImageView.setTranslateX(characterX);
    }

    private void update() {
        // Update game logic, if any
    }
}
