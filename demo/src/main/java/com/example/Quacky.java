
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
    private final double JUMP_VELOCITY = -17.0;
    private final double GRAVITY = 0.8;
    private long lastJumpTime = 0;
    private double leftBoundary;
    private double rightBoundary;
    private Image[] walkFrames;
    private int currentFrame;
    private long lastFrameChange;
    private static final long FRAME_DURATION = 100_000_000;
    private SoundManager soundManager;
    private long lastJumpSoundTime = 0;
    private static final long JUMP_SOUND_COOLDOWN = 100_000_000;
    private Image[] dieFrames;
    private boolean isDying;
    private int dieFrameIndex;
    private long lastDieFrameChange;
    private static final long DIE_FRAME_DURATION = 200_000_000;
    private boolean isDead;
    private double respawnX, respawnY;

    public Quacky(double startX, double startY, double leftBoundary, double rightBoundary, SoundManager soundManager) {
        this.x = startX;
        this.y = startY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isJumping = false;
        this.isFacingRight = true;
        this.health = 100;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.soundManager = soundManager;
        this.respawnX = startX;
        this.respawnY = startY;
        this.isDead = false;
        this.isDying = false;

        walkFrames = new Image[6];
        for (int i = 0; i < 6; i++) {
            walkFrames[i] = new Image(getClass().getResourceAsStream("/images/Quacky Walk Animation_" + (i + 1) + ".png"));
        }

        dieFrames = new Image[4];
        for (int i = 0; i < 4; i++) {
            dieFrames[i] = new Image(getClass().getResourceAsStream("/images/Quacky Die Animation_" + (i + 1) + ".png"));
        }

        this.sprite = new ImageView(walkFrames[0]);
        this.sprite.setTranslateX(startX);
        this.sprite.setTranslateY(startY);

        // Set initial sprite size (adjust as needed)
        this.sprite.setFitWidth(50);
        this.sprite.setFitHeight(50);

        // Ensure the sprite is visible
        this.sprite.setVisible(true);
    }

    public void moveLeft() {
        System.out.println("Quacky moving left");
        velocityX = -SPEED;
        isFacingRight = false;
        updateSpriteDirection();
    }

    public void moveRight() {
        System.out.println("Quacky moving right");
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
        if (!isJumping && (System.nanoTime() - lastJumpTime > 150_000_000)) {
            velocityY = JUMP_VELOCITY;
            isJumping = true;
            lastJumpTime = System.nanoTime();

            if (System.nanoTime() - lastJumpSoundTime > JUMP_SOUND_COOLDOWN) {
                soundManager.playSoundEffect("jump");
                lastJumpSoundTime = System.nanoTime();
            }
        }
    }

    public void update(long now) {
        System.out.println("Updating Quacky: x=" + x + ", y=" + y + ", velocityX=" + velocityX + ", velocityY=" + velocityY);
        if (isDying) {
            updateDieAnimation(now);
        } else if (!isDead) {
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

            updateAnimation(now);
            updateSpriteDirection();
        }
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
        if (health < 0)
            health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(x, y, sprite.getFitWidth(), sprite.getFitHeight());
    }

    // Getters and setters
    public ImageView getSprite() {
        return this.sprite;
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

    public void respawn() {
        x = respawnX;
        y = respawnY;
        velocityX = 0;
        velocityY = 0;
        isJumping = false;
        isDying = false;
        isDead = false;
        health = 100;  // Reset health
        updatePosition();
        sprite.setImage(walkFrames[0]);
    }

    private void updatePosition() {
        sprite.setTranslateX(x);
        sprite.setTranslateY(y);
    }

    public boolean hasFallenOffScreen(double screenHeight) {
        return y > screenHeight;
    }

    public void die() {
        if (!isDead) {
            health = 0;
            isDying = true;
            isDead = true;
            dieFrameIndex = 0;
            lastDieFrameChange = System.nanoTime();
            velocityX = 0;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isDying() {
        return isDying;
    }

    private void updateAnimation(long now) {
        if (Math.abs(velocityX) > 0) {
            if (now - lastFrameChange > FRAME_DURATION) {
                currentFrame = (currentFrame + 1) % walkFrames.length;
                sprite.setImage(walkFrames[currentFrame]);
                lastFrameChange = now;
            }
        } else {
            currentFrame = 0;
            sprite.setImage(walkFrames[currentFrame]);
        }
    }

    private void updateDieAnimation(long now) {
        if (now - lastDieFrameChange > DIE_FRAME_DURATION) {
            dieFrameIndex++;
            if (dieFrameIndex < dieFrames.length) {
                sprite.setImage(dieFrames[dieFrameIndex]);
                lastDieFrameChange = now;
            } else {
                isDying = false;
            }
        }
    }
}
