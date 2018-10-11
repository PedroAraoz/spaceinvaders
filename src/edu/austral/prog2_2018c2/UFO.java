package edu.austral.prog2_2018c2;

import javax.swing.*;

public class UFO extends Sprite{
  //aparece cada 45 a 60 seg
  
  private String UFOImg;
  private int points;
  private int height;
  private int width;
  
  
  public UFO(int direction){
    initUFO(direction);
  }
  
  private void initUFO(int direction) {
    points = 50 + (int)(Math.random()*((300-50) + 1));
    if (direction == 1) {
      this.x = 0;
    } else if (direction == -1) {
      this.x = 358; // ~~~BOARD_WIDTH!!
    }
    this.y = 10;
    height = 10;
    width = 10; // (aca o en commons?????)
    UFOImg = "src/images/UFO.png";
    ImageIcon ii = new ImageIcon(UFOImg);
    setImage(ii.getImage());
    
  }
  public void act(int direction) {
    
    this.x += direction;
  }
  public int getPoints() {
    return points;
  }
  
  public int getHeight() {
    return height;
  }
  
  public int getWidth() {
    return width;
  }
}
