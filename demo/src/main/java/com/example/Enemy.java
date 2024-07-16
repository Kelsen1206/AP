package com.example;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Enemy {
    private ImageView imageView;
    private double speedX;
    private double speedY;

    public Enemy(String imagePath, double startX, double startY, double speedX, double speedY) {
        Image image = new Image(imagePath);
        this.imageView = new ImageView(image);
        this.imageView.setX(startX);
        this.imageView.setY(startY);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void move() {
        imageView.setX(imageView.getX() + speedX);
        imageView.setY(imageView.getY() + speedY);
    }


    public void checkCollision(Quacky player) {
//        if (this.imageView.getBoundsInParent().intersects(player.getImageView().getBoundsInParent())) {
            // Handle collision with player
            System.out.println("Collision with player!");
        }
    }
//public Enemy1(double startX, double startY, double leftBoundary, double rightBoundary, SoundManager soundManager) {
//    this.x = startX;
//    this.y = startY;
//    this.velocityX = 0;
//    this.velocityY = 0;
//    this.isJumping = false;
//    this.isFacingRight = true;
//    this.health = 100;
//    this.leftBoundary = leftBoundary;
//    this.rightBoundary = rightBoundary;
//    this.soundManager = soundManager;
//    this.respawnX = startX;
//    this.respawnY = startY;
//    this.isDead = false;
//    this.isDying = false;
//
//    walkFrames = new Image[6];
//    for (int i = 0; i < 6; i++) {
//        walkFrames[i] = new Image(getClass().getResourceAsStream("/images/Quacky Walk Animation_" + (i + 1) + ".png"));
//    }
//
//    dieFrames = new Image[4];
//    for (int i = 0; i < 4; i++) {
//        dieFrames[i] = new Image(getClass().getResourceAsStream("/images/Quacky Die Animation_" + (i + 1) + ".png"));
//    }
//
//    this.sprite = new ImageView(walkFrames[0]);
//    this.sprite.setTranslateX(startX);
//    this.sprite.setTranslateY(startY);
//
//    // Set initial sprite size (adjust as needed)
//    this.sprite.setFitWidth(50);
//    this.sprite.setFitHeight(50);
//
//    // Ensure the sprite is visible
//    this.sprite.setVisible(true);
//}
////}
