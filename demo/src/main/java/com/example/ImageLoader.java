package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class ImageLoader {
    public Image loadImage(String path) throws FileNotFoundException {
        return new Image(new FileInputStream(path));
    }
}

