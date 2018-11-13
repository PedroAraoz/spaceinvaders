package edu.austral.prog2_2018c2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class tester {

  @Test
  public void RandTester(){
    int Min = 50;
    int Max = 300;
    int x = Min + (int)(Math.random() * ((Max - Min) + 1));
    for (int i = 0; i < 10000; i++) {
      if (x > 300 || x < 50) {
        throw new RuntimeException("The randomizer is wrong!");
      }
    }
  }

  @Test
  public void deserializeDebugger(){
    Score s = Score.deserialize("b:1");
    String sName = s.getName(); //Expects b
    int sScore = s.getScore(); //Expects 1
  }
  
  @Test
  public void savefile(){
    Score s = Score.deserialize("John:100");
    Score j = Score.deserialize("Pepe:100274");
    List<Score> scores = new ArrayList<>();
    scores.add(s); scores.add(j);
    Score.save(scores);
  }
  
  @Test
  public void readFile(){
    List<Score> a = new ArrayList<>();
    a = Score.load();
    System.out.println("a");
  }
  @Test
  public void InteractionTest(){
    Player p = new Player();
    Alien a = new Alien(0,0, "small");
    Bomb b = new Bomb(0,0);
    Shield shield = new Shield();
    Shot shot = new Shot();
    p.interactsWith(b);
    shot.interactsWith(a);
    shot.interactsWith(shield);
    a.interactsWith(shield);
    a.interactsWith(shot);
    b.interactsWith(shield);
    b.interactsWith(p);
  }
}
