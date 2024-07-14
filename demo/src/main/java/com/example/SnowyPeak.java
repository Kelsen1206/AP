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

public class SnowyPeak extends Level{
    private double spriteSize = 50;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private Sprite tileSprite;
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
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 10, 8, 9, 10, 8, 9, 10, 8, 9, 10, 8, 9, 10, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 8, 0, 9, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 4, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 8, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 4, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9, 1, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 3, 3, 3, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 1, 0, 0, 1, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 4, 10, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 3, 3, 3, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9, 1, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 10, 3, 5, 0, 0, 0, 0, 0, 0, 0, 5, 5, 6, 3, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 21, 0, 16, 0, 1, 0, 0, 0, 1, 6, 5, 0, 0, 0, 0, 0, 0, 1, 4, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 8, 0, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, 9, 3, 0, 0, 22, 3, 8, 0, 0, 0, 11, 11, 0, 0, 0},
                    {0, 0, 0, 0, 0, 17, 0, 0, 0, 1, 3, 3, 10, 3, 3, 3, 8, 18, 19, 20, 0, 0, 5, 0, 0, 3, 6, 5, 5, 5, 5, 5, 5, 5, 0, 0, 5, 5, 6, 3, 3, 0, 0, 0, 0, 0, 14, 15, 0, 0, 16, 0, 0, 21, 0, 22, 0, 21, 0, 25, 0, 17, 0, 0, 0, 1, 5, 5, 0, 0, 0, 0, 0, 0, 10, 4, 2, 3, 0, 0, 0, 0, 0, 0, 14, 15, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 5, 8, 9, 0, 22, 0, 0, 0, 8, 9, 0, 0, 0, 0, 0, 15, 0, 22, 0, 0, 21, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 9, 13, 22, 14, 15, 0, 22, 13, 8, 0, 0, 0, 15, 0, 0, 0},
                    {0, 0, 0, 0, 1, 22, 0, 0, 9, 1, 3, 18, 19, 20, 3, 3, 3, 1, 24, 1, 15, 0, 5, 0, 0, 3, 5, 5, 16, 0, 22, 0, 0, 0, 0, 0, 5, 3, 3, 3, 3, 0, 0, 0, 0, 0, 5, 5, 0, 0, 21, 0, 0, 22, 0, 0, 0, 22, 0, 0, 0, 22, 0, 0, 0, 1, 0, 1, 0, 0, 0, 15, 0, 0, 0, 0, 0, 22, 0, 0, 15, 0, 10, 4, 2, 3, 0, 0, 0, 0, 1, 5, 5, 5, 5, 0, 0, 16, 0, 0, 16, 0, 0, 16, 0, 1, 3, 0, 22, 0, 0, 0, 0, 0, 0, 18, 19, 20, 8, 9, 0, 18, 19, 20, 0, 0, 16, 0, 16, 0, 0, 0, 16, 0, 16, 0, 0, 0, 16, 0, 0, 0, 0, 9, 3, 16, 0, 18, 19, 20, 0, 16, 3, 8, 10, 10, 10, 0, 0, 0},
                    {0, 0, 9, 5, 5, 5, 5, 5, 5, 3, 3, 8, 24, 25, 0, 9, 3, 3, 5, 5, 5, 4, 5, 0, 0, 3, 0, 0, 21, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 5, 5, 5, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 17, 18, 19, 20, 0, 0, 0, 0, 0, 0, 0, 0, 1, 4, 2, 18, 19, 20, 0, 0, 0, 0, 1, 14, 15, 14, 5, 0, 0, 21, 0, 0, 21, 0, 0, 21, 0, 1, 3, 18, 19, 20, 0, 0, 0, 0, 0, 23, 24, 25, 0, 0, 0, 23, 24, 25, 0, 0, 21, 15, 21, 0, 16, 0, 21, 0, 21, 0, 0, 15, 21, 0, 0, 0, 9, 3, 0, 21, 0, 23, 24, 25, 0, 21, 0, 3, 8, 17, 18, 19, 20, 0},
                    {1, 5, 3, 3, 3, 3, 3, 6, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 22, 0, 0, 0, 0, 0, 5, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 22, 23, 24, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 24, 25, 0, 0, 1, 5, 3, 3, 3, 3, 3, 5, 5, 3, 5, 0, 3, 0, 5, 3, 5, 5, 3, 23, 24, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 5, 5, 5, 0, 21, 0, 5, 5, 5, 5, 0, 1, 5, 5, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 22, 23, 24, 25, 0},
            };

    public SnowyPeak(Game game, Stage stage, ImageLoader imageLoader) throws FileNotFoundException {
        super(game, stage, imageLoader);

        this.game = game;
        this.stage = stage;
        this.backgroundImage = new ImageView(imageLoader.loadImage("/images/Snowy Peak.jpg"));
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
                    InputStream tileSpriteStream = getClass().getResourceAsStream("/images/SnowyPeakTileSet.png");
                    tileSprite = new Sprite(tileSpriteStream, 32, 32);

                    switch (tileCode) {
                        case 1:
                            tileView = new ImageView(tileSprite.getTile(0, 0));
                            break;
                        case 2:
                            tileView = new ImageView(tileSprite.getTile(1, 0));
                            break;
                        case 3:
                            tileView = new ImageView(tileSprite.getTile(2, 0));
                            break;
                        case 4:
                            tileView = new ImageView(tileSprite.getTile(3, 0));
                            break;
                        case 5:
                            tileView = new ImageView(tileSprite.getTile(4, 0));
                            break;
                        case 6:
                            tileView = new ImageView(tileSprite.getTile(0, 1));
                            break;
                        case 8:
                            tileView = new ImageView(tileSprite.getTile(2, 1));
                            break;
                        case 9:
                            tileView = new ImageView(tileSprite.getTile(3, 1));
                            break;
                        case 10:
                            tileView = new ImageView(tileSprite.getTile(4, 1));
                            break;
                        case 13:
                            tileView = new ImageView(tileSprite.getTile(2, 2));
                            break;
                        case 14:
                            tileView = new ImageView(tileSprite.getTile(3, 2));
                            break;
                        case 15:
                            tileView = new ImageView(tileSprite.getTile(4, 2));
                            break;
                        case 16:
                            tileView = new ImageView(tileSprite.getTile(0,3));
                            break;
                        case 17:
                            tileView = new ImageView(tileSprite.getTile(1,3));
                            break;
                        case 18:
                            tileView = new ImageView(tileSprite.getTile(2,3));
                            break;
                        case 19:
                            tileView = new ImageView(tileSprite.getTile(3,3));
                            break;
                        case 20:
                            tileView = new ImageView(tileSprite.getTile(4,3));
                            break;
                        case 21:
                            tileView = new ImageView(tileSprite.getTile(0,4));
                            break;
                        case 22:
                            tileView = new ImageView(tileSprite.getTile(1,4));
                            break;
                        case 23:
                            tileView = new ImageView(tileSprite.getTile(2,4));
                            break;
                        case 24:
                            tileView = new ImageView(tileSprite.getTile(3,4));
                            break;
                        case 25:
                            tileView = new ImageView(tileSprite.getTile(4,4));
                            break;
                        case 11:
                            tileView = new ImageView(tileSprite.getTile(0,2));
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
                    } else if (!levelCompleted) {
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
                        if (tileCode == 11 && !levelCompleted) {
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