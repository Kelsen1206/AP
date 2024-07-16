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

public class CrystalHallows extends Level{
    private double spriteSize = 50;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private Sprite brownSprite;
    private Sprite graySprite;
    private Sprite objectSprite;
    private Sprite CrystalShardSprite;
    private Pane gameWorld;
    private Quacky quacky;
    private final double viewWidth = 400;
    private Camera camera;
    private static final long FRAME_TIME = 8_333_333;
    private int initialX = 100;
    private int initialY = 300;
    private SoundManager soundManager;
    private boolean gameOver;
    private Text gameOverText;
    private InputHandler inputHandler;
    private boolean levelCompleted = false;
    int[][] mapData =
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {377, 369, 316, 373, 373, 373, 369, 393, 377, 408, 408, 377, 393, 393, 432, 393,377, 377, 393, 317, 132, 393, 393, 432, 104, 393, 373, 319, 319, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 315, 109, 111, 112, 109, 111, 112,373, 132, 319, 132, 319, 319, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 911, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {385, 369, 373, 369, 369, 373, 385, 317, 0, 442, 0, 0, 0, 0, 432, 411,440, 442, 0, 0, 0, 0, 0, 103, 104, 132, 373, 374, 374, 316, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 121, 123, 124, 121, 123, 124,373, 357, 357, 357, 357, 357, 316, 0, 0, 0, 0, 0, 244, 197, 197, 197,129, 0, 129, 177, 177, 177, 140, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 205, 205, 205, 173, 193, 177, 140, 139, 193, 194, 194, 197, 229,193, 193, 193, 197, 193, 193, 193, 197, 193, 197, 193, 84, 193, 193, 177, 177,197, 177, 177, 177, 177, 177, 177, 177, 0, 84, 84, 84, 84, 84, 84, 84,84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 0, 0, 87, 88, 132, 129,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 91, 92, 0, 0, 0},
                    {0, 377, 0, 369, 0, 385, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 236, 0, 0, 0, 103, 104, 0, 389, 434, 432, 0, 0, 0,0, 0, 143, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 315,389, 434, 0, 434, 433, 434, 0, 0, 0, 0, 0, 0, 261, 213, 213, 213,213, 213, 213, 213, 256, 193, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 256, 189, 197, 205, 0, 256, 209, 0, 197, 213, 256,253, 209, 193, 193, 193, 197, 193, 193, 193, 193, 193, 98, 130, 131, 256, 141,256, 107, 108, 256, 110, 111, 112, 197, 194, 86, 100, 129, 89, 97, 84, 84,101, 100, 129, 130, 99, 86, 100, 113, 89, 102, 84, 84, 99, 100, 89, 90,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 103, 104, 0, 0, 0},
                    {315, 369, 316, 369, 0, 0, 0, 0, 0, 0, 0, 241, 234, 0, 0, 0,0, 0, 0, 181, 143, 0, 0, 103, 116, 0, 0, 0, 0, 0, 0, 0,0, 0, 185, 0, 167, 185, 141, 0, 143, 143, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 209, 262, 0, 0, 0, 237, 238, 0, 237, 238, 0,0, 0, 0, 0, 0, 0, 205, 205, 0, 0, 0, 0, 0, 213, 0, 0,0, 0, 256, 209, 0, 0, 0, 0, 0, 0, 253, 205, 193, 193, 143, 0,256, 119, 120, 256, 122, 123, 124, 193, 0, 131, 0, 111, 112, 97, 0, 101,130, 131, 0, 0, 99, 0, 129, 0, 130, 131, 0, 132, 102, 88, 101, 102,141, 111, 112, 0, 0, 0, 0, 0, 0, 0, 0, 103, 104, 0, 0, 0},
                    {0, 0, 0, 385, 0, 0, 0, 0, 177, 0, 0, 181, 181, 234, 143, 142,0, 0, 0, 181, 143, 0, 0, 103, 116, 0, 0, 0, 0, 0, 0, 0,107, 108, 201, 0, 0, 193, 185, 185, 193, 185, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 237, 238, 0, 253, 254, 0, 253, 254, 0,0, 0, 0, 91, 92, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 425, 408, 408, 426, 0, 0, 0, 0, 252, 193, 256,108, 109, 256, 111, 112, 143, 197, 209, 0, 0, 0, 123, 124, 101, 111, 112,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 130, 101, 87, 88,0, 123, 124, 0, 0, 0, 0, 0, 0, 0, 0, 103, 104, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 197, 0, 0, 256, 193, 197, 193, 205,0, 0, 197, 197, 141, 0, 0, 115, 0, 0, 0, 0, 0, 94, 82, 93,91, 104, 217, 0, 0, 0, 0, 185, 185, 185, 185, 141, 0, 0, 0, 0,0, 0, 0, 0, 244, 0, 0, 0, 0, 0, 0, 0, 0, 0, 144, 0,0, 361, 353, 316, 0, 0, 0, 253, 254, 0, 0, 0, 0, 0, 0, 0,237, 238, 0, 103, 104, 0, 0, 0, 0, 0, 0, 0, 0, 91, 92, 0,0, 0, 0, 0, 425, 361, 353, 369, 357, 0, 0, 0, 0, 256, 197, 256,120, 121, 256, 123, 124, 193, 209, 0, 0, 0, 0, 0, 0, 132, 123, 124,0, 0, 0, 0, 0, 0, 86, 86, 86, 89, 90, 0, 0, 101, 99, 100,141, 0, 0, 0, 0, 0, 0, 0, 143, 0, 0, 103, 104, 0, 0, 0},
                    {0, 0, 0, 0, 0, 318, 177, 0, 197, 141, 0, 256, 197, 197, 197, 140,0, 139, 197, 193, 0, 0, 0, 0, 0, 0, 0, 109, 0, 91, 92, 105,103, 104, 0, 0, 0, 0, 0, 0, 217, 0, 217, 0, 0, 0, 0, 0,0, 0, 244, 143, 260, 71, 0, 113, 0, 319, 0, 319, 0, 0, 177, 143,0, 361, 353, 316, 0, 0, 0, 253, 254, 0, 0, 0, 0, 0, 0, 0,253, 254, 0, 103, 104, 0, 0, 0, 91, 92, 0, 0, 0, 103, 104, 0,0, 0, 0, 425, 368, 132, 93, 385, 385, 357, 0, 0, 0, 0, 0, 209,209, 209, 209, 209, 209, 209, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 101, 86, 89, 98, 86, 102, 86, 97, 100, 131, 0, 0, 101, 84, 84,88, 88, 90, 0, 0, 0, 143, 0, 185, 0, 0, 103, 104, 0, 0, 0},
                    {0, 0, 0, 318, 0, 173, 177, 177, 177, 0, 0, 256, 213, 213, 213, 0,0, 0, 213, 0, 0, 0, 0, 0, 0, 0, 0, 91, 92, 103, 104, 0,115, 116, 85, 86, 86, 86, 0, 0, 0, 0, 0, 0, 84, 84, 0, 0,0, 0, 181, 177, 181, 181, 181, 125, 0, 91, 92, 91, 92, 0, 193, 181,0, 0, 0, 0, 0, 385, 385, 0, 0, 315, 353, 353, 0, 143, 0, 0,0, 0, 0, 103, 104, 91, 92, 0, 103, 104, 91, 92, 0, 103, 104, 0,0, 0, 425, 368, 91, 92, 94, 94, 91, 92, 357, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 319, 319, 0, 84,84, 86, 131, 84, 84, 100, 84, 84, 84, 113, 132, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 143, 181, 201, 201, 0, 0, 185, 185, 0, 0, 0},
                    {0, 318, 232, 177, 232, 193, 209, 84, 209, 91, 92, 0, 0, 83, 84, 90,0, 0, 0, 0, 0, 0, 0, 185, 185, 140, 0, 103, 104, 0, 0, 0,0, 0, 97, 130, 131, 97, 131, 0, 0, 0, 0, 84, 131, 0, 0, 0,91, 92, 197, 189, 193, 197, 193, 143, 0, 103, 104, 103, 104, 0, 193, 197,143, 144, 0, 0, 0, 0, 0, 0, 0, 0, 385, 385, 0, 353, 361, 317,0, 0, 0, 103, 104, 103, 104, 0, 103, 104, 103, 104, 0, 103, 104, 0,0, 425, 368, 0, 103, 104, 106, 106, 103, 104, 132, 357, 319, 0, 88, 88,0, 76, 86, 0, 75, 86, 0, 86, 86, 0, 0, 319, 349, 369, 349, 369,84, 84, 84, 113, 89, 90, 130, 83, 84, 125, 86, 113, 98, 86, 0, 0,0, 0, 0, 0, 143, 197, 193, 0, 0, 143, 143, 201, 201, 0, 0, 0},
                    {192, 192, 192, 193, 194, 209, 95, 96, 0, 103, 104, 0, 0, 95, 96, 102,0, 0, 0, 0, 0, 0, 200, 217, 0, 0, 0, 115, 116, 0, 0, 0,0, 0, 97, 86, 86, 98, 84, 84, 0, 84, 0, 0, 0, 0, 0, 0,173, 173, 193, 197, 193, 201, 189, 177, 177, 177, 197, 177, 173, 197, 173, 181,181, 224, 181, 0, 224, 224, 224, 224, 0, 224, 224, 0, 0, 385, 385, 0,0, 0, 0, 349, 361, 357, 349, 0, 349, 361, 349, 349, 0, 357, 349, 0,0, 361, 349, 349, 349, 361, 349, 349, 361, 357, 357, 349, 349, 0, 103, 104,0, 103, 104, 0, 103, 104, 0, 103, 104, 0, 0, 361, 349, 357, 361, 369,0, 0, 0, 125, 101, 102, 0, 95, 96, 0, 0, 125, 84, 100, 84, 84,0, 88, 90, 213, 213, 213, 0, 0, 0, 217, 217, 217, 217, 202, 202, 185},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            };



