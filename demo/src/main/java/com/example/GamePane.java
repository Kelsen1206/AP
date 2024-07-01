package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GamePane extends StackPane {
    private ImageView backgroundImageView;
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
