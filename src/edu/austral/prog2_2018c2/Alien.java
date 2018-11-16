package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Alien extends Invader {

    private Bomb bomb;
    private int downSpeed;
    public Alien(int x, int y, String type) {
        initAlien(x, y, type);
    }

    private void initAlien(int x, int y, String type) {
        downSpeed = 15;
        this.x = x;
        this.y = y;
        switch (type){
          case "big": points = 10;Img = "src/images/big.gif";break;
          case "medium": points = 20;Img = "src/images/medium.gif";break;
          case "small": points = 30;Img = "src/images/small.gif";break;
        }
        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(Img);
        setImage(ii.getImage());
        setHeight(ii.getImage().getHeight(null));
        setWidth(ii.getImage().getWidth(null));
    }

    public Bomb getBomb() {

        return bomb;
    }
    public void goDown(){
        setY(y + downSpeed);
    }
    
    public void spawnBomb() {
        bomb.setVisible(true);
        bomb.setX(x);
        bomb.setY(y);
    }
}