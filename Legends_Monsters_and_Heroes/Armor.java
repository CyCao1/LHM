/**
 * This class represents Hero's Armor, extends Equipments
 */
public class Armor extends Equipment{
    private int damageReduction;
    private String type = "Armor";
    public Armor(String[] info) {
        super(info[0],Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        this.damageReduction = Integer.parseInt(info[3]);
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    public String getType(){
        return type;
    }
}
