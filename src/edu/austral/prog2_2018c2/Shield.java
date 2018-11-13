package edu.austral.prog2_2018c2;

import javax.swing.*;

public class Shield extends Sprite{

  private int percentage;
  private String ShieldImg;
  private String damagedShieldImg;
  private String dyingShieldImg;
  
  public Shield(){
    percentage = 100;
    ShieldImg = "src/images/shield.png";
    damagedShieldImg = "src/images/shield2.png";
    dyingShieldImg = "src/images/shield3.png";
    ImageIcon ii = new ImageIcon(ShieldImg);
    setImage(ii.getImage());
    setWidth(ii.getImage().getWidth(null));
    setHeight(ii.getImage().getHeight(null));
  }

  public int getPercentage(){
    return percentage;
  }
  public void hit(){
    percentage -= 2;
    if (percentage < 20) {
      setImage(new ImageIcon(dyingShieldImg).getImage());
    }
    else if (percentage < 80) {
      setImage(new ImageIcon(damagedShieldImg).getImage());
    }
    if (percentage <= 0){
      setVisible(false);
    }
  }
}
