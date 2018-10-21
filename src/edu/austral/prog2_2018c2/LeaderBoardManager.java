package edu.austral.prog2_2018c2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoardManager { //Habria como que hacer un tester de esto...
    
    private List<Score> scores;

    public LeaderBoardManager(){
        scores = new ArrayList<Score>() {//Aca armo una lista que se mantiene a si misma ordenada por puntaje del Score
            @Override
            public boolean add(Score score){
                super.add(score);
                this.sort((Score::compareTo));
                return true;
            }
        };
    }

    public void addScore(Score score){ //No lo usamos tecnicamente pero lo dejo porque why not. Usariamos el de abajo
        scores.add(score);
    }

    public void addScore(String name, int score){
        scores.add(new Score(name, score));
    }
    
    public void save(){
        try {
            FileWriter fw = new FileWriter("LeaderBoard.txt");
            for (Score score : scores) {
                fw.write(score.serialize());
            }
            fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("write: FNF Exception");
        } catch (IOException e) {
            System.out.println("write: IO Exception");
        }
    }

    public void load(){
        try {
            FileReader fr = new FileReader("LeaderBoard.txt");
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null){
                scores.add(Score.deserialize(s));
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("read: File not found");
        } catch (IOException e) {
            System.out.println("read: IO exception");
        }
    }
}