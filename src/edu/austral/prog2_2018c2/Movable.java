package edu.austral.prog2_2018c2;

public interface Movable {
  default void interactsWith(GameObject o){
    o.collided(this);
  }
  default void collideWithBomb(Bomb b){}
  
  default void collideWithShield(Shield shield){}
  
  default void collideWithShot(Shot shot){}
  
  default void collideWithAlien(Alien alien){}
  
  default void collideWithPlayer(Player player){}
}
