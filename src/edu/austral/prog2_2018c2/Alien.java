package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Alien extends Invader implements GameObject, Movable{

    private Bomb bomb;

    public Alien(int x, int y, String type) {
        initAlien(x, y, type);
    }

    private void initAlien(int x, int y, String type) {

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
        setHeight(16);
        setWidth(ii.getImage().getWidth(null));
    }

    public Bomb getBomb() {

        return bomb;
    }
    @Override
    public void collided(Movable m) {
      m.collideWithAlien(this);
    }
    @Override
    public void collideWithShield(Shield shield) {
      System.out.println("asdjiasdjasdasdasd shie");
    }
    public void collideWithShot(Shot shot){
      System.out.println("asdasdasdasdasd shot");
    }
}