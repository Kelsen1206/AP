package com.example;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

public abstract class GameObject {

    private double x, y;
    private double velX, velY;
    private double width, height;
    private ImageView style;
    private double gravityAcc;
    private boolean falling, jumping;

    public GameObject(double x, double y, Image style) {
        setLocation(x, y);
        setStyle(style);

        if (style != null) {
            setDimension(style.getWidth(), style.getHeight());
        }

        setVelX(0);
        setVelY(0);
        setGravityAcc(0.38);
        jumping = false;
        falling = true;
    }

    public void draw(GraphicsContext gc) {
        Image style = getStyle().getImage();

        if (style != null) {
            gc.drawImage(style, x, y);
        }

        // for debugging
        /*gc.setStroke(Color.WHITE);
        gc.strokeRect(getTopBounds().getMinX(), getTopBounds().getMinY(), getTopBounds().getWidth(), getTopBounds().getHeight());
        gc.strokeRect(getBottomBounds().getMinX(), getBottomBounds().getMinY(), getBottomBounds().getWidth(), getBottomBounds().getHeight());
        gc.strokeRect(getRightBounds().getMinX(), getRightBounds().getMinY(), getRightBounds().getWidth(), getRightBounds().getHeight());
        gc.strokeRect(getLeftBounds().getMinX(), getLeftBounds().getMinY(), getLeftBounds().getWidth(), getLeftBounds().getHeight());*/
    }

    public void updateLocation() {
        if (jumping && velY <= 0) {
            jumping = false;
            falling = true;
        } else if (jumping) {
            velY = velY - gravityAcc;
            y = y - velY;
        }

        if (falling) {
            y = y + velY;
            velY = velY + gravityAcc;
        }

        x = x + velX;
    }

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        style.setTranslateX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        style.setTranslateY(y);
    }

    public void setDimension(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public ImageView getStyle() {
        return style;
    }

    public void setStyle(Image style) {
        this.style = new ImageView(style);
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getGravityAcc() {
        return gravityAcc;
    }

    public void setGravityAcc(double gravityAcc) {
        this.gravityAcc = gravityAcc;
    }

    public Rectangle2D getTopBounds() {
        return new Rectangle2D(x + width / 6, y, 2 * width / 3, height / 2);
    }

    public Rectangle2D getBottomBounds() {
        return new Rectangle2D(x + width / 6, y + height / 2, 2 * width / 3, height / 2);
    }

    public Rectangle2D getLeftBounds() {
        return new Rectangle2D(x, y + height / 4, width / 4, height / 2);
    }

    public Rectangle2D getRightBounds() {
        return new Rectangle2D(x + 3 * width / 4, y + height / 4, width / 4, height / 2);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}
