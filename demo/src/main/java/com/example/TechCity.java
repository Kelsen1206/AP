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
import java.io.IOException;
import java.io.InputStream;

public class TechCity extends Level{
    private double spriteSize = 50;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private Sprite bigSprite;
    private Sprite towerSprite1;
    private Sprite towerSprite2;
    private Sprite pipeSprite;
    private Pane gameWorld;
    private Quacky quacky;
    private final double viewWidth = 400;
    private Camera camera;
    private static final long FRAME_TIME = 8_333_333;
    private int initialX = 100;
    private int initialY = 200;
    private SoundManager soundManager;
    private boolean gameOver;
    private Text gameOverText;
    private InputHandler inputHandler;
    private boolean levelCompleted;
    int[][] mapData =
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 91, 91, 91, 91, 91, 84, 42, 84, 91, 91, 91, 91, 91, 91, 91, 42, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 19, 19, 19, 19, 32, 32, 34, 32, 32, 32, 32, 32, 0, 0, 0, 0, 0, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 305, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 91, 91, 91, 91, 91, 91, 42, 92, 91, 91, 91, 67, 42, 79, 79, 42, 91, 91, 39, 81, 41, 91, 91, 91, 91, 91, 91, 91, 0, 33, 31, 32, 32, 33, 42, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 74, 0, 0, 0, 73, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 337, 338, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 18, 32, 32, 32, 33, 0, 0, 32, 32, 32, 32, 21, 0, 0, 73, 65, 80, 81, 82, 83, 93, 88, 80, 40, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 91, 91, 79, 79, 42, 67, 67, 67, 67, 67, 42, 0, 0, 42, 79, 79, 39, 88, 41, 79, 79, 39, 81, 41, 79, 0, 0, 41, 0, 0, 0, 0, 42, 0, 0, 0, 73, 48, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 0, 0, 0},
                    {0, 0, 0, 0, 0, 18, 30, 86, 17, 48, 49, 0, 0, 48, 1, 95, 95, 29, 21, 0, 0, 73, 65, 79, 79, 41, 73, 40, 40, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 42, 15, 68, 68, 68, 68, 42, 0, 0, 42, 0, 0, 73, 48, 74, 0, 0, 73, 48, 74, 0, 0, 0, 41, 0, 0, 0, 31, 42, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 64, 0, 0, 0, 10, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 32, 73, 48, 48, 74, 0, 84, 91, 7, 73, 74, 25, 0, 0, 0},
                    {20, 20, 20, 20, 20, 30, 86, 86, 17, 0, 0, 0, 0, 0, 1, 96, 95, 48, 29, 21, 0, 0, 73, 65, 79, 41, 0, 73, 74, 0, 0, 0, 0, 0, 0, 0, 58, 58, 58, 58, 58, 0, 0, 0, 14, 58, 58, 58, 58, 58, 0, 0, 0, 0, 42, 0, 0, 0, 0, 42, 0, 0, 75, 75, 75, 42, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 0, 0, 0, 47, 48, 49, 0, 0, 0, 0, 0, 0, 0, 63, 32, 33, 2, 3, 0, 39, 41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 32, 0, 32, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 40, 74, 0, 0, 0, 0, 84, 91, 9, 0, 0, 26, 0, 0, 0},
                    {96, 28, 28, 28, 28, 96, 28, 28, 28, 61, 77, 0, 0, 0, 50, 0, 42, 93, 77, 29, 21, 0, 0, 73, 65, 41, 0, 0, 0, 0, 0, 0, 32, 96, 28, 28, 0, 73, 65, 67, 67, 67, 67, 66, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 41, 0, 0, 0, 0, 0, 0, 0, 0, 31, 32, 64, 0, 63, 71, 84, 41, 4, 5, 0, 69, 41, 0, 64, 0, 0, 0, 0, 0, 0, 63, 32, 32, 32, 64, 0, 0, 55, 34, 34, 0, 34, 34, 57, 0, 0, 0, 63, 32, 32, 32, 64, 0, 0, 0, 0, 0, 0, 0, 0, 73, 65, 39, 40, 41, 0, 0, 0, 0, 84, 91, 11, 0, 0, 27, 0, 0, 0},
                    {19, 19, 19, 19, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 42, 87, 88, 40, 29, 21, 0, 0, 73, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 73, 65, 58, 58, 58, 58, 58, 0, 0, 40, 29, 21, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 32, 64, 0, 0, 0, 49, 0, 0, 0, 0, 0, 0, 0, 0, 39, 79, 41, 0, 39, 40, 84, 41, 6, 7, 0, 39, 70, 0, 72, 64, 0, 0, 0, 0, 55, 34, 34, 34, 34, 34, 57, 63, 32, 32, 15, 0, 15, 32, 32, 32, 64, 55, 34, 34, 34, 34, 34, 57, 0, 0, 0, 0, 0, 0, 0, 0, 73, 39, 40, 41, 31, 32, 32, 32, 32, 32, 32, 32, 32, 33, 0, 0, 0},
                    {19, 19, 19, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 42, 14, 16, 36, 40, 29, 64, 0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 0, 41, 0, 73, 65, 66, 74, 0, 0, 14, 16, 36, 40, 29, 64, 0, 0, 0, 0, 0, 0, 19, 19, 19, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 32, 33, 0, 0, 39, 67, 41, 0, 0, 0, 0, 0, 0, 0, 0, 63, 32, 33, 0, 47, 48, 49, 0, 0, 39, 92, 41, 8, 9, 0, 69, 41, 0, 40, 41, 0, 0, 0, 0, 0, 0, 15, 0, 15, 0, 63, 71, 61, 67, 67, 0, 67, 35, 36, 67, 72, 64, 0, 15, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 66, 74, 39, 40, 40, 78, 40, 40, 40, 62, 62, 41, 0, 0, 0},
                    {19, 19, 91, 91, 0, 14, 15, 16, 0, 0, 0, 0, 0, 0, 0, 18, 95, 20, 40, 40, 43, 44, 72, 64, 0, 0, 0, 0, 32, 0, 48, 0, 0, 14, 15, 16, 0, 41, 0, 0, 73, 74, 0, 18, 95, 20, 40, 40, 43, 44, 72, 64, 0, 0, 0, 0, 0, 19, 19, 91, 91, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 39, 65, 41, 0, 0, 40, 67, 43, 44, 0, 0, 0, 0, 0, 0, 0, 47, 48, 49, 3, 63, 42, 64, 0, 0, 39, 92, 40, 40, 40, 40, 59, 60, 40, 40, 41, 40, 40, 0, 40, 40, 40, 40, 40, 40, 35, 70, 44, 40, 40, 43, 0, 40, 40, 69, 62, 70, 69, 40, 40, 40, 40, 40, 40, 40, 40, 40, 0, 0, 40, 40, 40, 40, 39, 74, 0, 73, 59, 45, 77, 60, 61, 61, 44, 66, 74, 0, 0, 0},
                    {20, 20, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 0, 20, 20, 30, 43, 44, 60, 77, 77, 59, 40, 67, 37, 38, 32, 32, 32, 0, 20, 20, 20, 20, 20, 20, 20, 41, 0, 20, 20, 20, 20, 30, 43, 44, 60, 77, 77, 59, 40, 67, 37, 38, 32, 32, 32, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 32, 20, 20, 20, 20, 30, 43, 0, 0, 77, 77, 59, 40, 0, 37, 38, 32, 32, 32, 0, 63, 78, 64, 63, 71, 77, 72, 64, 0, 39, 92, 41, 12, 13, 0, 39, 41, 0, 40, 41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            };


    public TechCity(Game game, Stage stage, ImageLoader imageLoader) throws FileNotFoundException {
        super(game, stage, imageLoader);

        this.game = game;
        this.stage = stage;
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

    @Override
    public void createLevel() {
        // Create tile map
        gridPane = new GridPane();
        for (int i = 0; i < mapData.length; i++) {
            for (int j = 0; j < mapData[i].length; j++) {
                int tileCode = mapData[i][j];
                ImageView tileView = null;
                try {
                    InputStream bigSpriteStream = getClass().getResourceAsStream("/images/TechCityTileSet.png");
                    bigSprite = new Sprite(bigSpriteStream, 32, 32);
                    InputStream towerSpriteStream1 = getClass().getResourceAsStream("/images/Tech City Tower1.png");
                    towerSprite1 = new Sprite(towerSpriteStream1, 30, 32);
                    InputStream towerSpriteStream2 = getClass().getResourceAsStream("/images/Tech City Tower2.png");
                    towerSprite2 = new Sprite(towerSpriteStream2, 34, 32);
                    InputStream pipeSpriteStream = getClass().getResourceAsStream("/images/Tech City Pipe.png");
                    pipeSprite = new Sprite(pipeSpriteStream, 34, 42);

                    switch (tileCode) {
                        case 31:
                            tileView = new ImageView(bigSprite.getTile(0, 0));
                            break;
                        case 20:
                        case 32:
                        case 305:
                            tileView = new ImageView(bigSprite.getTile(1, 0));
                            break;
                        case 33:
                            tileView = new ImageView(bigSprite.getTile(2, 0));
                            break;
                        case 34:
                            tileView = new ImageView(bigSprite.getTile(3, 0));
                            break;
                        case 35:
                            tileView = new ImageView(bigSprite.getTile(4, 0));
                            break;
                        case 36:
                            tileView = new ImageView(bigSprite.getTile(5, 0));
                            break;
                        case 37:
                            tileView = new ImageView(bigSprite.getTile(6, 0));
                            break;
                        case 38:
                            tileView = new ImageView(bigSprite.getTile(7, 0));
                            break;
                        case 39:
                            tileView = new ImageView(bigSprite.getTile(0, 1));
                            break;
                        case 40:
                            tileView = new ImageView(bigSprite.getTile(1, 1));
                            break;
                        case 17:
                        case 41:
                            tileView = new ImageView(bigSprite.getTile(2, 1));
                            break;
                        case 42:
                            tileView = new ImageView(bigSprite.getTile(3, 1));
                            break;
                        case 43:
                            tileView = new ImageView(bigSprite.getTile(4,1));
                            break;
                        case 44:
                            tileView = new ImageView(bigSprite.getTile(5,1));
                            break;
                        case 45:
                            tileView = new ImageView(bigSprite.getTile(6,1));
                            break;
                        case 96:
                            tileView = new ImageView(bigSprite.getTile(7,1));
                            break;
                        case 1:
                        case 47:
                            tileView = new ImageView(bigSprite.getTile(0,2));
                            break;
                        case 28:
                        case 48:
                            tileView = new ImageView(bigSprite.getTile(1,2));
                            break;
                        case 49:
                            tileView = new ImageView(bigSprite.getTile(2,2));
                            break;
                        case 50:
                            tileView = new ImageView(bigSprite.getTile(3,2));
                            break;
                        case 55:
                            tileView = new ImageView(bigSprite.getTile(0,3));
                            break;
                        case 57:
                            tileView = new ImageView(bigSprite.getTile(2,3));
                            break;
                        case 58:
                            tileView = new ImageView(bigSprite.getTile(3,3));
                            break;
                        case 59:
                            tileView = new ImageView(bigSprite.getTile(4,3));
                            break;
                        case 60:
                            tileView = new ImageView(bigSprite.getTile(5,3));
                            break;
                        case 61:
                            tileView = new ImageView(bigSprite.getTile(6,3));
                            break;
                        case 62:
                            tileView = new ImageView(bigSprite.getTile(7,3));
                            break;
                        case 18:
                        case 63:
                            tileView = new ImageView(bigSprite.getTile(0,4));
                            break;
                        case 21:
                        case 64:
                            tileView = new ImageView(bigSprite.getTile(1,4));
                            break;
                        case 65:
                            tileView = new ImageView(bigSprite.getTile(2,4));
                            break;
                        case 66:
                        case 95:
                            tileView = new ImageView(bigSprite.getTile(3,4));
                            break;
                        case 67:
                            tileView = new ImageView(bigSprite.getTile(4,4));
                            break;
                        case 68:
                            tileView = new ImageView(bigSprite.getTile(5,4));
                            break;
                        case 69:
                            tileView = new ImageView(bigSprite.getTile(6,4));
                            break;
                        case 70:
                            tileView = new ImageView(bigSprite.getTile(7,4));
                            break;
                        case 30:
                        case 71:
                            tileView = new ImageView(bigSprite.getTile(0,5));
                            break;
                        case 29:
                        case 72:
                            tileView = new ImageView(bigSprite.getTile(1,5));
                            break;
                        case 73:
                            tileView = new ImageView(bigSprite.getTile(2,5));
                            break;
                        case 74:
                            tileView = new ImageView(bigSprite.getTile(3,5));
                            break;
                        case 75:
                            tileView = new ImageView(bigSprite.getTile(4,5));
                            break;
                        case 76:
                            tileView = new ImageView(bigSprite.getTile(5,5));
                            break;
                        case 77:
                            tileView = new ImageView(bigSprite.getTile(6,5));
                            break;
                        case 78:
                            tileView = new ImageView(bigSprite.getTile(7,5));
                            break;
                        case 79:
                            tileView = new ImageView(bigSprite.getTile(0,6));
                            break;
                        case 80:
                            tileView = new ImageView(bigSprite.getTile(1,6));
                            break;
                        case 81:
                            tileView = new ImageView(bigSprite.getTile(2,6));
                            break;
                        case 82:
                            tileView = new ImageView(bigSprite.getTile(3,6));
                            break;
                        case 83:
                            tileView = new ImageView(bigSprite.getTile(4,6));
                            break;
                        case 84:
                            tileView = new ImageView(bigSprite.getTile(5,6));
                            break;
                        case 86:
                            tileView = new ImageView(bigSprite.getTile(7,6));
                            break;
                        case 87:
                            tileView = new ImageView(bigSprite.getTile(0,7));
                            break;
                        case 88:
                            tileView = new ImageView(bigSprite.getTile(1,7));
                            break;
                        case 19:
                        case 91:
                            tileView = new ImageView(bigSprite.getTile(4,7));
                            break;
                        case 92:
                            tileView = new ImageView(bigSprite.getTile(5,7));
                            break;
                        case 93:
                            tileView = new ImageView(bigSprite.getTile(6,7));
                            break;
                        case 14:
                            tileView = new ImageView(pipeSprite.getTile(0,0));
                            break;
                        case 15:
                            tileView = new ImageView(pipeSprite.getTile(1,0));
                            break;
                        case 16:
                            tileView = new ImageView(pipeSprite.getTile(2,0));
                            break;
                        case 25:
                            tileView = new ImageView(towerSprite1.getTile(0,2));
                            break;
                        case 26:
                            tileView = new ImageView(towerSprite1.getTile(0,3));
                            break;
                        case 27:
                            tileView = new ImageView(towerSprite1.getTile(0,4));
                            break;
                        case 2:
                            tileView = new ImageView(towerSprite2.getTile(0,0));
                            break;
                        case 3:
                            tileView = new ImageView(towerSprite2.getTile(1,0));
                            break;
                        case 4:
                            tileView = new ImageView(towerSprite2.getTile(0,1));
                            break;
                        case 5:
                            tileView = new ImageView(towerSprite2.getTile(1,1));
                            break;
                        case 6:
                            tileView = new ImageView(towerSprite2.getTile(0,2));
                            break;
                        case 7:
                            tileView = new ImageView(towerSprite2.getTile(1, 2));
                            break;
                        case 8:
                            tileView = new ImageView(towerSprite2.getTile(0,3));
                            break;
                        case 9:
                            tileView = new ImageView(towerSprite2.getTile(1, 3));
                            break;
                        case 10:
                            tileView = new ImageView(towerSprite2.getTile(0,4));
                            break;
                        case 11:
                            tileView = new ImageView(towerSprite2.getTile(1, 4));
                            break;
                        case 12:
                            tileView = new ImageView(towerSprite2.getTile(0,5));
                            break;
                        case 13:
                            tileView = new ImageView(towerSprite2.getTile(1, 5));
                            break;
                        case 100:
                            tileView = new ImageView(pipeSprite.getTile(2,0));
                            break;
                        case 0:
                        default:
                            tileView = new ImageView();
                            break;

                    }
                    assert tileView != null;
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
        inputHandler = new InputHandler(game, gamePane, stage, this::show, quacky, this::restartGame);
        scene.setOnKeyPressed(event -> inputHandler.keyPressed(event));
        scene.setOnKeyReleased(event -> inputHandler.keyReleased(event));

        // Start the game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= FRAME_TIME) {
                    if (game.getGameStatus() == GameStatus.GAME_RUNNING && !levelCompleted) {
                        quacky.update(now);
                        try {
                            checkCollisions();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
                    } else if (levelCompleted) {
                        this.stop();
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

    @Override
    public void show() {
        resetLevelState();
        stage.setScene(scene);
        stage.show();
    }

    private void showGameOver(){
        gameOverText.setVisible(true);
        gameOverText.setX(20);
        gameOverText.setY(100);
        gameOverText.toFront();
    }

    @Override
    public void restartGame() {
        quacky.respawn();
        gameOver = false;
        gameOverText.setVisible(false);
        camera.setX(0);
        updateBackground();
        game.setGameStatus(GameStatus.GAME_RUNNING);
    }

    private void checkCollisions() throws IOException {
        int tileSize = 50; // Adjust this to match your tile size
        int quackyTileX = (int) (quacky.getX() / tileSize);
        int quackyTileY = (int) (quacky.getY() / tileSize);

        for (int y = Math.max(0, quackyTileY - 1); y <= Math.min(mapData.length - 1, quackyTileY + 2); y++) {
            for (int x = Math.max(0, quackyTileX - 1); x <= Math.min(mapData[0].length - 1, quackyTileX + 2); x++) {
                int tileCode = mapData[y][x];
                if (tileCode != 0) {  // If it's not an empty tile
                    Rectangle2D tileBounds = new Rectangle2D(
                            x * tileSize,
                            y * tileSize,
                            tileSize,
                            tileSize
                    );

                    if (quacky.getBoundingBox().intersects(tileBounds)) {
                        if (tileCode == 100 && !levelCompleted) {
                            // Handle collision with pipe
                            levelComplete();
                            return;
                        } else {
                            handleCollision(quacky, tileBounds);
                        }
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

    private double getBottomBoundary() {
        return mapData.length * spriteSize - quacky.getSprite().getFitHeight();
    }

    private void levelComplete() {
        if(!levelCompleted) {
            levelCompleted = true;
            game.setGameStatus(GameStatus.LEVEL_TRANSITIONING);
            // Any other level completion logic
        }
    }
}