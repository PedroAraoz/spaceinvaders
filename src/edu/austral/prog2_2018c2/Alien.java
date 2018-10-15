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
        setHeight(16);
        switch (type){
          case "big": points = 10;setWidth(24)  ;Img = "src/images/big.gif";break;
          case "medium": points = 20;setWidth(22);Img = "src/images/medium.gif";break;
          case "small": points = 30;setWidth(16);Img = "src/images/small.gif";break;
        }
        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(Img);
        setImage(ii.getImage());
    }

    public Bomb getBomb() {

        return bomb;
    }
}