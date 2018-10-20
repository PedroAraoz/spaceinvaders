package edu.austral.prog2_2018c2;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard { //Podriamos hacer que este objeto sea un metodo estatico en Score que devuelva una lista de scores si no le encontramos un sentido al objeto leaderboard

    private List<Score> scores;

    public LeaderBoard(){
        scores = new ArrayList<Score>(); //Podriamos hacer una Sorted List y sortearla por el int de scores pero para eso hay q hacer algo de un comparator
    }

    public void addScore(Score score){
        scores.add(score);
    }
}
