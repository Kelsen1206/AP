package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GamePane extends StackPane {
    private ImageView backgroundImageView;
    private double backgroundWidth;
    private VBox loginComponents;

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

    public void setLevelBackgroundImage(Image backgroundImage) {
        backgroundImageView.setImage(backgroundImage);
        backgroundWidth = backgroundImage.getWidth();
        backgroundImageView.setFitWidth(backgroundWidth);
        backgroundImageView.setFitHeight(650);
        backgroundImageView.setPreserveRatio(true);
    }

    public void updateBackgroundPosition(double xOffset) {
        double newX = -xOffset;
        if (newX < -(backgroundWidth - 1000)) {
            newX = -(backgroundWidth - 1000);  // Prevent moving beyond the right edge
        } else if (newX > 0) {
            newX = 0;  // Prevent moving beyond the left edge
        }
        backgroundImageView.setTranslateX(newX);
    }

    public void setLoginComponents(VBox loginComponents) {
        this.getChildren().remove(this.loginComponents);
        this.loginComponents = loginComponents;
        this.loginComponents.setVisible(false);
        this.getChildren().add(this.loginComponents);
    }

    public void showLoginComponents() {
        loginComponents.setVisible(true);
    }

    public void hideLoginComponents() {
        loginComponents.setVisible(false);
    }
}
