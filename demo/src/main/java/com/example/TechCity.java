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
    private Scene scene;
    private double backgroundWidth;
    private Sprite sprite;
    private Pane gameWorld;
    private Quacky quacky;
    private final double viewWidth = 375;
    private Camera camera;
    int[][] mapData = {};

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
        GridPane gridPane = new GridPane();
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

        // Move the background image
        backgroundImage.setTranslateX(-camera.getX());
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
