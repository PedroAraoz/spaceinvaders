package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Bomb extends Sprite {
  
  private final String bombImg = "src/images/bomb.png";
  private boolean destroyed;
  
  public Bomb(int x, int y) {
    
    initBomb(x, y);
  }
  
  private void initBomb(int x, int y) {
    
    setDestroyed(true);
    this.x = x;
    this.y = y;
    ImageIcon ii = new ImageIcon(bombImg);
    setImage(ii.getImage());
    
  }
  
  public void setDestroyed(boolean destroyed) {
    
    this.destroyed = destroyed;
  }
  
  public boolean isDestroyed() {
    
    return destroyed;
  }
}