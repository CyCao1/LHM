/**
 * This class represents Hero's 3 stats: Strength, Agility and Dexterity
 */
public class Stats {
    private String statsName;
    private double coef;
    private int value;

    public Stats(String statsName, int value) {
        this.statsName = statsName;
        this.value = value;
        coef = 1.05;
    }

    public void setCoef(){
        coef = 1.1;
    }

    public void levelUp(){ value *= coef; }

    public int getValue() {
        return value;
    }

    public void setValue(int value) { this.value = value; }
}