    public CrystalHallows(Game game, Stage stage, ImageLoader imageLoader) throws FileNotFoundException {
        super(game, stage, imageLoader);

        this.game = game;
        this.stage = stage;
        this.backgroundImage = new ImageView(imageLoader.loadImage("/images/Crystal Hallows.jpg"));
        this.gameWorld = new Pane();
        this.gamePane = new GamePane(backgroundImage.getImage());
        this.scene = new Scene(gamePane, 1000, 650);
        this.camera = new Camera();

        this.soundManager = SoundManager.getInstance();
        soundManager.loadSounds();
        soundManager.playBackgroundMusic("Crystal");

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
                    InputStream brownSpriteStream = getClass().getResourceAsStream("/images/CrystalBrownTileSet.png");
                    brownSprite = new Sprite(brownSpriteStream, 32, 32);
                    InputStream graySpriteStream = getClass().getResourceAsStream("/images/CrystalGrayTileSet.png");
                    graySprite = new Sprite(graySpriteStream, 32, 32);
                    InputStream objectSpriteStream = getClass().getResourceAsStream("/images/CrystalObjectTileSet.png");
                    objectSprite = new Sprite(objectSpriteStream, 32, 32);
                    InputStream CrystalShardSpriteStream = getClass().getResourceAsStream("/images/Crystal_Shard.png");
                    CrystalShardSprite = new Sprite(CrystalShardSpriteStream,105,106);

                    switch (tileCode) {
                        case 315:
                            tileView = new ImageView(brownSprite.getTile(0, 0));
                            break;
                        case 316:
                            tileView = new ImageView(brownSprite.getTile(1,0));
                            break;
                        case 317:
                            tileView = new ImageView(brownSprite.getTile(2,0));
                            break;
                        case 319:
                            tileView = new ImageView(brownSprite.getTile(4,0));
                            break;
                        case 349:
                            tileView = new ImageView(brownSprite.getTile(2,2));
                            break;
                        case 353:
                            tileView = new ImageView(brownSprite.getTile(6,2));
                            break;
                        case 357:
                            tileView = new ImageView(brownSprite.getTile(10,2));
                            break;
                        case 361:
                            tileView = new ImageView(brownSprite.getTile(14,2));
                            break;
                        case 368:
                            tileView = new ImageView(brownSprite.getTile(5,3));
                            break;
                        case 369:
                            tileView = new ImageView(brownSprite.getTile(6,3));
                            break;
                        case 373:
                            tileView = new ImageView(brownSprite.getTile(10,3));
                            break;
                        case 374:
                            tileView = new ImageView(brownSprite.getTile(11,3));
                            break;
                        case 377:
                            tileView = new ImageView(brownSprite.getTile(14,3));
                            break;
                        case 385:
                            tileView = new ImageView(brownSprite.getTile(6,4));
                            break;
                        case 389:
                            tileView = new ImageView(brownSprite.getTile(10,4));
                            break;
                        case 393:
                            tileView = new ImageView(brownSprite.getTile(14,4));
                            break;
                        case 408:
                            tileView = new ImageView(brownSprite.getTile(13,5));
                            break;
                        case 425:
                            tileView = new ImageView(brownSprite.getTile(14,6));
                            break;
                        case 426:
                            tileView = new ImageView(brownSprite.getTile(15,6));
                            break;
                        case 432:
                            tileView = new ImageView(brownSprite.getTile(5,7));
                            break;
                        case 433:
                            tileView = new ImageView(brownSprite.getTile(6,7));
                            break;
                        case 434:
                            tileView = new ImageView(brownSprite.getTile(7,7));
                            break;
                        case 440:
                            tileView = new ImageView(brownSprite.getTile(13,7));
                            break;
                        case 442:
                            tileView = new ImageView(brownSprite.getTile(15,7));
                            break;
                        case 71:
                            tileView = new ImageView(objectSprite.getTile(4,0));
                            break;
                        case 82:
                            tileView = new ImageView(objectSprite.getTile(3,1));
                            break;
                        case 83:
                        case 93:
                            tileView = new ImageView(objectSprite.getTile(4,1));
                            break;
                        case 84:
                            tileView = new ImageView(objectSprite.getTile(5,1));
                            break;
                        case 85:
                            tileView = new ImageView(objectSprite.getTile(6,1));
                            break;
                        case 86:
                            tileView = new ImageView(objectSprite.getTile(7,1));
                            break;
                        case 87:
                            tileView = new ImageView(objectSprite.getTile(8,1));
                            break;
                        case 88:
                            tileView = new ImageView(objectSprite.getTile(9,1));
                            break;
                        case 89:
                            tileView = new ImageView(objectSprite.getTile(10,1));
                            break;
                        case 90:
                            tileView = new ImageView(objectSprite.getTile(11,1));
                            break;
                        case 91:
                            tileView = new ImageView(objectSprite.getTile(0,2));
                            break;
                        case 92:
                            tileView = new ImageView(objectSprite.getTile(1,2));
                            break;
                        case 94:
                            tileView = new ImageView(objectSprite.getTile(3,2));
                            break;
                        case 95:
                            tileView = new ImageView(objectSprite.getTile(4,2));
                            break;
                        case 96:
                            tileView = new ImageView(objectSprite.getTile(5,2));
                            break;
                        case 97:
                            tileView = new ImageView(objectSprite.getTile(6,2));
                            break;
                        case 98:
                            tileView = new ImageView(objectSprite.getTile(7,2));
                            break;
                        case 99:
                            tileView = new ImageView(objectSprite.getTile(8,2));
                            break;
                        case 100:
                            tileView = new ImageView(objectSprite.getTile(9,2));
                            break;
                        case 101:
                            tileView = new ImageView(objectSprite.getTile(10,2));
                            break;
                        case 102:
                            tileView = new ImageView(objectSprite.getTile(11,2));
                            break;
                        case 103:
                            tileView = new ImageView(objectSprite.getTile(0,3));
                            break;
                        case 104:
                            tileView = new ImageView(objectSprite.getTile(1,3));
                            break;
                        case 105:
                            tileView = new ImageView(objectSprite.getTile(2,3));
                            break;
                        case 106:
                            tileView = new ImageView(objectSprite.getTile(3,3));
                            break;
                        case 107:
                            tileView = new ImageView(objectSprite.getTile(4,3));
                            break;
                        case 108:
                            tileView = new ImageView(objectSprite.getTile(5,3));
                            break;
                        case 109:
                            tileView = new ImageView(objectSprite.getTile(6,3));
                            break;
                        case 110:
                            tileView = new ImageView(objectSprite.getTile(7,3));
                            break;
                        case 111:
                            tileView = new ImageView(objectSprite.getTile(8,3));
                            break;
                        case 112:
                            tileView = new ImageView(objectSprite.getTile(9,3));
                            break;
                        case 113:
                            tileView = new ImageView(objectSprite.getTile(10,3));
                            break;
                        case 115:
                            tileView = new ImageView(objectSprite.getTile(0,4));
                            break;
                        case 116:
                            tileView = new ImageView(objectSprite.getTile(1,4));
                            break;
                        case 119:
                            tileView = new ImageView(objectSprite.getTile(4,4));
                            break;
                        case 120:
                            tileView = new ImageView(objectSprite.getTile(5,4));
                            break;
                        case 121:
                            tileView = new ImageView(objectSprite.getTile(6,4));
                            break;
                        case 122:
                            tileView = new ImageView(objectSprite.getTile(7,4));
                            break;
                        case 123:
                            tileView = new ImageView(objectSprite.getTile(8,4));
                            break;
                        case 124:
                            tileView = new ImageView(objectSprite.getTile(9,4));
                            break;
                        case 125:
                            tileView = new ImageView(objectSprite.getTile(10,4));
                            break;
                        case 129:
                            tileView = new ImageView(objectSprite.getTile(2,5));
                            break;
                        case 130:
                            tileView = new ImageView(objectSprite.getTile(3,5));
                            break;
                        case 131:
                            tileView = new ImageView(objectSprite.getTile(4,5));
                            break;
                        case 132:
                            tileView = new ImageView(objectSprite.getTile(5,5));
                            break;
                        case 139:
                            tileView = new ImageView(graySprite.getTile(0,0));
                            break;
                        case 140:
                            tileView = new ImageView(graySprite.getTile(1,0));
                            break;
                        case 141:
                            tileView = new ImageView(graySprite.getTile(2,0));
                            break;
                        case 142:
                            tileView = new ImageView(graySprite.getTile(3,0));
                            break;
                        case 143:
                            tileView = new ImageView(graySprite.getTile(4,0));
                            break;
                        case 167:
                            tileView = new ImageView(graySprite.getTile(12,1));
                            break;
                        case 173:
                            tileView = new ImageView(graySprite.getTile(2,2));
                            break;
                        case 177:
                            tileView = new ImageView(graySprite.getTile(6,2));
                            break;
                        case 181:
                            tileView = new ImageView(graySprite.getTile(10,2));
                            break;
                        case 185:
                            tileView = new ImageView(graySprite.getTile(14,2));
                            break;
                        case 189:
                            tileView = new ImageView(graySprite.getTile(2,3));
                            break;
                        case 192:
                            tileView = new ImageView(graySprite.getTile(5,3));
                            break;
                        case 193:
                            tileView = new ImageView(graySprite.getTile(6,3));
                            break;
                        case 194:
                            tileView = new ImageView(graySprite.getTile(7,3));
                            break;
                        case 197:
                            tileView = new ImageView(graySprite.getTile(10,3));
                            break;
                        case 200:
                            tileView = new ImageView(graySprite.getTile(13,3));
                            break;
                        case 201:
                            tileView = new ImageView(graySprite.getTile(14,3));
                            break;
                        case 202:
                            tileView = new ImageView(graySprite.getTile(15,3));
                            break;
                        case 205:
                            tileView = new ImageView(graySprite.getTile(2,4));
                            break;
                        case 209:
                            tileView = new ImageView(graySprite.getTile(6,4));
                            break;
                        case 213:
                            tileView = new ImageView(graySprite.getTile(10,4));
                            break;
                        case 217:
                            tileView = new ImageView(graySprite.getTile(14,4));
                            break;
                        case 224:
                            tileView = new ImageView(graySprite.getTile(5,5));
                            break;
                        case 229:
                            tileView = new ImageView(graySprite.getTile(10,5));
                            break;
                        case 232:
                            tileView = new ImageView(graySprite.getTile(13,5));
                            break;
                        case 234:
                            tileView = new ImageView(graySprite.getTile(15,5));
                            break;
                        case 236:
                            tileView = new ImageView(graySprite.getTile(1,6));
                            break;
                        case 237:
                            tileView = new ImageView(graySprite.getTile(2,6));
                            break;
                        case 238:
                            tileView = new ImageView(graySprite.getTile(3,6));
                            break;
                        case 241:
                            tileView = new ImageView(graySprite.getTile(6,6));
                            break;
                        case 244:
                            tileView = new ImageView(graySprite.getTile(9,6));
                            break;
                        case 252:
                            tileView = new ImageView(graySprite.getTile(1,7));
                            break;
                        case 253:
                            tileView = new ImageView(graySprite.getTile(2,7));
                            break;
                        case 254:
                            tileView = new ImageView(graySprite.getTile(3,7));
                            break;
                        case 256:
                            tileView = new ImageView(graySprite.getTile(5,7));
                            break;
                        case 260:
                            tileView = new ImageView(graySprite.getTile(9,7));
                            break;
                        case 261:
                            tileView = new ImageView(graySprite.getTile(10,7));
                            break;
                        case 262:
                            tileView = new ImageView(graySprite.getTile(11,7));
                            break;
                        case 117:
                            tileView = new ImageView(objectSprite.getTile(2, 4));
                            break;
                        case 911:
                            tileView = new ImageView(CrystalShardSprite.getTile(0,0));
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
                        if (tileCode == 911 && !levelCompleted) {
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