package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class IntroductionPage {
    private final String imagePath = "/images/";
    private final String fontPath = "/fonts/";
    private Game game;
    private Stage stage;
    private ImageLoader imageLoader;
    private int currentIntroIndex = 0;
    private final String[] introImages = {
        "intro1.png",
        "intro2.png",
        "intro3.png",
        "intro4.png",
        "intro5.png"
    };
    private final String[] introTexts = {
        "Hello, everyone!\nI'm Quacky, I need your help to save my friends and restore the balance in Quackland.",
        "My friends, the elemental guardians, have been kidnapped by the evil Dr. Minder.",
        "He has scattered the powerful elemental shards that sustain our world, and we must collect them to set my friends free and defeat Dr. Minder.",
        "Join me as I embark on an adventure through various elemental landscapes to rescue my friends and retrieve the lost shards.",
        "Each level will present its own challenges, but with your help, we can overcome them."
    };

    // Position coordinates for each intro text (adjust these values as needed)
    private final double[][] introTextPositions = {
        {300, 180}, // {X, Y} for intro1
        {335, 110}, // {X, Y} for intro2
        {310, 85}, // {X, Y} for intro3
        {200, 310}, // {X, Y} for intro4
        {180, 325}  // {X, Y} for intro5
    };

    public IntroductionPage(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
    }

    public void show() throws IOException {
        showNextIntro();
    }

    private void showNextIntro() throws IOException {
        if (currentIntroIndex < introImages.length) {
            Image introImage = new Image(getClass().getResourceAsStream(imagePath + introImages[currentIntroIndex]));
            ImageView introImageView = new ImageView(introImage);
            introImageView.setFitWidth(1000);
            introImageView.setFitHeight(650);

            StackPane introPane = new StackPane();
            introPane.getChildren().add(introImageView);

            // Add dialog text
            Text dialogText = new Text();
            dialogText.setFont(Font.loadFont(getClass().getResourceAsStream(fontPath + "ARCADE_N.ttf"), 18));
            dialogText.setFill(javafx.scene.paint.Color.BLACK);
            dialogText.setWrappingWidth(500); // Adjust this value as needed
            dialogText.setTextAlignment(TextAlignment.LEFT);

            // Positioning the text inside the speech bubble (adjust the coordinates as needed)
            StackPane.setAlignment(dialogText, Pos.TOP_LEFT);
            double[] textPosition = introTextPositions[currentIntroIndex];
            dialogText.setTranslateX(textPosition[0]); // Adjust X coordinate
            dialogText.setTranslateY(textPosition[1]); // Adjust Y coordinate

            // Typing animation for text
            String fullText = introTexts[currentIntroIndex];
            Timeline timeline = new Timeline();
            for (int i = 0; i < fullText.length(); i++) {
                final int index = i;
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.05 * i), event -> {
                    dialogText.setText(fullText.substring(0, index + 1));
                });
                timeline.getKeyFrames().add(keyFrame);
            }
            timeline.play();

            // Create an ImageView for the "Next" button
            Button nextButton = new Button();
            Image nextButtonImage = new Image(getClass().getResourceAsStream(imagePath + "next button.png")); // Ensure this image exists
            ImageView nextButtonImageView = new ImageView(nextButtonImage);
            nextButtonImageView.setFitWidth(175);
            nextButtonImageView.setPreserveRatio(true);
            nextButton.setGraphic(nextButtonImageView);
            nextButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

            nextButton.setOnMouseEntered(event -> nextButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: hand;"));
            nextButton.setOnMouseExited(event -> nextButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-cursor: default;"));

            nextButton.setOnMouseClicked(e -> {
                currentIntroIndex++;
                try {
                    showNextIntro();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Positioning the next button (adjust the coordinates as needed)
            StackPane.setAlignment(nextButton, Pos.BOTTOM_CENTER);
            nextButton.setTranslateY(-20); // Adjust Y coordinate to move it above

            introPane.getChildren().addAll(dialogText, nextButton);

            Scene introScene = new Scene(introPane, 1000, 650);
            stage.setScene(introScene);
            stage.show();
        } else {
            startGame();
        }
    }

    private void startGame() throws IOException {
        imageLoader = new ImageLoader();
        game.setGameStatus(GameStatus.GAME_RUNNING);
        TechCity techCity = new TechCity(game, stage, imageLoader);
        techCity.createLevel();
        techCity.show();
    }
    
}
