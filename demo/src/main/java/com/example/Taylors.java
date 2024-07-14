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

public class Taylors extends Level {
    private double spriteSize = 50;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private Sprite bigSprite;
    private Sprite boxSprite;
    private Sprite stoneSprite;
    private Sprite moneySprite;
    private Sprite redTreeSprite;
    private Sprite greenTreeSprite;
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
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 0, 0, 0, 0, 0, 28, 28, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 296, 297, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 84, 36, 121, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 36, 88, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 7, 8, 0, 0, 0, 0, 0, 0, 0, 0, 33, 42, 0, 0, 0, 0, 0, 0, 35, 0, 35, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 298, 299, 0, 0, 0, 0, 0, 0, 0, 36, 0, 36, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 84, 36, 36, 36, 121, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 87, 36, 0, 84, 36, 0, 0, 36, 0, 36, 36, 0, 0, 36, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 15, 16, 17, 0, 0, 0, 33, 34, 0, 0, 0, 0, 0, 43, 34, 0, 0, 0, 35, 45, 35, 45, 35, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 300, 301, 0, 0, 0, 0, 0, 36, 0, 36, 0, 0, 0, 23, 24, 25, 26, 0, 0, 0, 0, 84, 36, 36, 95, 36, 36, 121, 0, 0, 0, 0, 0, 0, 0, 6, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 89, 0, 36, 36, 36, 89, 0, 36, 36, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 0, 0, 0, 1, 34, 0, 33, 2, 3, 34, 0, 33, 2, 34, 0, 33, 2, 2, 34, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 34, 0, 0, 0, 0, 0, 34, 0, 0, 0, 0, 45, 0, 45, 0, 45, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 302, 303, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 36, 36, 36, 36, 36, 36, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 36, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 45, 0, 45, 0, 0, 10, 89, 0, 45, 0, 0, 6, 0, 84, 45, 0, 0, 45, 0, 6, 45, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, 0, 45, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 24, 25, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 45, 0, 0, 45, 0, 0, 10, 45, 0, 0, 0, 0, 45, 0, 45, 0, 0, 0, 0, 0, 45, 0, 89, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 35, 0, 0, 0, 45, 0, 0, 0},
                    {304, 307, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 1, 2, 3, 3, 34, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 4, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 45, 0, 0, 0, 0, 0, 0, 10, 0, 6, 0, 45, 0, 0, 0, 0, 0, 45, 0, 0, 0, 0, 0, 45, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 35, 2, 3, 0, 0, 0, 0, 0, 0, 0},
                    {305, 308, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 10, 11, 12, 12, 43, 34, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 10, 0, 0, 13, 0, 0, 36, 0, 0, 0, 33, 0, 34, 0, 0, 0, 36, 0, 0, 0, 35, 0, 87, 2, 0, 0, 0, 33, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 84, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 45, 0, 0, 0, 0, 0, 0, 0, 10, 0, 45, 0, 0, 45, 0, 6, 0, 0, 0, 0, 7, 45, 0, 6, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 27, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {306, 309, 7, 8, 0, 35, 0, 18, 0, 35, 0, 0, 33, 2, 34, 0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 10, 12, 11, 11, 12, 43, 34, 35, 35, 35, 35, 0, 0, 0, 89, 6, 2, 0, 0, 0, 0, 0, 0, 10, 89, 89, 13, 0, 0, 0, 0, 0, 33, 42, 95, 43, 34, 0, 0, 0, 0, 0, 0, 2, 87, 2, 12, 86, 121, 33, 42, 12, 0, 11, 6, 2, 0, 7, 7, 7, 7, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 45, 0, 33, 34, 0, 0, 0, 0, 0, 10, 8, 0, 0, 89, 89, 0, 45, 0, 45, 0, 0, 45, 95, 0, 45, 8, 13, 0, 0, 9, 0, 0, 0, 9, 0, 0, 27, 0, 0, 0, 0, 171, 172, 173, 174, 175, 176, 177, 172, 173, 174, 175, 176, 177},
                    {14, 15, 16, 17, 0, 44, 0, 27, 0, 44, 0, 33, 42, 12, 43, 34, 0, 36, 36, 36, 89, 89, 89, 89, 89, 89, 89, 10, 11, 11, 12, 11, 11, 43, 2, 2, 2, 2, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 19, 20, 21, 22, 0, 0, 0, 0, 33, 42, 12, 12, 12, 43, 34, 0, 0, 0, 2, 2, 12, 2, 12, 12, 2, 3, 42, 12, 11, 0, 11, 2, 11, 0, 2, 2, 2, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 45, 0, 33, 42, 43, 34, 0, 0, 0, 0, 19, 20, 21, 20, 21, 20, 21, 20, 21, 20, 20, 20, 20, 20, 20, 20, 21, 22, 0, 36, 27, 36, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 3, 2, 3, 3, 2}
            };


    public Taylors(Game game, Stage stage, ImageLoader imageLoader) throws FileNotFoundException {
        super(game, stage, imageLoader);

        this.game = game;
        this.stage = stage;
        this.backgroundImage = new ImageView(imageLoader.loadImage("/images/Taylors.png"));
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
                ImageView tileView = null;
                try {
                    InputStream bigSpriteStream = getClass().getResourceAsStream("/images/TaylorsTileSet.png");
                    bigSprite = new Sprite(bigSpriteStream, 32, 32);
                    InputStream greenTreeSpriteStream = getClass().getResourceAsStream("/images/TaylorsTileSet.png");
                    greenTreeSprite = new Sprite(greenTreeSpriteStream, 32, 32);
                    InputStream redTreeSpriteStream = getClass().getResourceAsStream("/images/TaylorsTileSet.png");
                    redTreeSprite = new Sprite(redTreeSpriteStream, 32, 32);
                    InputStream boxSpriteStream = getClass().getResourceAsStream("/images/TaylorsTileSet.png");
                    boxSprite = new Sprite(boxSpriteStream, 32, 32);
                    InputStream stoneSpriteStream = getClass().getResourceAsStream("/images/TaylorsTileSet.png");
                    stoneSprite = new Sprite(stoneSpriteStream, 32, 32);
                    InputStream moneySpriteStream = getClass().getResourceAsStream("/images/TaylorsTileSet.png");
                    moneySprite = new Sprite(moneySpriteStream, 32, 32);

                    switch (tileCode) {
                        case 1:
                            tileView = new ImageView(bigSprite.getTile(0, 0));
                            break;
                        case 2:
                            tileView = new ImageView(bigSprite.getTile(1,0));
                            break;
                        case 3:
                            tileView = new ImageView(bigSprite.getTile(2,0));
                            break;
                        case 4:
                            tileView = new ImageView(bigSprite.getTile(3,0));
                            break;
                        case 5:
                            tileView = new ImageView(bigSprite.getTile(4,0));
                            break;
                        case 6:
                            tileView = new ImageView(bigSprite.getTile(5,0));
                            break;
                        case 7:
                            tileView = new ImageView(bigSprite.getTile(6,0));
                            break;
                        case 8:
                            tileView = new ImageView(bigSprite.getTile(7,0));
                            break;
                        case 9:
                            tileView = new ImageView(bigSprite.getTile(8,0));
                            break;
                        case 10:
                            tileView = new ImageView(bigSprite.getTile(0,1));
                            break;
                        case 11:
                            tileView = new ImageView(bigSprite.getTile(1,1));
                            break;
                        case 12:
                            tileView = new ImageView(bigSprite.getTile(2,1));
                            break;
                        case 13:
                            tileView = new ImageView(bigSprite.getTile(3,1));
                            break;
                        case 14:
                            tileView = new ImageView(bigSprite.getTile(4,1));
                            break;
                        case 15:
                            tileView = new ImageView(bigSprite.getTile(5,1));
                            break;
                        case 16:
                            tileView = new ImageView(bigSprite.getTile(6,1));
                            break;
                        case 17:
                            tileView = new ImageView(bigSprite.getTile(7,1));
                            break;
                        case 18:
                            tileView = new ImageView(bigSprite.getTile(8,1));
                            break;
                        case 19:
                            tileView = new ImageView(bigSprite.getTile(0,2));
                            break;
                        case 20:
                            tileView = new ImageView(bigSprite.getTile(1,2));
                            break;
                        case 21:
                            tileView = new ImageView(bigSprite.getTile(2,2));
                            break;
                        case 22:
                            tileView = new ImageView(bigSprite.getTile(3,2));
                            break;
                        case 23:
                            tileView = new ImageView(bigSprite.getTile(4,2));
                            break;
                        case 24:
                            tileView = new ImageView(bigSprite.getTile(5,2));
                            break;
                        case 25:
                            tileView = new ImageView(bigSprite.getTile(6,2));
                            break;
                        case 26:
                            tileView = new ImageView(bigSprite.getTile(7,2));
                            break;
                        case 27:
                            tileView = new ImageView(bigSprite.getTile(8,2));
                            break;
                        case 33:
                            tileView = new ImageView(bigSprite.getTile(5, 3));
                            break;
                        case 34:
                            tileView = new ImageView(bigSprite.getTile(6, 3));
                            break;
                        case 35:
                            tileView = new ImageView(bigSprite.getTile(7, 3));
                            break;
                        case 36:
                            tileView = new ImageView(bigSprite.getTile(8, 3));
                            break;
                        case 42:
                            tileView = new ImageView(bigSprite.getTile(5,4));
                            break;
                        case 43:
                            tileView = new ImageView(bigSprite.getTile(6,4));
                            break;
                        case 44:
                            tileView = new ImageView(bigSprite.getTile(7,4));
                            break;
                        case 45:
                            tileView = new ImageView(bigSprite.getTile(8,4));
                            break;
                        case 95:
                            tileView = new ImageView(boxSprite.getTile(0,0));
                            break;
                        case 89:
                        case 84:
                        case 121:
                        case 88:
                        case 87:
                            tileView = new ImageView(stoneSprite.getTile(0,0));
                            break;
                        case 171:
                            tileView = new ImageView(moneySprite.getTile(0,0));
                            break;
                        case 304:
                            tileView = new ImageView(greenTreeSprite.getTile(0,0));
                            break;
                        case 305:
                            tileView = new ImageView(greenTreeSprite.getTile(0,1));
                            break;
                        case 306:
                            tileView = new ImageView(greenTreeSprite.getTile(0,2));
                            break;
                        case 307:
                            tileView = new ImageView(greenTreeSprite.getTile(1,0));
                            break;
                        case 308:
                            tileView = new ImageView(greenTreeSprite.getTile(1,1));
                            break;
                        case 309:
                            tileView = new ImageView(greenTreeSprite.getTile(1,2));
                            break;
                        case 296:
                            tileView = new ImageView(redTreeSprite.getTile(0,0));
                            break;
                        case 297:
                            tileView = new ImageView(redTreeSprite.getTile(1,0));
                            break;
                        case 298:
                            tileView = new ImageView(redTreeSprite.getTile(0,1));
                            break;
                        case 299:
                            tileView = new ImageView(redTreeSprite.getTile(1,1));
                            break;
                        case 300:
                            tileView = new ImageView(redTreeSprite.getTile(0,2));
                            break;
                        case 301:
                            tileView = new ImageView(redTreeSprite.getTile(1,2));
                            break;
                        case 302:
                            tileView = new ImageView(redTreeSprite.getTile(0,3));
                            break;
                        case 303:
                            tileView = new ImageView(redTreeSprite.getTile(1,3));
                            break;
                        case 28:
                            tileView = new ImageView(bigSprite.getTile(1,7));
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
                        checkCollisions();
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

    private void checkCollisions() {
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
                        if (tileCode == 28 && !levelCompleted) {
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