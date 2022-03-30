/**
 * This class represents inventory, stores 4 type of equipment,
 * every hero has independent inventory.
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private Hero hero;
    private ArrayList<Equipment>[] equipments = new ArrayList[4];
    public static HashMap<String, Integer> itemType = new HashMap<>();

    static {
        itemType.put("Armor", 0);
        itemType.put("Weapon", 1);
        itemType.put("Potion", 2);
        itemType.put("Spell", 3);
        itemType.put("IceSpell", 3);
        itemType.put("LightningSpell", 3);
        itemType.put("FireSpell", 3);
    }

    public Inventory(Hero hero) {
        this.hero = hero;
        for (int i = 0; i < equipments.length; i++)
            equipments[i] = new ArrayList<>();
    }

    public void add(Equipment e) {
        equipments[itemType.get(e.getType())].add(e);
    }

    public void remove(Equipment e) {
        equipments[itemType.get(e.getType())].remove(e);
    }

    public ArrayList<Equipment>[] getEquipments() {
        return equipments;
    }
}
