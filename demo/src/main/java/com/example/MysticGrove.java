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

public class MysticGrove {
    private double spriteSize = 50;
    private ImageLoader imageLoader;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private Sprite bigSprite;
    private Sprite grassSprite;
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
            {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,6,6,6,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,34,35,37,0,0,0,0,1,0,0,5,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,6,6,8,0,0,0,0,0,5,6,6,6,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,6,6,6,6,8,0,0,0,0,0,0,0,0,0,5,8,0,34,37,0,5,6,6,6,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34,37,0,0,0,0,0,0,34,35,35,35,37,0,0,0,0,0,24,0,0,0,0,5,6,7,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,31,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34,37,0,0,0,0,34,35,35,35,37,0,0,5,6,6,6,6,6,8,0,24,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,6,6,8,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,5,6,8,0,0,0,0,0,0,0,0,0,5,8,0,0,0,0,0,0,0,0,0,0,0,1,2,0,0,0,0,0,0,0,5,6,8,0,0,0,0,0,0,0,31,0,0,0,0,0,2,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,6,6,8,0,0,0,0,0,0,0,0,1,1,0,5,6,6,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34,35,35,35,35,17,30,6,6,6,10,0,0,0,0,0,0,0,5,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24,0,0,0,5,6,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,34,37,0,0,26,0,0,0,0,0,0,0,5,6,6,8,0,0,0,0,31,0,34,35,37,0,0,24,0,0,31,0,0,32,41,44,5,6,6,6,8},
                    {0,0,0,0,0,0,0,71,0,0,0,0,0,0,0,0,34,37,0,0,0,0,0,0,0,0,0,5,8,0,0,34,37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34,35,35,35,35,37,0,0,0,0,0,0,0,27,30,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,32,14,6,6,6,8,0,0,0,0,0,0,5,6,10,0,0,0,0,0,24,0,73,33,0,5,8,0,0,0,0,5,8,0,0,0,0,0,0,0,34,35,35,37,0,0,0,0,0,0,0,0,0,0,0,5,8,0,0,0,0,48,49,50,34,35,35,17,30},
                    {0,0,0,0,0,0,78,79,0,0,2,0,5,8,0,0,0,0,0,0,0,0,0,2,5,58,0,34,37,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,8,0,27,30,0,0,0,0,2,5,7,7,8,0,0,0,0,0,3,0,73,41,42,43,44,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,6,8,78,79,0,34,37,0,0,0,0,27,30,1,1,0,0,0,0,0,0,0,0,0,0,0,25,2,0,0,0,0,33,0,0,34,37,0,0,0,60,61,62,63,0,0,0,27,30},
                    {0,0,0,0,0,0,0,84,0,5,8,0,27,30,0,0,0,0,0,0,0,5,6,6,35,37,0,0,0,0,0,0,1,5,6,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34,37,0,27,30,0,0,0,5,6,27,17,17,30,3,3,0,0,5,8,0,85,47,48,49,50,0,73,81,82,0,0,0,0,24,3,0,0,0,0,0,0,27,28,30,1,84,0,0,0,0,0,0,0,34,35,6,31,0,0,1,8,8,0,0,0,0,0,5,6,6,8,0,0,81,50,0,0,0,0,0,0,0,0,72,49,50,0,0,0,27,30},
                    {0,5,8,3,0,0,5,8,0,27,30,0,27,30,73,71,0,31,0,3,2,0,0,0,0,0,0,0,0,0,0,5,6,27,17,30,0,0,31,0,0,1,24,0,0,0,0,2,0,0,0,0,0,5,6,6,6,6,8,0,5,8,0,0,0,0,27,30,0,0,0,27,17,17,17,17,17,7,10,0,0,27,30,0,0,60,61,62,63,0,85,86,87,0,0,6,6,6,6,10,0,0,0,5,6,27,17,30,7,8,0,0,0,0,0,0,0,0,0,0,0,0,5,6,17,30,0,0,0,0,0,0,34,37,0,0,85,86,87,73,71,0,0,0,0,0,60,61,0,0,0,0,0,27,30},
                    {0,27,17,7,10,0,27,30,0,27,30,0,27,30,78,79,0,45,7,7,7,7,7,8,0,0,0,0,0,0,0,27,17,17,17,30,0,0,45,6,8,10,6,6,10,0,5,6,6,8,8,0,0,34,35,35,35,35,35,0,34,37,0,0,0,0,65,30,0,0,0,66,67,67,67,67,67,67,37,0,0,27,30,0,0,0,72,0,0,0,0,88,0,0,34,35,35,35,35,35,10,0,0,34,35,35,35,35,35,37,0,0,0,0,0,0,0,0,0,0,0,0,27,28,28,30,0,0,0,0,0,0,0,0,0,0,0,88,0,78,79,0,0,0,0,0,0,72,0,0,0,0,0,27,30},
                    {0,20,35,68,70,0,34,37,0,34,37,0,34,30,0,84,0,34,35,35,35,35,35,37,0,0,0,0,5,6,6,34,35,35,35,37,0,0,34,35,35,35,35,35,37,0,34,35,35,35,37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34,37,0,0,0,0,0,0,0,0,0,0,0,0,0,34,37,0,0,0,72,3,3,3,2,88,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,34,35,35,37,0,0,0,0,0,0,0,0,0,0,0,88,0,0,84,0,3,3,3,3,3,72,0,0,3,3,3,34,37}
            };



    public MysticGrove(Game game, Stage stage, ImageLoader imageLoader) throws FileNotFoundException {
        this.game = game;
        this.stage = stage;
        this.imageLoader = imageLoader;

        this.backgroundImage = new ImageView(imageLoader.loadImage("/images/Mystic Grove.jpg"));
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
                    InputStream bigSpriteStream = getClass().getResourceAsStream("/images/MysticGroveTileSet.png");
                    bigSprite = new Sprite(bigSpriteStream, 41, 39);
                    InputStream grassSpriteStream = getClass().getResourceAsStream("/images/MysticGroveGrassTileSet.png");
                    grassSprite = new Sprite(grassSpriteStream,38, 24);

                    switch (tileCode) {
                        case 1:
                            tileView = new ImageView(grassSprite.getTile(0,0));
                            break;
                        case 2:
                            tileView = new ImageView(grassSprite.getTile(1, 0));
                            break;
                        case 3:
                            tileView = new ImageView(grassSprite.getTile(2,0));
                            break;
                        case 5:
                            tileView = new ImageView(bigSprite.getTile(0,0));
                            break;
                        case 6:
                        case 7:
                        case 31:
                            tileView = new ImageView(bigSprite.getTile(1,0));
                            break;
                        case 8:
                        case 10:
                            tileView = new ImageView(bigSprite.getTile(3,0));
                            break;
                        case 27:
                            tileView = new ImageView(bigSprite.getTile(0,1));
                            break;
                        case 17:
                            tileView = new ImageView(bigSprite.getTile(1,1));
                            break;
                        case 30:
                            tileView = new ImageView(bigSprite.getTile(3,1));
                            break;
                        case 20:
                        case 34:
                            tileView = new ImageView(bigSprite.getTile(5,1));
                            break;
                        case 70:
                        case 37:
                            tileView = new ImageView(bigSprite.getTile(6,1));
                            break;
                        case 35:
                            tileView = new ImageView(bigSprite.getTile(1,3));
                            break;
                        case 36:
                            tileView = new ImageView(bigSprite.getTile(2,3));
                            break;
                        case 45:
                            tileView = new ImageView(bigSprite.getTile(8,3));
                            break;
                        case 23:
                            tileView = new ImageView(bigSprite.getTile(10,1));
                            break;
                        case 24:
                            tileView = new ImageView(bigSprite.getTile(11,1));
                            break;
                        case 25:
                            tileView = new ImageView(bigSprite.getTile(12,1));
                            break;
                        case 26:
                            tileView = new ImageView(bigSprite.getTile(13,1));
                            break;
                        case 32:
                            tileView = new ImageView(bigSprite.getTile(11,3));
                            break;
                        case 33:
                            tileView = new ImageView(bigSprite.getTile(12,3));
                            break;
                        case 41:
                            tileView = new ImageView(bigSprite.getTile(10,3));
                            break;
                        case 42:
                            tileView = new ImageView(bigSprite.getTile(11,3));
                            break;
                        case 43:
                            tileView = new ImageView(bigSprite.getTile(12,3));
                            break;
                        case 44:
                            tileView = new ImageView(bigSprite.getTile(13,3));
                            break;
                        case 46:
                            tileView = new ImageView(bigSprite.getTile(9,4));
                            break;
                        case 47:
                            tileView = new ImageView(bigSprite.getTile(10,4));
                            break;
                        case 48:
                            tileView = new ImageView(bigSprite.getTile(11,4));
                            break;
                        case 49:
                            tileView = new ImageView(bigSprite.getTile(12,4));
                            break;
                        case 50:
                            tileView = new ImageView(bigSprite.getTile(13,4));
                            break;
                        case 58:
                            tileView = new ImageView(bigSprite.getTile(8,5));
                            break;
                        case 60:
                            tileView = new ImageView(bigSprite.getTile(10,5));
                            break;
                        case 61:
                            tileView = new ImageView(bigSprite.getTile(11,5));
                            break;
                        case 62:
                            tileView = new ImageView(bigSprite.getTile(12,5));
                            break;
                        case 63:
                            tileView = new ImageView(bigSprite.getTile(13,5));
                            break;
                        case 71:
                            tileView = new ImageView(bigSprite.getTile(10,6));
                            break;
                        case 72:
                            tileView = new ImageView(bigSprite.getTile(11,6));
                            break;
                        case 78:
                            tileView = new ImageView(bigSprite.getTile(9,7));
                            break;
                        case 79:
                            tileView = new ImageView(bigSprite.getTile(10,7));
                            break;
                        case 73:
                            tileView = new ImageView(bigSprite.getTile(11,7));
                            break;
                        case 81:
                            tileView = new ImageView(bigSprite.getTile(12,7));
                            break;
                        case 82:
                            tileView = new ImageView(bigSprite.getTile(13,7));
                            break;
                        case 84:
                            tileView = new ImageView(bigSprite.getTile(10,8));
                            break;
                        case 85:
                            tileView = new ImageView(bigSprite.getTile(11,8));
                            break;
                        case 86:
                            tileView = new ImageView(bigSprite.getTile(12,8));
                            break;
                        case 87:
                            tileView = new ImageView(bigSprite.getTile(13,8));
                            break;
                        case 0:
                        default:
                            tileView = new ImageView();
                            break;
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
                int tileCode = mapData[y][x];
                if (tileCode != 0) {  // If it's not an empty tile
                    Rectangle2D tileBounds = new Rectangle2D(
                            x * tileSize,
                            y * tileSize,
                            tileSize,
                            tileSize
                    );

                    if (quacky.getBoundingBox().intersects(tileBounds)) {
                        if (tileCode == 100) {
                            // Handle collision with pipe
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
}