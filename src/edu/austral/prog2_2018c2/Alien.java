package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Alien extends Sprite {
    private Bomb bomb;
    //private final String alienImg = "src/images/alien.png";
    private String alienImg;
    private int points;
    private int height;
    private int width;
    public Alien(int x, int y, String type) {
        // dejar el switch o lo cambio a subclases???
        initAlien(x, y, type);
    }

    private void initAlien(int x, int y, String type) {

        this.x = x;
        this.y = y;
        switch (type){
          case "big": points = 10;height = 12;width = 12;alienImg = "src/images/alien.png";break;
          case "medium": points = 20;height = 11;width = 11;alienImg = "src/images/alien2.png";break;
          case "small": points = 30;height = 10;width = 10;alienImg = "src/images/alien3.png";break;
        }
        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
    }

    public void act(int direction) {

        this.x += direction;
    }

    public Bomb getBomb() {

        return bomb;
    }
    public int getPoints(){
      return points;
    }
  
    public int getHeight() {
      return height;
    }
  
    public int getWidth() {
      return width;
    }
}