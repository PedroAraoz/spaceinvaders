package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Shot extends Sprite implements GameObject, Movable{

    private final String shotImg = "src/images/shot.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
    @Override
    public void collided(Movable m) {
        m.collideWithShot(this);
    }
    
    @Override
    public void collideWithAlien(Alien alien) {
        alien.collideWithShot(this);
    }
    public void collideWithShield(Shield shield) {
        System.out.println("SHOT WIT SHIELD");
    }
}
