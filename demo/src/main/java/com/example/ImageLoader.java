package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public class ImageLoader {
    public Image loadImage(String filePath) throws FileNotFoundException {
        FileInputStream imageStream = new FileInputStream(filePath);
        return new Image(imageStream);
    }
}
