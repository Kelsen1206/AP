package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TechCityLevel extends BaseLevel {
    private final String imagePath = "/images/";
    private double characterX = 0;
    private double characterY = 0;
    private ImageView characterImageView;
    private StackPane levelPane;
    private ImageLoader imageLoader;

    public TechCityLevel(Game game, Stage stage) {
        super(game, stage);
    }

    @Override
    public void createLevel() {
        setLevelBackgroundImage(imagePath + "TechCity.png");

        levelPane = new StackPane();

        // Add character image
        Image characterImage = new Image(getClass().getResourceAsStream(imagePath + "Quacky.png"));
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
        } else if (event.getCode() == KeyCode.UP){
            characterY -= 5;
        } else if (event.getCode() == KeyCode.DOWN){
            characterY += 5;
        }
        updateBackground();
        updateCharacterPosition();
    }

    private void updateBackground() {
        gamePane.updateBackgroundPosition(characterX);
    }

    private void updateCharacterPosition() {
        characterImageView.setTranslateX(characterX);
        characterImageView.setTranslateY(characterY);
    }

    private void update() {
        // Update game logic, if any
    }
}