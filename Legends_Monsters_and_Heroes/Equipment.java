/**
 * This class represents Hero's equipment, extended by all 4 types of equipments,
 * including armor, weapon, spell and potion
 */
public abstract class Equipment {
    private String name;
    private int price;
    private String type;
    private int reqLevel;
    private Hero hero;
    Equipment(String name, int price, int reqLevel) {
        this.name = name;
        this.price = price;
        this.reqLevel = reqLevel;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getReqLevel() {
        return reqLevel;
    }

    public abstract String getType();

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
