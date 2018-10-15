package edu.austral.prog2_2018c2;

public class Invader extends Sprite {
  protected String Img;
  protected int points;
  
  public void act(int direction) {
    
    this.x += direction;
  }
  public int getPoints(){
    return points;
  }
}
