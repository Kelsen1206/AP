package com.example;

import javafx.scene.image.Image;

public class Quacky extends GameObject {

    public Quacky(double x, double y, Image style) {
        super(x, y, style);
    }

    public void move(double deltaX, double deltaY) {
        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }
}