package edu.austral.prog2_2018c2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoard { //Podriamos hacer que este objeto sea un metodo estatico en Score que devuelva una lista de scores si no le encontramos un sentido al objeto leaderboard

    private List<Score> scores;

    public LeaderBoard(){
        scores = new ArrayList<Score>() {//Aca armo una lista que se mantiene a si misma ordenada por puntaje del Score
            @Override
            public boolean add(Score score){
                super.add(score);
                Collections.sort(this, (Score::compareTo));
                return true;
            }
        };
    }

    public void addScore(Score score){
        scores.add(score);
    }
}
