package edu.austral.prog2_2018c2;

import javax.swing.*;

public class UFO extends Invader implements Commons{
  //aparece cada 45 a 60 seg
  int direction;
  public UFO(int direction){
    initUFO(direction);
  }
  
  public void initUFO(int direction) {
    this.direction = direction;
    points = 50 + (int)(Math.random()*((300-50) + 1));
    if (direction > 0) {
      this.x = 0;
    } else if (direction < 0) {
      this.x = BOARD_WIDTH;
    }
    this.y = 10;

    Img = "src/images/UFO.png";
    ImageIcon ii = new ImageIcon(Img);
    setImage(ii.getImage());
    setHeight(ii.getImage().getHeight(null));
    setWidth(ii.getImage().getWidth(null));
  }
  
  public int getDirection() {
    return direction;
  }
}
