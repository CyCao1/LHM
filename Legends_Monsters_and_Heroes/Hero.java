/**
 * This class represents Hero, extended by 3 type of heroes,
 * including moves of heroes like buying equipment and changing equipment
 */
import java.util.ArrayList;
public abstract class Hero {
    private int level;
    private String name;
    private int HP;
    private int exp;
    private int attack;
    private int mana;
    private Stats strength;
    private Stats agility;
    private Stats dexterity;
    private String type;
    private int money;
    private int maxHP;
    private int maxMana;
    private Weapon weapon;
    private Armor armor;
    private Inventory inventory = new Inventory(this);

    Hero(String[] heroData, String type){
        setName(heroData[0]);
        mana = maxMana = Integer.parseInt(heroData[1]);
        strength = new Stats("strength", Integer.parseInt(heroData[2]));
        agility = new Stats("agility", Integer.parseInt(heroData[3]));
        dexterity = new Stats("dexterity", Integer.parseInt(heroData[4]));
        money = Integer.parseInt(heroData[5]);
        exp = Integer.parseInt(heroData[6]);
        this.type = type;
        setLevel(1);
        maxHP = getLevel() * 100;
        setHP(maxHP);
    }

    public void buyItem(Equipment item){
        if (item.getPrice() > money){
            System.out.println("This hero doesn't have enough money.");
        } else if (item.getReqLevel() > getLevel()) {
            System.out.println("This hero doesn't meet required level.");
        } else {
            inventory.add(item);
            item.setHero(this);
            money -= item.getPrice();
            System.out.println(name +" bought "+ item.getName() +
                    " and it costs $" + item.getPrice() +", $" + money + " left.");
        }
    }

    public void changeArmor(){
        ArrayList<Equipment> armors = inventory.getEquipments()[0];
        if(armors.size()==0) System.out.println("This hero has no armor to change.");
        else {
            for (int i = 0; i<armors.size(); i++)
                System.out.println(i+1 + " " + armors.get(i).getName() + "\n");
            System.out.print("Please enter the id of the armor you choose(<size): ");
            int id  = GameCenter.getNumeric();
            while(id<1||id>armors.size()) {
                System.out.print("Please enter a valid id: ");
                id = GameCenter.getNumeric();
            }
            if(armor!=null)
                System.out.printf(GameCenter.ANSI_GREEN +
                                "%s put off %s and put on %s." + GameCenter.ANSI_RESET + "\n",
                        name, armor.getName(), armors.get(id-1).getName());
            else
                System.out.printf(GameCenter.ANSI_GREEN + "%s put on %s." + GameCenter.ANSI_RESET
                        +"\n", name, armors.get(id-1).getName());
            armor = (Armor) armors.get(id-1);
        }
    }

    public void changeWeapon(){
        ArrayList<Equipment> weapons = inventory.getEquipments()[Inventory.itemType.get("Weapon")];
        if(weapons.size()==0) System.out.println("This hero has no weapon to change.");
        else {
            for (int i = 0; i<weapons.size(); i++)
                System.out.println(i+1 + " " + weapons.get(i).getName());
            System.out.print("Please enter the id of the weapon you choose(<size): ");
            int id  = GameCenter.getNumeric();
            while(id<1||id>weapons.size()) {
                System.out.print("Please enter a valid id: ");
                id = GameCenter.getNumeric();
            }
            if(weapon!=null) {
                System.out.printf(GameCenter.ANSI_YELLOW + "%s unequipped %s and equipped %s."
                                + GameCenter.ANSI_RESET + "\n",
                        name, weapon.getName(), weapons.get(id - 1).getName());
                inventory.add(weapon);
            }
            else
                System.out.printf(GameCenter.ANSI_YELLOW + "%s equipped %s"+
                        GameCenter.ANSI_RESET + ".\n", name, weapons.get(id - 1).getName());
            weapon = (Weapon) weapons.get(id-1);
            inventory.remove(weapons.get(id-1));
        }
    }

    public void usePotion(){
        ArrayList<Equipment> potions = inventory.getEquipments()[Inventory.itemType.get("Potion")];
        if(potions.size()==0) System.out.println("This hero has no potion to cast.");
        else {
            for (int i = 0; i < potions.size(); i++)
                System.out.println(i + 1 + " " + potions.get(i).getName());
            System.out.print("Please enter the id of the potion you choose(<size): ");
            int id = GameCenter.getNumeric();
            while (id < 1 || id > potions.size()) {
                System.out.print("Please enter a valid id: ");
                id = GameCenter.getNumeric();
            }
            Potion potion = (Potion) potions.get(id-1);
            potion.use(this);
            inventory.remove(potion);
        }
    }

    public void levelUp(){
        setLevel(getLevel()+1);
        maxHP = getLevel() * 100;
        maxMana *= 1.1;
        setHP(maxHP);
        agility.levelUp();
        dexterity.levelUp();
        strength.levelUp();
        System.out.printf( GameCenter.ANSI_YELLOW_BACKGROUND +
                        "%s level up! Level: %d, Strength: %d, Agility: %d, Dexterity: %d"
                        + GameCenter.ANSI_RESET +"\n",
                name, getLevel(), strength.getValue(), agility.getValue(), dexterity.getValue());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() { return armor; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getHP() { return HP; }

    public void setHP(int HP) {this.HP = HP;}

    public int getExp() {return exp;}

    public void setExp(int exp) {this.exp = exp;}

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMana() {return mana;}

    public void setMana(int mana) {this.mana = mana;}

    public Stats getStrength() {return strength;}

    public Stats getAgility() {
        return agility;
    }

    public Stats getDexterity() { return dexterity; }

    public int getStrengthVal() { return strength.getValue(); }

    public int getAgilityVal() {
        return agility.getValue();
    }

    public int getDexterityVal() { return dexterity.getValue(); }

    public void setStrengthVal(int strength) {
        this.strength.setValue(strength);
    }

    public void setAgilityVal(int agility) { this.agility.setValue(agility); }

    public void setDexterityVal(int dexterity) { this.dexterity.setValue(dexterity); }

    public int getMoney() {return money;}

    public void setMoney(int money) { this.money = money; }

    public int getMaxHP() {return maxHP;}

    public void setMaxHP(int maxHP) { this.maxHP = maxHP; }

    public int getMaxMana() {return maxMana;}

    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

}
