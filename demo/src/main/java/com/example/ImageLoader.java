package com.example;

import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.image.Image;

public class ImageLoader {
    public Image loadImage(String path) throws FileNotFoundException {
        InputStream imageStream = getClass().getResourceAsStream(path);
        if (imageStream == null) {
            throw new FileNotFoundException("Image not found: " + path);
        }
        return new Image(imageStream);
    }
}