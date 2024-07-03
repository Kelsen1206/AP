package com.example;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SettingsPage {
    private final String path = "C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\";
    private GamePane gamePane;
    private Game game;
    private Stage stage;
    private static final int DEFAULT_VOLUME = 50;
    private Slider volumeSlider;
    private CheckBox muteCheckBox;

    public SettingsPage(GamePane gamePane, Game game, Stage stage) {
        this.gamePane = gamePane;
        this.game = game;
        this.stage = stage;
    }

    public void show() {
        Font font = Font.loadFont("file:" + path + "fonts\\ARCADE_N.ttf", 16);
        Font errorFont = Font.loadFont("file:" + path + "fonts\\ARCADE_N.ttf", 8);

        StackPane settingsPane = new StackPane();
        settingsPane.setPrefSize(1000, 650);  // Set preferred size for the StackPane

        // Background image
        ImageView settingsPage = new ImageView(new Image("file:" + path + "images\\settings page.png"));
        settingsPage.setFitWidth(1000);
        settingsPage.setFitHeight(650);

        Label titleLabel = new Label("Sound settings");
        titleLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Adjust font size and color
        titleLabel.setFont(font);

        Label volumeLabel = new Label("Volume:");
        volumeLabel.setStyle("-fx-text-fill: #FFFFFF;"); // Adjust font size and color
        volumeLabel.setFont(font);

        volumeSlider = new Slider(0, 100, DEFAULT_VOLUME);
        volumeSlider.setPrefWidth(500);
        volumeSlider.setMaxWidth(Double.MAX_VALUE);

        muteCheckBox = new CheckBox("Mute Sound");
        muteCheckBox.setStyle("-fx-text-fill: #FFFFFF;");
        muteCheckBox.setFont(font);

        // Layout for UI controls
        VBox settingsLayout = new VBox(25);
        settingsLayout.setAlignment(Pos.CENTER); // Center aligns the children vertically within the VBox
        settingsLayout.setPadding(new Insets(20)); // 20 pixels padding around the VBox
        settingsLayout.setStyle("-fx-padding: 20px;");
        settingsLayout.setMaxWidth(600);
        settingsLayout.getChildren().addAll(titleLabel, volumeLabel, volumeSlider, muteCheckBox);

        Button backButton = new Button("Back");
        backButton.setPrefSize(60, 35);
        backButton.setFont(errorFont);
        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-cursor: hand;"));
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-cursor: default;"));
        backButton.setOnAction(e -> {
            game.setGameStatus(GameStatus.MAIN_MENU_SCREEN);
            gamePane.setBackgroundImage(new Image("file:" + path + "images\\background2.png"));
            GameMenu gameMenu = new GameMenu(game, stage);
            gameMenu.showMenu();
        }); // Handle going back to the previous scene


        VBox backButtonBox = new VBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        backButtonBox.setPadding(new Insets(70));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(settingsLayout);
        borderPane.setBottom(backButtonBox);

        settingsPane.getChildren().addAll(settingsPage, borderPane);

        Scene settingsScene = new Scene(settingsPane, 1000, 650);
        stage.setScene(settingsScene);
        stage.show();

        // Adjust volume label based on slider value
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                volumeLabel.setText("Volume: " + newValue.intValue());
            }
        });

         // Mute checkbox listener
         muteCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    // Handle mute action
                    volumeSlider.setDisable(true); // Disable volume slider when muted
                } else {
                    // Handle unmute action
                    volumeSlider.setDisable(false); // Enable volume slider
                }
            }
        });
    }
}
