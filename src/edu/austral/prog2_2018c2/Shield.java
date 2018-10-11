package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Shield extends Sprite{
  private boolean alive;
  private int percentage;
  private String ShieldImg;
  public Shield(){
    alive = true;
    percentage = 100;
    ShieldImg = "src/images/shield.gif";
    ImageIcon ii = new ImageIcon(ShieldImg);
    setImage(ii.getImage());
  }
  public boolean isAlive() {
    return alive;
  }
  public void die(){
    alive = false;
  }
  public int getPercentage(){
    return percentage;
  }
  public void hit(){
    percentage -= 2;
    if (percentage <= 0){
      die();
    }
  }
}
