package com.example;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class TechCity {
    private double spriteSize = 50;
    private ImageLoader imageLoader;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private Sprite sprite;
    private Pane gameWorld;
    private Quacky quacky;
    private final double viewWidth = 400;
    private Camera camera;
    private static final long FRAME_TIME = 8_333_333; // 60 FPS in nanoseconds
    private int initialX = 100;
    private int initialY = 200;
    private SoundManager soundManager;
    private boolean gameOver;
    private Text gameOverText;
    private SettingsMenu settingsMenu;
    int[][] mapData =
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
        this.soundManager = SoundManager.getInstance();
        soundManager.loadSounds();

        gameOver = false;
        gameOverText = new Text("GAME OVER\nPress SPACE to respawn");
        gameOverText.setFont(Font.loadFont(getClass().getResourceAsStream( "/fonts/ARCADE_N.ttf"), 45));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setVisible(false);
    }

    private void updateCamera() {
        double quackyCenterX = quacky.getX() + quacky.getSprite().getFitWidth() / 2;
        double newCameraX = quackyCenterX - viewWidth / 2;

        // Ensure the camera doesn't move past the edges of the background
        double maxCameraX = gridPane.getPrefWidth() - scene.getWidth();
        newCameraX = Math.max(0, Math.min(newCameraX, maxCameraX));

        camera.setX(newCameraX);
    }

    public void createLevel() {
        // Create tile map
        gridPane = new GridPane();
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                int tileCode = mapData[i][j];
                ImageView tileView;
                try {
                    InputStream spriteStream = getClass().getResourceAsStream("/images/TechCityTileSet.png");
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

        int mapWidth = mapData[0].length;
        double gridWidth = mapWidth * spriteSize;
        gridPane.setPrefSize(gridWidth, 650);

        backgroundImage.setFitWidth(gridWidth);
        backgroundImage.setFitHeight(650);
        backgroundImage.setPreserveRatio(false);

        double sceneWidth = Math.min(1000, gridWidth);

        gameWorld = new Pane();
        gameWorld.getChildren().addAll(backgroundImage, gridPane);
        gameWorld.setPrefSize(gridWidth, 650);

        gameWorld.getChildren().add(gameOverText);

        gameWorld.setClip(new Rectangle(1000, 650));

        quacky = new Quacky(initialX, initialY, 0, gridWidth, soundManager); // Starting position
        quacky.getSprite().setVisible(true);
        quacky.getSprite().setViewOrder(-1);
        gameWorld.getChildren().add(quacky.getSprite());

        // Set the content of the scene to the gameWorld
        scene = new Scene(gameWorld, sceneWidth, 650);

        // Add input handler
        InputHandler inputHandler = new InputHandler(game, gamePane, stage, this::show, quacky, this::restartGame);
        scene.setOnKeyPressed(event -> inputHandler.keyPressed(event));
        scene.setOnKeyReleased(event -> inputHandler.keyReleased(event));

        // Start the game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= FRAME_TIME) {
                    if (game.getGameStatus() == GameStatus.GAME_RUNNING) {
                        quacky.update(now);
                        checkCollisions();
                        debugRender();
                        updateCamera();
                        updateBackground();

                        if (quacky.getY() >= getBottomBoundary()) {
                            quacky.setY(getBottomBoundary());
                            quacky.die();
                        }

                        if (quacky.isDead() && !quacky.isDying()) {
                            game.setGameStatus(GameStatus.GAME_OVER);
                            showGameOver();
                        }
                    }

                    lastUpdate = now;
                }
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
        double minTranslation = Math.min(0, viewWidth - gridPane.getPrefWidth());
        cameraX = Math.max(minTranslation, Math.min(0, cameraX));

        backgroundImage.setFitWidth(Math.max(gridPane.getPrefWidth(), scene.getWidth()));

        backgroundImage.setTranslateX(cameraX);
        gridPane.setTranslateX(cameraX);
    }

    public void show() {
        stage.setScene(scene);
        stage.show();
    }

    private void showGameOver(){
        gameOverText.setVisible(true);
        gameOverText.setX(20);
        gameOverText.setY(100);
        gameOverText.toFront();
    }

    public void restartGame() {
        quacky.respawn();
        gameOver = false;
        gameOverText.setVisible(false);
        camera.setX(0);
        updateBackground();
        game.setGameStatus(GameStatus.GAME_RUNNING);
    }

    private void checkCollisions() {
        int tileSize = 50; // Adjust this to match your tile size
        int quackyTileX = (int) (quacky.getX() / tileSize);
        int quackyTileY = (int) (quacky.getY() / tileSize);

        for (int y = Math.max(0, quackyTileY - 1); y <= Math.min(mapData.length - 1, quackyTileY + 2); y++) {
            for (int x = Math.max(0, quackyTileX - 1); x <= Math.min(mapData[0].length - 1, quackyTileX + 2); x++) {
                if (mapData[y][x] != 0) {  // If it's not an empty tile
                    Rectangle2D tileBounds = new Rectangle2D(
                            x * tileSize,
                            y * tileSize,
                            tileSize,
                            tileSize
                    );

                    if (quacky.getBoundingBox().intersects(tileBounds)) {
                        handleCollision(quacky, tileBounds);
                    }
                }
            }
        }
    }

    private void handleCollision(Quacky quacky, Rectangle2D tileBounds) {
        double overlapLeft = quacky.getX() + quacky.getSprite().getFitWidth() - tileBounds.getMinX();
        double overlapRight = tileBounds.getMaxX() - quacky.getX();
        double overlapTop = quacky.getY() + quacky.getSprite().getFitHeight() - tileBounds.getMinY();
        double overlapBottom = tileBounds.getMaxY() - quacky.getY();

        double minOverlapX = Math.min(overlapLeft, overlapRight);
        double minOverlapY = Math.min(overlapTop, overlapBottom);

        if (minOverlapX < minOverlapY) {
            if (overlapLeft < overlapRight) {
                quacky.setX(tileBounds.getMinX() - quacky.getSprite().getFitWidth());
            } else {
                quacky.setX(tileBounds.getMaxX());
            }
            quacky.stopMoving(); // Assuming this method exists to stop horizontal movement
        } else {
            if (overlapTop < overlapBottom) {
                quacky.land(tileBounds.getMinY());
            } else {
                quacky.setY(tileBounds.getMaxY());
                quacky.stopJump();
            }
        }
    }

    private void debugRender() {
        // Clear previous debug visuals
        gameWorld.getChildren().removeIf(node -> node instanceof Rectangle);

        // Render Quacky's collision box
        Rectangle quackyBox = new Rectangle(
                quacky.getX(),
                quacky.getY(),
                quacky.getSprite().getFitWidth(),
                quacky.getSprite().getFitHeight()
        );
        quackyBox.setFill(Color.TRANSPARENT);
        quackyBox.setStroke(Color.RED);
        gameWorld.getChildren().add(quackyBox);

        // Render visible tile collision boxes
        int tileSize = 50;
        int startX = (int) (camera.getX() / tileSize);
        double endX = startX + (viewWidth / tileSize) + 1;

        for (int y = 0; y < mapData.length; y++) {
            for (int x = startX; x < endX && x < mapData[0].length; x++) {
                if (mapData[y][x] != 0) {
                    Rectangle tileBox = new Rectangle(
                            x * tileSize - camera.getX(),
                            y * tileSize,
                            tileSize,
                            tileSize
                    );
                    tileBox.setFill(Color.TRANSPARENT);
                    tileBox.setStroke(Color.BLUE);
                    gameWorld.getChildren().add(tileBox);
                }
            }
        }
    }

    private double getBottomBoundary() {
        return mapData.length * spriteSize - quacky.getSprite().getFitHeight();
    }
}