package edu.austral.prog2_2018c2;

import org.junit.Test;

public class tester {

  @Test
  public void aaa(){
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
}
