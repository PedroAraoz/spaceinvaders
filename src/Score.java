public class Score {

    private String name;
    private int score;
    private String serializeSplitParameter;

    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String serialize(){
        return name+serializeSplitParameter+score;
    }

    public void deserialize(String string){
        String[] splittedString = string.split(serializeSplitParameter);
        name = splittedString[0];
        score = (Integer) splittedString[1];
    }
}
