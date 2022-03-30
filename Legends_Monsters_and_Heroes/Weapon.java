/**
 * This class represents Potion, extends Equipment.
 */
public class Weapon extends Equipment{
    private int damage;
    private int reqHands;
    private String type = "Weapon";
    Weapon(String[] info) {
        super(info[0],Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        this.damage = Integer.parseInt(info[3]);
        this.reqHands = Integer.parseInt(info[4]);
    }

    @Override
    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }
}
