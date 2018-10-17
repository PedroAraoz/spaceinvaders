package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Bomb extends Sprite {
  
  private final String bombImg = "src/images/bomb.png";
  private boolean destroyed;
  
  public Bomb(int x, int y) {
    
    initBomb(x, y);
  }
  
  private void initBomb(int x, int y) {
    
    setVisible(false);
    this.x = x;
    this.y = y;
    ImageIcon ii = new ImageIcon(bombImg);
    setImage(ii.getImage());
    
  }
}