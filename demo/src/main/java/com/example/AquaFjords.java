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

public class AquaFjords extends Level{
    private double spriteSize = 50;
    protected Game game;
    protected Stage stage;
    private ImageView backgroundImage;
    private GamePane gamePane;
    private GridPane gridPane;
    private Scene scene;
    private Sprite tileSprite;
    private Sprite treeSprite;
    private Sprite flagSprite;
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
                    {19, 19, 19, 20, 0, 0, 0, 0, 0, 762, 763, 763, 764, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 0, 22, 0,22, 0, 39, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                    {19, 28, 36, 37, 0, 0, 0, 0, 0, 765, 766, 766, 767, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 70, 71, 0, 69, 70, 71, 0, 0, 0, 0, 0, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 70, 70, 70, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 0, 22, 0, 22, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                    {19, 20, 0, 0, 0, 0, 0, 0, 0, 768, 769, 769, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 70, 71, 0, 69, 70, 71, 0, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 0, 0, 73, 0, 0, 73, 0, 0, 69, 70, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 70, 71, 0, 0, 0, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 70, 70, 70, 71, 0, 0, 0, 0, 0, 0, 0, 22, 0, 22, 0, 39, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                    {19, 20, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 0, 39, 0, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 0, 39, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                    {19, 20, 0, 0, 0, 0, 0, 0, 18, 19, 19, 2, 2, 3, 0, 0, 0, 0, 69, 70, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 70, 71, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 71, 0, 69, 71, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 762, 763, 764, 0, 0, 39, 0, 0, 0, 0, 0, 5, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                    {19, 20, 0, 0, 0, 0, 1, 2, 2, 2, 19, 19, 19, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 22, 0, 0, 73, 0, 39, 0, 73, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 1, 3, 0, 1, 3, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 765, 766, 767, 0, 0, 0, 0, 0, 0, 5, 0, 22, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                    {19, 20, 0, 0, 0, 0, 18, 19, 19, 19, 19, 19, 2, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 18, 19, 20, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 22, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 1, 2, 2, 3, 35, 36, 36, 37, 763, 764, 0, 0, 0, 1, 2, 18, 20, 0, 18, 20, 2, 3, 0, 0, 0, 0, 22, 0, 0, 0, 0, 22, 762, 763, 764, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 0, 0, 35, 36, 36, 37, 0, 0, 1, 2, 2, 3, 0, 0, 0, 0, 768, 769, 770, 0, 0, 0, 0, 5, 0, 22, 0, 22, 0, 22, 762, 763, 764, 0, 0, 0, 0, 151, 152, 153, 0,},
                    {19, 20, 0, 0, 0, 1, 2, 2, 2, 2, 19, 19, 19, 19, 19, 20, 0, 0, 0, 0, 1, 2, 3, 0, 0, 18, 19, 20, 0, 0, 18, 19, 20, 0, 0, 22, 0, 0, 0, 0, 73, 0, 0, 0, 0, 5, 0, 22, 0, 22, 0, 69, 70, 71, 0, 0, 22, 1, 2, 2, 3, 35, 36, 36, 37, 0, 0, 0, 765, 766, 767, 0, 1, 2, 18, 19, 19, 20, 0, 18, 19, 19, 20, 2, 3, 0, 0, 22, 5, 0, 0, 5, 22, 765, 766, 767, 69, 70, 71, 0, 0, 0, 1, 2, 2, 3, 0, 0, 35, 36, 36, 37, 0, 0, 0, 0, 0, 0, 0, 0, 35, 36, 36, 37, 0, 0, 1, 2, 2, 3, 0, 0, 0, 5, 0, 22, 0, 22, 0, 22, 0, 22, 765, 766, 767, 0, 0, 0, 0, 177, 178, 179, 0,},
                    {19, 20, 0, 0, 0, 18, 19, 19, 19, 19, 19, 19, 19, 19, 19, 20, 0, 0, 0, 0, 35, 36, 37, 0, 0, 35, 36, 37, 0, 0, 35, 36, 37, 0, 0, 39, 0, 0, 0, 73, 0, 73, 0, 0, 0, 39, 0, 39, 0, 39, 0, 0, 0, 0, 0, 0, 39, 35, 36, 36, 37, 0, 0, 0, 0, 0, 0, 0, 0, 769, 770, 0, 35, 36, 36, 36, 36, 37, 0, 35, 36, 36, 36, 36, 37, 0, 0, 39, 39, 73, 73, 39, 39, 768, 769, 770, 0, 0, 0, 0, 0, 0, 35, 36, 36, 37, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, 36, 36, 37, 0, 0, 0, 39, 0, 39, 0, 39, 0, 39, 0, 39, 768, 769, 770, 0, 0, 0, 0, 203, 204, 205, 0,},
                    {19, 11, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
            };


    public AquaFjords(Game game, Stage stage, ImageLoader imageLoader) throws FileNotFoundException {
        super(game, stage, imageLoader);

        this.game = game;
        this.stage = stage;
        this.backgroundImage = new ImageView(imageLoader.loadImage("/images/Aqua Fjords.jpg"));
        this.gameWorld = new Pane();
        this.gamePane = new GamePane(backgroundImage.getImage());
        this.scene = new Scene(gamePane, 1000, 650);
        this.camera = new Camera();
        this.soundManager = SoundManager.getInstance();
        soundManager.loadSounds();

        soundManager.playBackgroundMusic("Aqua");

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
                    InputStream tileSpriteStream = getClass().getResourceAsStream("/images/AquaFjordsTileSet.png");
                    tileSprite = new Sprite(tileSpriteStream, 72, 72);
                    InputStream treeSpriteStream = getClass().getResourceAsStream("/images/AquaFjordsTree.png");
                    treeSprite = new Sprite(treeSpriteStream, 19, 25);
                    InputStream flagSpriteStream = getClass().getResourceAsStream("/images/AquaFjordsMarioFlag.jpeg");
                    flagSprite = new Sprite(flagSpriteStream, 50, 33);

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
                        case 5:
                            tileView = new ImageView(tileSprite.getTile(3, 0));
                            break;
                        case 11:
                            tileView = new ImageView(tileSprite.getTile(8, 0));
                            break;
                        case 18:
                            tileView = new ImageView(tileSprite.getTile(0, 1));
                            break;
                        case 19:
                            tileView = new ImageView(tileSprite.getTile(1, 1));
                            break;
                        case 20:
                            tileView = new ImageView(tileSprite.getTile(2, 1));
                            break;
                        case 22:
                            tileView = new ImageView(tileSprite.getTile(3, 1));
                            break;
                        case 28:
                            tileView = new ImageView(tileSprite.getTile(8, 1));
                            break;
                        case 35:
                            tileView = new ImageView(tileSprite.getTile(0, 2));
                            break;
                        case 36:
                            tileView = new ImageView(tileSprite.getTile(1, 2));
                            break;
                        case 37:
                            tileView = new ImageView(tileSprite.getTile(2,2));
                            break;
                        case 39:
                            tileView = new ImageView(tileSprite.getTile(3,2));
                            break;
                        case 69:
                            tileView = new ImageView(tileSprite.getTile(0,3));
                            break;
                        case 70:
                            tileView = new ImageView(tileSprite.getTile(1,3));
                            break;
                        case 71:
                            tileView = new ImageView(tileSprite.getTile(2,3));
                            break;
                        case 73:
                            tileView = new ImageView(tileSprite.getTile(3,3));
                            break;
                        case 762:
                            tileView = new ImageView(treeSprite.getTile(0,0));
                            break;
                        case 763:
                            tileView = new ImageView(treeSprite.getTile(1,0));
                            break;
                        case 764:
                            tileView = new ImageView(treeSprite.getTile(2,0));
                            break;
                        case 765:
                            tileView = new ImageView(treeSprite.getTile(0,1));
                            break;
                        case 766:
                            tileView = new ImageView(treeSprite.getTile(1,1));
                            break;
                        case 767:
                            tileView = new ImageView(treeSprite.getTile(2,1));
                            break;
                        case 768:
                            tileView = new ImageView(treeSprite.getTile(0,2));
                            break;
                        case 769:
                            tileView = new ImageView(treeSprite.getTile(1,2));
                            break;
                        case 770:
                            tileView = new ImageView(treeSprite.getTile(2,2));
                            break;
                        case 151:
                            tileView = new ImageView(flagSprite.getTile(0,0));
                            break;
                        case 152:
                            tileView = new ImageView(flagSprite.getTile(1,0));
                            break;
                        case 153:
                            tileView = new ImageView(flagSprite.getTile(2,0));
                            break;
                        case 177:
                            tileView = new ImageView(flagSprite.getTile(0,1));
                            break;
                        case 178:
                            tileView = new ImageView(flagSprite.getTile(1,1));
                            break;
                        case 179:
                            tileView = new ImageView(flagSprite.getTile(2,1));
                            break;
                        case 203:
                            tileView = new ImageView(flagSprite.getTile(0,2));
                            break;
                        case 204:
                            tileView = new ImageView(flagSprite.getTile(1,2));
                            break;
                        case 205:
                            tileView = new ImageView(flagSprite.getTile(2,2));
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
                        if (tileCode == 151 || tileCode == 152 || tileCode == 153 || tileCode == 177 || tileCode == 178 || tileCode == 179 || tileCode == 203 || tileCode == 204 || tileCode == 205 && !levelCompleted) {
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