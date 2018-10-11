package edu.austral.prog2_2018c2;

public class Shield extends Sprite{
  private boolean alive;
  private int percentage;
  public Shield(){
    alive = true;
    percentage = 100;
  }
  public boolean isAlive() {
    return alive;
  }
  public void die(){
    alive = false;
  }
  public boolean hit(){
    percentage -= 2;
    if (percentage <= 0){
      die();
    }
    return true;
  }
}
