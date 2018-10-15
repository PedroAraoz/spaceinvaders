package edu.austral.prog2_2018c2;

import java.awt.Image;

public class Sprite {

    private boolean visible;
    private Image image;
    protected int x;
    protected int y;
    protected int dx;
    private int width;
    private int height;
    public Sprite() {

        visible = true;
    }

    public void die() {

        visible = false;
    }

    public boolean isVisible() {

        return visible;
    }

    protected void setVisible(boolean visible) {

        this.visible = visible;
    }

    public void setImage(Image image) {

        this.image = image;
    }

    public Image getImage() {

        return image;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setWidth(int width){
        this.width = width;
    }
}
