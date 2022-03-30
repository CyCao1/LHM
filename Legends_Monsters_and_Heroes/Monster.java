/**
 * This class represents monsters, extended by 3 types of monsters.
 */
public abstract class Monster {
    private String name;
    private int HP;
    private int defense;
    private int attack;
    private int dodgeChance;
    private int level;
    private String state;

    public Monster(String[] monsterData) {
        setName(monsterData[0]);
        setLevel(Integer.parseInt(monsterData[1]));
        attack = Integer.parseInt(monsterData[2]);
        defense = Integer.parseInt(monsterData[3]);
        dodgeChance = Integer.parseInt(monsterData[4]);
        setHP(getLevel()*100);
        setState("Alive");
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getHP() {
        return HP;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
