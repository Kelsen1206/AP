package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GamePane extends StackPane {
    private ImageView imageView;

    public GamePane(Image backgroundImage) {
        imageView = new ImageView(backgroundImage);
        imageView.setFitHeight(650);
        imageView.setFitWidth(1000);
        getChildren().add(imageView);
    }

    public void setBackgroundImage(Image image) {
        imageView.setImage(image);
    }
}
