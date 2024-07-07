package com.example;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Quacky {
    private ImageView sprite;
    private double x, y;
    private double velocityX, velocityY;
    private boolean isJumping;
    private boolean isFacingRight;
    private int health;
    private final double SPEED = 5.0;
    private final double JUMP_VELOCITY = -15.0;
    private final double GRAVITY = 0.8;
    private double leftBoundary;
    private double rightBoundary;

    public Quacky(double startX, double startY, double leftBoundary, double rightBoundary) {
        this.x = startX;
        this.y = startY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isJumping = false;
        this.isFacingRight = true;
        this.health = 100;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;

        // Load Quacky's image
        Image quackyImage = new Image(getClass().getResourceAsStream("/images/Quacky.png"));
        this.sprite = new ImageView(quackyImage);
        this.sprite.setTranslateX(startX);
        this.sprite.setTranslateY(startY);

        // Set initial sprite size (adjust as needed)
        this.sprite.setFitWidth(50);
        this.sprite.setFitHeight(50);

        // Ensure the sprite is visible
        this.sprite.setVisible(true);
    }

    public void moveLeft() {
        velocityX = -SPEED;
        isFacingRight = false;
        updateSpriteDirection();
    }

    public void moveRight() {
        velocityX = SPEED;
        isFacingRight = true;
        updateSpriteDirection();
    }

    public void stopMoving() {
        velocityX = 0;
    }

    public void jump() {
        if (!isJumping) {
            velocityY = JUMP_VELOCITY;
            isJumping = true;
        }
    }

    public void update() {
        // Update horizontal position
        x += velocityX;

        // Ensure Quacky stays within the background bounds
        x = Math.max(0, Math.min(x, rightBoundary - sprite.getFitWidth()));

        // Update vertical position
        if (isJumping) {
            y += velocityY;
            velocityY += GRAVITY;

            // Simple ground collision (adjust Y value as needed)
            if (y > 550) {
                y = 550;
                velocityY = 0;
                isJumping = false;
            }
        }

        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
    }

    public double getCenterX() {
        return x + sprite.getFitWidth() / 2;
    }

    private void updateSpriteDirection() {
        // Flip the sprite based on direction
        sprite.setScaleX(isFacingRight ? 1 : -1);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(x, y, sprite.getFitWidth(), sprite.getFitHeight());
    }

    // Getters and setters
    public ImageView getSprite() {
        return sprite;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setLeftBoundary(double leftBoundary) {
        this.leftBoundary = leftBoundary;
    }

    public void setRightBoundary(double rightBoundary) {
        this.rightBoundary = rightBoundary;
    }
}
