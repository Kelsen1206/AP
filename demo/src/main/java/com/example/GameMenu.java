package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameMenu {
    private final String path = "file:C:\\Users\\kelse\\OneDrive\\Desktop\\Advance Programming asgn\\demo\\src\\main\\resources\\images\\";
    private Game game;
    private Stage stage;

    public GameMenu(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
    }
    
    public void showMenu() {
        GamePane gamePane = new GamePane(new Image(path + "background2.png"));

        VBox menuBox = new VBox(0);
        menuBox.setAlignment(Pos.CENTER);

        // Buttons
        Button loginButton = createImageButton(path + "Login button.png");
        Button signUpButton = createImageButton(path + "Signup button.png");
        Button userGuideButton = createImageButton(path + "Userguide button.png");
        Button settingsButton = createImageButton(path + "Settings button.png");

        loginButton.setOnAction(event -> showLoginPage());
        signUpButton.setOnAction(event -> showSignUpPage());
        userGuideButton.setOnAction(event -> showUserGuidePage());
        settingsButton.setOnAction(event -> showSettingsPage());

        menuBox.getChildren().addAll(loginButton, signUpButton, userGuideButton, settingsButton);

        StackPane.setAlignment(menuBox, Pos.CENTER);
        gamePane.getChildren().add(menuBox);

        Scene menuScene = new Scene(gamePane, 1000, 650);
        stage.setScene(menuScene);
        stage.show();
    }

    private Button createImageButton(String imagePath) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-border-color: transparent;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"));

        return button;
    }

    private void showLoginPage() {
        GamePane gamePane = new GamePane(new Image(path + "login page.png"));
        LoginPage loginPage = new LoginPage(gamePane, game, stage);
        loginPage.show();
    }

    private void showSignUpPage() {
        GamePane gamePane = new GamePane(new Image(path + "sign up page.png"));
        SignUpPage signUpPage = new SignUpPage(gamePane, game, stage);
        signUpPage.show();
    }

    private void showUserGuidePage() {
        GamePane gamePane = new GamePane(new Image(path + "user guide page.png"));
        UserGuidePage userGuidePage = new UserGuidePage(gamePane, game, stage);
        userGuidePage.show();
    }

    private void showSettingsPage() {
        GamePane gamePane = new GamePane(new Image(path + "settings page.png"));
        SettingsPage settingsPage = new SettingsPage(gamePane, game, stage);
        settingsPage.show();
    }
}
