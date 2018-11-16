package edu.austral.prog2_2018c2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
    Score s = Score.deserialize("b:1:date");
    String sName = s.getName();
    int sScore = s.getScore();
    assertEquals("b", sName);
    assertEquals(1, sScore);
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
}
