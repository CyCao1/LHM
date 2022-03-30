/**
 * This class represents Paladin, favors on Strength and Dexterity, extends Hero.
 */
public class Paladin extends Hero{
    public Paladin(String[] heroData, String type) {
        super(heroData, type);
        getStrength().setCoef();
        getDexterity().setCoef();
    }
}
