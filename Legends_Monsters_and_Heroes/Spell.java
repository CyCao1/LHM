/**
 * This class represents Spell, extends Equipment.
 */
public class Spell extends Equipment{
    private int damage;
    private int manaCost;
    private String type = "Spell";
    Spell(String[] info) {
        super(info[0],Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        this.damage = Integer.parseInt(info[3]);
        this.manaCost = Integer.parseInt(info[4]);
    }

    @Override
    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }
}
