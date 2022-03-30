/**
 * This class represents Potion, extends Equipment.
 */
public class Potion extends Equipment{
    private int attribute_increase;
    private String attribute_affected;
    private String type = "Potion";
    Potion(String[] info) {
        super(info[0],Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        this.attribute_increase = Integer.parseInt(info[3]);
        this.attribute_affected = info[4];
    }

    @Override
    public String getType() {
        return type;
    }

    public void use(Hero h) {
        if (attribute_affected.contains("HP")){
            h.setHP(h.getHP() + attribute_increase);
            System.out.printf("%s's HP increases to %d\n", h.getName(), h.getHP());
        }
        if (attribute_affected.contains("Mana")){
            h.setMana(h.getMana() + attribute_increase);
            System.out.printf("%s's mana increases to %d\n", h.getName(), h.getMana());
        }
        if (attribute_affected.contains("Strength")) {
            h.setStrengthVal(h.getStrengthVal() + attribute_increase);
            System.out.printf("%s's strength increases to %d\n", h.getName(), h.getStrengthVal());
        }
        if (attribute_affected.contains("Agility")) {
            h.setAgilityVal(h.getAgilityVal() + attribute_increase);
            System.out.printf("%s's agility increases to %d\n", h.getName(), h.getAgilityVal());
        }
        if (attribute_affected.contains("Dexterity")) {
            h.setDexterityVal(h.getDexterityVal() + attribute_increase);
            System.out.printf("%s's dexterity increases to %d\n", h.getName(), h.getDexterityVal());
        }
    }
}
