package edu.austral.prog2_2018c2;

public class Score {

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

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public static Score deserialize(String string){
        String[] splittedString = string.split(serializeSplitParameter);
        return new Score(splittedString[0], splittedString[1]);
    }
}
