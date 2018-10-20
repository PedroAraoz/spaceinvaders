package edu.austral.prog2_2018c2;

public class Score implements Comparable{

    private String name;
    private int score;

    private static String serializeSplitParameter = ":";

    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String serialize(){
        return name+serializeSplitParameter+score;
    }

    @Override
    public int compareTo(Object o) {
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

    public static Score deserialize(String string){
        String[] splittedString = string.split(serializeSplitParameter);
        return new Score(splittedString[0], Integer.parseInt(splittedString[1]));
    }


}
