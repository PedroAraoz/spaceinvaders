package edu.austral.prog2_2018c2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Score implements Comparable<Score> {

    private String name;
    private int score;
    private static String serializeSplitParameter = ":";
    private static String FileName = "C:/Users/Pedro/Desktop/TXT.txt";

    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String serialize(){
        return name+serializeSplitParameter+score;
    }

    @Override
    public int compareTo(Score o) {
        if(score > ((Score) o).getScore()){
            return 1;
        }
        else if (score < ((Score) o).getScore()){
            return -1;
        }
        else return 0;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

     //Static methods
    
    public static Score deserialize(String string){
        String[] splittedString = string.split(serializeSplitParameter);
        return new Score(splittedString[0], Integer.parseInt(splittedString[1]));
    }

    public static void save(List<Score> LeaderBoard){
        try {
            FileWriter fw = new FileWriter(FileName);
            for (Score score : LeaderBoard) {
                String txt = score.serialize() + System.getProperty( "line.separator" );
                fw.write(txt);
                System.out.println("beep boop");
            }
            fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("write: FNF Exception");
        } catch (IOException e) {
            System.out.println("write: IO Exception");
        }
    }

    public static List<Score> load(){
        try {
            FileReader fr = new FileReader(FileName);
            BufferedReader br = new BufferedReader(fr);
            List<Score> LeaderBoard = new ArrayList<Score>() { //Aca armo una lista que se mantiene a si misma ordenada por puntaje del Score
                @Override
                public boolean add(Score score) {
                    super.add(score);
                    this.sort((Score::compareTo));
                    if (size() >= 10){
                        remove(0); // el primero.
                    }
                    return true;
                }
            };
            String line;

            while ((line = br.readLine()) != null){
                LeaderBoard.add(Score.deserialize(line));
                System.out.println("aaaaaaaa");
            }
            br.close();

            return LeaderBoard;

        } catch (FileNotFoundException e) { //Aca podriamos usar expresiones lambda en vez de devolver null... wip
            System.out.println("read: File not found");
            return null;
        } catch (IOException e) {
            System.out.println("read: IO exception");
            return null;
        }
    }
}
