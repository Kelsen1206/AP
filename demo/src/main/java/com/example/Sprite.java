package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.InputStream;

public class Sprite {
    private Image tileSet;
    private int tileWidth;
    private int tileHeight;
    int centerX;
    int centerY;

    public Sprite(InputStream imageStream, int tileWidth, int tileHeight, int centerX, int centerY) {
        this.tileSet = new Image(imageStream);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public Sprite(InputStream imageStream, int tileWidth, int tileHeight) {
        this(imageStream, tileWidth, tileHeight, 0, 0);
    }

    public WritableImage getTile(int x, int y) {
        PixelReader reader = tileSet.getPixelReader();
        WritableImage tile = new WritableImage(reader, x * tileWidth, y * tileHeight, tileWidth, tileHeight);
        return tile;
    }
}
