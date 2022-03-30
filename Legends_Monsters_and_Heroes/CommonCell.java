/**
 * This class represents common cells on map, extends map cells. When the team enter
 * a common cell, they have 50% chance to encounter monsters, 50% chance to be safe.
 */
import java.io.IOException;
import java.util.Random;

public class CommonCell extends MapCell{
    CommonCell(String checker) {
        super(checker);
    }

    public CommonCell() {
        super("   ");
    }

    @Override
    public boolean moveTo(Team<Hero> team, int[] pos) throws IOException {
        team.setPos(pos);
        Random rnd = new Random();
        int chance = rnd.nextInt(100);
        if(chance<50){ new Fight(team); }
        else safeCell(team);
        return true;
    }

    public void safeCell(Team<Hero> team){
        System.out.println(GameCenter.ANSI_BLACK + GameCenter.ANSI_WHITE_BACKGROUND +
                "You entered a safe cell!" + GameCenter.ANSI_RESET);
        while(true) {
            System.out.println("a: Change armor\n" + "w: Change weapon\n" + "p: Use Potion\n"
                    + "m: Show map\n" + "i: Show info\n" +"l: Leave the cell\n" + "q: Quit the game" );
            System.out.print("Please enter an action: ");
            char c = GameCenter.getChar();
            while (c != 'a' && c != 'w' && c != 'm' && c != 'i' && c != 'q' && c != 'p' && c != 'l') {
                System.out.print("Please enter a valid char: ");
                c = GameCenter.getChar();
            }
            if (c == 'i') printInfo(team);
            else if (c == 'm') break;
            else if (c == 'l') break;
            else if (c == 'q') System.exit(0);
            else if (c == 'a') {
                Hero h = getHero(team);
                h.changeArmor();
            } else if (c == 'w') {
                Hero h = getHero(team);
                h.changeWeapon();
            } else if (c == 'p') {
                Hero h = getHero(team);
                h.usePotion();
            }
        }
    }

    public void printInfo(Team<Hero> team) {
        System.out.printf(GameCenter.ANSI_CYAN_BACKGROUND + "TEAM INFORMATION" +
                GameCenter.ANSI_RESET +"\n");
        System.out.printf(GameCenter.ANSI_CYAN_BACKGROUND + "    Hero Name    | Lv | HP | Mana | EXP " +
                "| $$ | Str | Agi | Dex"+ GameCenter.ANSI_RESET + "\n");
        for (int i = 0; i < team.getTeamSize(); i++) {
            Hero h = team.getHeroes().get(i);
            System.out.printf(GameCenter.ANSI_BLUE_BACKGROUND + " " +h.getName() + " " +
                    h.getLevel() + "  " + h.getHP() + "  " + h.getMana() + "  " + h.getExp() +
                    "  " + h.getMoney() + "  " + h.getStrengthVal() + "  " +
                    h.getAgilityVal() + "  " + h.getDexterityVal()  +GameCenter.ANSI_RESET + "\n");
        }
    }

    public Hero getHero(Team<Hero> team) {
        System.out.printf(" ID |    Hero Name    |\n");
        for (int i = 0; i < team.getTeamSize(); i++) {
            Hero h = team.getHeroes().get(i);
            System.out.printf(i+1 + "  " + h.getName() + "\n");
        }
        System.out.print("Please enter the id of the hero that wants to change equipment(<team size): ");
        int id  = GameCenter.getNumeric();
        while(id<1||id>team.getTeamSize()) {
            System.out.print("Please enter a valid id: ");
            id = GameCenter.getNumeric();
        }
        return team.getHeroes().get(id-1);
    }

}