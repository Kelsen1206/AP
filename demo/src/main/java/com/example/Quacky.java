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
    private final double SPEED = 8.0;
    private final double JUMP_VELOCITY = -15.0;
    private final double GRAVITY = 1.0;
    private long lastJumpTime = 0;
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

    public void land(double groundY) {
        isJumping = false;
        velocityY = 0;
        y = groundY - sprite.getFitHeight();
    }

    public void stopJump() {
        velocityY = 0;
    }

    public void jump() {
        if (!isJumping && (System.currentTimeMillis() - lastJumpTime > 150)) {
            velocityY = JUMP_VELOCITY;
            isJumping = true;
            lastJumpTime = System.currentTimeMillis();
        }
    }

    public void update() {
        // Update horizontal position
        x += velocityX;
        x = Math.max(leftBoundary, Math.min(x, rightBoundary - sprite.getFitWidth()));

        // Apply gravity
        velocityY += GRAVITY;
        y += velocityY;

        velocityY = Math.min(velocityY, 20);

        // Update sprite position
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
        sprite.setTranslateX(this.x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        sprite.setTranslateY(this.y);
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
