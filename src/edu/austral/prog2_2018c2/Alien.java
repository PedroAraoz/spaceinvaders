package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Alien extends Invader {
    private Bomb bomb;
    //private final String alienImg = "src/images/alien.png";
    public Alien(int x, int y, String type) {
        initAlien(x, y, type);
    }

    private void initAlien(int x, int y, String type) {

        this.x = x;
        this.y = y;
        
        switch (type){
          case "big": points = 10;height = 12;width = 12;Img = "src/images/alien.png";break;
          case "medium": points = 20;height = 11;width = 11;Img = "src/images/alien2.png";break;
          case "small": points = 30;height = 10;width = 10;Img = "src/images/alien3.png";break;
        }
        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(Img);
        setImage(ii.getImage());
    }

    public Bomb getBomb() {

        return bomb;
    }
}