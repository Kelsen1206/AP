package com.example;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TechCity {
    private double spriteSize = 40.625;
    private ImageLoader imageLoader;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private double backgroundWidth;
    private Sprite sprite;
    private Pane gameWorld;
    private Quacky quacky;
    private final double viewWidth = 375;
    private Camera camera;
    int[][] mapData =
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 91, 91, 91, 91, 91, 84, 42, 84, 91, 91, 91, 91, 91, 91, 91, 42, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 19, 19, 19, 19, 32, 32, 34, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 305, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 91, 91, 91, 91, 91, 91, 42, 92, 91, 91, 91, 67, 42, 79, 79, 42, 91, 91, 39, 81, 41, 91, 91, 91, 91, 91, 91, 91, 0, 33, 31, 32, 32, 33, 42, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 74, 0, 0, 0, 73, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 337, 338, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 18, 32, 32, 32, 33, 0, 0, 32, 32, 32, 32, 21, 0, 0, 73, 65, 80, 81, 82, 83, 93, 88, 80, 40, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 91, 91, 79, 79, 42, 67, 67, 67, 67, 67, 42, 0, 0, 42, 79, 79, 39, 88, 41, 79, 79, 39, 81, 41, 79, 0, 0, 41, 0, 0, 0, 0, 42, 0, 0, 0, 73, 48, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 369, 370, 371, 0, 0, 0, 0, 63, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0},
                    {0, 0, 0, 0, 0, 18, 30, 86, 17, 48, 49, 0, 0, 48, 1, 95, 95, 29, 21, 0, 0, 73, 65, 79, 79, 41, 73, 40, 40, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 42, 15, 68, 68, 68, 68, 42, 0, 0, 42, 0, 0, 73, 48, 74, 0, 0, 73, 48, 74, 0, 0, 0, 41, 0, 0, 0, 31, 42, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 64, 0, 0, 0, 10, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 32, 73, 48, 48, 74, 0, 84, 91, 7, 73, 74, 25, 0, 0, 0},
                    {20, 20, 20, 20, 20, 30, 86, 86, 17, 0, 0, 0, 0, 0, 1, 96, 95, 48, 29, 21, 0, 0, 73, 65, 79, 41, 0, 73, 74, 0, 0, 0, 0, 0, 0, 0, 58, 58, 58, 58, 58, 0, 0, 0, 14, 58, 58, 58, 58, 58, 0, 0, 0, 0, 42, 0, 0, 0, 0, 42, 0, 0, 75, 75, 75, 42, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 0, 0, 0, 47, 48, 49, 0, 0, 0, 0, 0, 0, 0, 63, 32, 33, 2, 3, 0, 39, 41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 32, 0, 32, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 40, 74, 0, 0, 0, 0, 84, 91, 9, 0, 0, 26, 0, 0, 0},
                    {96, 28, 28, 28, 28, 96, 28, 28, 28, 61, 77, 0, 0, 0, 50, 0, 42, 93, 77, 29, 21, 0, 0, 73, 65, 41, 0, 0, 0, 0, 0, 0, 32, 96, 28, 28, 0, 73, 65, 67, 67, 67, 67, 66, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 0, 0, 0, 0, 0, 0, 0, 0, 31, 32, 64, 0, 63, 71, 84, 41, 4, 5, 0, 69, 41, 0, 64, 0, 0, 0, 0, 0, 0, 63, 32, 32, 32, 64, 0, 0, 55, 34, 34, 0, 34, 34, 57, 0, 0, 0, 63, 32, 32, 32, 64, 0, 0, 0, 0, 0, 0, 0, 0, 73, 65, 39, 40, 41, 0, 0, 0, 0, 84, 91, 11, 0, 0, 27, 0, 0, 0},
                    {19, 19, 19, 19, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 42, 87, 88, 40, 29, 21, 0, 0, 73, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 73, 65, 58, 58, 58, 58, 58, 0, 0, 40, 29, 21, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 32, 64, 0, 0, 0, 49, 0, 0, 0, 0, 0, 0, 0, 0, 39, 79, 41, 0, 39, 40, 84, 41, 6, 7, 0, 39, 70, 0, 72, 64, 0, 0, 0, 0, 55, 34, 34, 34, 34, 34, 57, 63, 32, 32, 15, 0, 15, 32, 32, 32, 64, 55, 34, 34, 34, 34, 34, 57, 0, 0, 0, 0, 0, 0, 0, 0, 73, 39, 40, 41, 31, 32, 32, 32, 32, 32, 32, 32, 32, 33, 0, 0, 0},
                    {19, 19, 19, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 14, 16, 36, 40, 29, 64, 0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 0, 41, 0, 73, 65, 66, 74, 0, 0, 14, 16, 36, 40, 29, 64, 0, 0, 0, 0, 0, 0, 19, 19, 19, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 32, 33, 0, 0, 39, 67, 41, 0, 0, 0, 0, 0, 0, 0, 0, 63, 32, 33, 0, 47, 48, 49, 0, 0, 39, 92, 41, 8, 9, 0, 69, 41, 0, 40, 41, 0, 0, 0, 0, 0, 0, 15, 0, 15, 0, 63, 71, 61, 67, 67, 0, 67, 35, 36, 67, 72, 64, 0, 15, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 66, 74, 39, 40, 40, 78, 40, 40, 40, 62, 62, 41, 0, 0, 0},
                    {19, 19, 91, 91, 0, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 18, 95, 20, 40, 40, 43, 44, 72, 64, 0, 0, 0, 0, 32, 0, 48, 0, 0, 14, 15, 16, 0, 41, 0, 0, 73, 74, 0, 18, 95, 20, 40, 40, 43, 44, 72, 64, 0, 0, 0, 0, 0, 19, 19, 91, 91, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 65, 41, 0, 0, 40, 67, 43, 44, 0, 0, 0, 0, 0, 0, 0, 47, 48, 49, 3, 63, 42, 64, 0, 0, 39, 92, 40, 40, 40, 40, 59, 60, 40, 40, 41, 40, 40, 0, 40, 40, 40, 40, 40, 40, 35, 70, 44, 40, 40, 43, 0, 40, 40, 69, 62, 70, 69, 40, 40, 40, 40, 40, 40, 40, 40, 40, 0, 0, 40, 40, 40, 40, 39, 74, 0, 73, 59, 45, 77, 60, 61, 61, 44, 66, 74, 0, 0, 0},
                    {20, 20, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 0, 20, 20, 30, 43, 44, 60, 77, 77, 59, 40, 67, 37, 38, 32, 32, 32, 0, 20, 20, 20, 20, 20, 20, 20, 41, 0, 20, 20, 20, 20, 30, 43, 44, 60, 77, 77, 59, 40, 67, 37, 38, 32, 32, 32, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 32, 20, 20, 20, 20, 30, 43, 0, 0, 77, 77, 59, 40, 0, 37, 38, 32, 32, 32, 0, 63, 78, 64, 63, 71, 77, 72, 64, 0, 39, 92, 41, 12, 13, 0, 39, 41, 0, 40, 41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            };

    public TechCity(Game game, Stage stage, ImageLoader imageLoader) throws FileNotFoundException {
        this.game = game;
        this.stage = stage;
        this.imageLoader = imageLoader;

        this.backgroundImage = new ImageView(imageLoader.loadImage("/images/TechCity.png"));
        this.gameWorld = new Pane();
        this.gamePane = new GamePane(backgroundImage.getImage());
        this.scene = new Scene(gamePane, 1000, 650);
        this.camera = new Camera();
    }

    private void updateCamera() {
        double quackyCenterX = quacky.getX() + quacky.getSprite().getFitWidth() / 2;
        double newCameraX = quackyCenterX - viewWidth / 2;

        // Ensure the camera doesn't move past the edges of the background
        newCameraX = Math.max(0, Math.min(newCameraX, backgroundWidth - viewWidth));

        camera.setX(newCameraX);
    }

    public void createLevel() throws IOException {
        backgroundWidth = backgroundImage.getImage().getWidth();
        backgroundImage.setFitWidth(backgroundWidth);
        backgroundImage.setFitHeight(650);
        backgroundImage.setPreserveRatio(true);

        // Create tile map
        gridPane = new GridPane();
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                int tileCode = mapData[i][j];
                ImageView tileView;
                try {
                    InputStream spriteStream = getClass().getResourceAsStream("/images/TechCityTileSet.png");
                    if (spriteStream == null) {
                        System.err.println("Sprite file not found.");
                        continue;
                    }
                    sprite = new Sprite(spriteStream, 32, 32);
                    if (tileCode != 0) {
                        tileView = new ImageView(sprite.getTile(0, 0));
                    } else {
                        // Create an empty placeholder for tiles with code 0
                        tileView = new ImageView();
                    }
                    tileView.setFitWidth(spriteSize);
                    tileView.setFitHeight(spriteSize);
                    gridPane.add(tileView, j, i);
                } catch (Exception e) {
                    System.err.println("Error loading tile image: " + e.getMessage());
                }
            }
        }

        gridPane.setPrefSize(backgroundWidth, 650);

        gameWorld = new Pane();
        gameWorld.getChildren().addAll(backgroundImage, gridPane);
        gameWorld.setPrefSize(backgroundWidth, 650);

        quacky = new Quacky(200, 500, 0, backgroundWidth); // Starting position
        quacky.getSprite().setVisible(true);
        quacky.getSprite().setViewOrder(-1);
        gameWorld.getChildren().add(quacky.getSprite());

        // Set the content of the scene to the gameWorld
        scene = new Scene(gameWorld, 1000, 650);

        // Add input handler
        InputHandler inputHandler = new InputHandler(game, gamePane, stage, this::show, quacky);
        scene.setOnKeyPressed(event -> inputHandler.keyPressed(event));
        scene.setOnKeyReleased(event -> inputHandler.keyReleased(event));

        // Start the game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    private void updateBackground() {
        // Update Quacky's screen position
        double quackyScreenX = quacky.getX() - camera.getX();
        quacky.getSprite().setTranslateX(quackyScreenX);

        // Move the background image and the grid pane
        double cameraX = -camera.getX();
        backgroundImage.setTranslateX(cameraX);
        gridPane.setTranslateX(cameraX);
    }

    public void update() {
        quacky.update();
        updateCamera();
        updateBackground();
    }

    public void show() {
        stage.setScene(scene);
        stage.show();
    }
}
