package edu.austral.prog2_2018c2;

import javax.swing.*;

public class UFO extends Invader implements Commons{
  //aparece cada 45 a 60 seg
  
  public UFO(int direction){
    initUFO(direction);
  }
  
  private void initUFO(int direction) {
    points = 50 + (int)(Math.random()*((300-50) + 1));
    if (direction == 1) {
      this.x = 0;
    } else if (direction == -1) {
      this.x = BOARD_WIDTH;
    }
    this.y = 10;
    setHeight(10);
    setWidth(10);
    Img = "src/images/UFO.png";
    ImageIcon ii = new ImageIcon(Img);
    setImage(ii.getImage());
    
  }
}
