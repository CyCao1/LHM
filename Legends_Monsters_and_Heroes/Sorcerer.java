/**
 * This class represents Sorcerer, favors on Agility and Dexterity, extends Hero.
 */
public class Sorcerer extends Hero{
    public Sorcerer(String[] heroData, String type) {
        super(heroData, type);
        getAgility().setCoef();
        getDexterity().setCoef();
    }
}