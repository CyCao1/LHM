/**
 * This class represents Paladin, favors on Strength and Agility, extends Hero.
 */
public class Warrior extends Hero{
    public Warrior(String[] heroData, String type) {
        super(heroData, type);
        getStrength().setCoef();
        getAgility().setCoef();
    }
}
