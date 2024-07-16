package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WinPage {
    private final String imagePath = "/images/";
    private final String fontPath = "/fonts/";
    private Game game;
    private Stage stage;
    private int currentWinIndex = 0;
    private GameStatus gameStatus;
    private SoundManager soundManager;
    private final String[] WinImages = {
            "Win1.png",
            "Win2.png",
            "Win3.png",
            "Win4.png",
    };
    private final String[] WinTexts = {
            " ",
            "Hey team, We did it! We managed to get everyone out safe and sound.",
            "Now, let's head home and celebrate our freedom! Quack, quack, hurray!",
            " ",
    };

    // Position coordinates for each win text (adjust these values as needed)
    private final double[][] WinTextPositions = {
            {300, 180}, // {X, Y} for win1
            {315, 100}, // {X, Y} for win2
            {285, 125}, // {X, Y} for win3
            {200, 310}, // {X, Y} for win4
    };

    public WinPage(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
        this.gameStatus = GameStatus.CONGRATULATIONS;

        this.soundManager = SoundManager.getInstance();
        soundManager.loadSounds();
        soundManager.playBackgroundMusic("Win");
    }

    public void show() throws IOException {
        showNextIntro();
    }

    private void showNextIntro() throws IOException {
        if (currentWinIndex < WinImages.length) {
            Image winImage = new Image(getClass().getResourceAsStream(imagePath + WinImages[currentWinIndex]));
            ImageView winImageView = new ImageView(winImage);
            winImageView.setFitWidth(1000);
            winImageView.setFitHeight(650);

            StackPane winPane = new StackPane();
            winPane.getChildren().add(winImageView);

            // Add dialog text
            Text dialogText = new Text();
            dialogText.setFont(Font.loadFont(getClass().getResourceAsStream(fontPath + "ARCADE_N.ttf"), 15));
            dialogText.setFill(javafx.scene.paint.Color.BLACK);
            dialogText.setWrappingWidth(365); // Adjust this value as needed
            dialogText.setTextAlignment(TextAlignment.LEFT);

            // Positioning the text inside the speech bubble (adjust the coordinates as needed)
            StackPane.setAlignment(dialogText, Pos.TOP_LEFT);
            double[] textPosition = WinTextPositions[currentWinIndex];
            dialogText.setTranslateX(textPosition[0]); // Adjust X coordinate
            dialogText.setTranslateY(textPosition[1]); // Adjust Y coordinate

            // Typing animation for text
            String fullText = WinTexts[currentWinIndex];
            Timeline timeline = new Timeline();
            for (int i = 0; i < fullText.length(); i++) {
                final int index = i;
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.05 * i), event -> {
                    dialogText.setText(fullText.substring(0, index + 1));
                });
                timeline.getKeyFrames().add(keyFrame);
            }
            timeline.play();

            winPane.getChildren().add(dialogText);

            Scene winScene = new Scene(winPane, 1000, 650);

            // Adding key press functionality
            winScene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case ENTER:
                        currentWinIndex++;
                        try {
                            showNextIntro();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    default:
                        break;
                }
            });

            stage.setScene(winScene);
            stage.show();
        } else {
            backtoMainMenu();
        }
    }

    private void backtoMainMenu() throws IOException {
        game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
        GameMenu gameMenu = new GameMenu(game, stage);
        gameMenu.showMenu();
    }
}
