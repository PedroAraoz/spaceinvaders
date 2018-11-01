package edu.austral.prog2_2018c2;

import javax.swing.ImageIcon;

public class Bomb extends Sprite implements GameObject, Movable{
  
  private final String bombImg = "src/images/bomb.png";
  
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
  @Override
  public void collided(Movable m) {
    m.collideWithBomb(this);
  }
  
  @Override
  public void collideWithPlayer(Player player) {
    player.collideWithBomb(this);
  }
  @Override
  public void collideWithShield(Shield shield) {
    System.out.println("boom boom shield");
  }
}