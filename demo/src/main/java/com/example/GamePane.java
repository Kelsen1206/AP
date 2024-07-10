package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GamePane extends StackPane {
    private ImageView backgroundImageView;
    private double backgroundWidth;
    private VBox loginComponents;
    private GameStatus gameStatus;

    public GamePane(Image backgroundImage) {
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(650);
        backgroundImageView.setPreserveRatio(false);
        this.getChildren().add(backgroundImageView);

        loginComponents = new VBox();
        loginComponents.setVisible(false);
        this.getChildren().add(loginComponents);
    }

    public void setBackgroundImage(Image backgroundImage) {
        backgroundImageView.setImage(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(650);
        backgroundImageView.setPreserveRatio(false);
    }

    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
}