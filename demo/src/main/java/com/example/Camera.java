package com.example;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Camera {
    private Pane cameraPane;
    private double width;
    private double height;

    public Camera(double width, double height) {
        this.width = width;
        this.height = height;
        this.cameraPane = new Pane();
        cameraPane.setPrefSize(width, height);
    }

    public void update(Node player, Pane levelPane) {
        Bounds playerBounds = player.getBoundsInParent();
        double offsetX = playerBounds.getMinX() - width / 2;
        double offsetY = playerBounds.getMinY() - height / 2;

        // Limit scrolling to map size
        offsetX = Math.min(offsetX, 0);
        offsetY = Math.min(offsetY, 0);
        offsetX = Math.max(offsetX, -(levelPane.getPrefWidth() - width));
        offsetY = Math.max(offsetY, -(levelPane.getPrefHeight() - height));

        levelPane.setLayoutX(offsetX);
        levelPane.setLayoutY(offsetY);
    }

    public Pane getCameraPane() {
        return cameraPane;
    }
}
