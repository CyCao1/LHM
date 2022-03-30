/**
 * This class represents market cells on map, the hero team can buy or sell stuffs here.
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MarketCell extends MapCell{

    MarketCell(String checker) {
        super(checker);
    }

    MarketCell() {
        super("MKT");
    }

    @Override
    public boolean moveTo(Team<Hero> team, int[] pos) throws IOException {
        team.setPos(pos);
        System.out.println("Welcome to market!");
        while(true) {
            System.out.println("Please enter b to buy or s to sell, enter q to leave the market");
            char c = GameCenter.getChar();
            while (c != 'b' && c != 's' && c != 'q') {
                System.out.print("Please enter a valid char: ");
                c = GameCenter.getChar();
            }
            if (c == 'q') {
                System.out.println("You left the market.");
                break;
            } else if (c == 'b') {
                Hero h = chooseHero(team);
                buy(h);
            } else if (c == 's') {
                Hero h = chooseHero(team);
                sell(h);
            }
        }
        return true;
    }

    public void buy(Hero hero) throws IOException {
        System.out.println("Please enter a to buy armors, w to buy weapons, s to buy spells " +
                "or p to buy potions");
        char c = GameCenter.getChar();
        while(c!='a'&&c!='w'&&c!='s'&&c!='p') {
            System.out.print("Please enter a valid char: ");
            c = GameCenter.getChar();
        }
        if(c=='a'){
            String[] armorInfo = getSpecItem("Armory");
            Armor armor = new Armor(armorInfo);
            hero.buyItem(armor);
        } else if(c=='w') {
            String[] weaponInfo = getSpecItem("Weaponry");
            Weapon weapon = new Weapon(weaponInfo);
            hero.buyItem(weapon);
        } else if(c=='s') {
            System.out.println("Which kind of spell you want to buy?");
            System.out.println("Enter i to buy ice spells, f to buy fire spells, l to buy lightning spells.");
            char spellType = GameCenter.getChar();
            while(spellType!='i' && spellType!='f' && spellType!='l') {
                System.out.print("Please enter a valid char: ");
                spellType = GameCenter.getChar();
            }
            String[] spellInfo;
            if(spellType=='i')
                spellInfo = getSpecItem("IceSpells");
            else if(spellType=='f')
                spellInfo = getSpecItem("FireSpells");
            else
                spellInfo = getSpecItem("LightningSpells");
            Spell spell = new Spell(spellInfo);
            hero.buyItem(spell);
        } else if(c=='p') {
            String[] potionInfo = getSpecItem("Potions");
            Potion potion = new Potion(potionInfo);
            hero.buyItem(potion);
        }
    }

    public void sell(Hero hero) {
        System.out.println("Please enter a to sell armors or w to sell weapons.");
        char c = GameCenter.getChar();
        while (c != 'a' && c != 'w') {
            System.out.print("Please enter a valid char: ");
            c = GameCenter.getChar();
        }
        if (c == 'a') {
            ArrayList<Equipment> armors = hero.getInventory().getEquipments()[Inventory.itemType.get("Armor")];
            if (armors.size() == 0)
                System.out.println("This hero has no armor to sell.");
            else {
                for (int i = 0; i < armors.size(); i++)
                    System.out.println(i + 1 + " " + armors.get(i).getName());
                System.out.print("Please enter the id of the armor you want to sell(<size): ");
                int id = GameCenter.getNumeric();
                while (id < 1 || id > armors.size()) {
                    System.out.print("Please enter a valid id: ");
                    id = GameCenter.getNumeric();
                }
                Armor armor = (Armor) armors.get(id - 1);
                hero.setMoney(hero.getMoney() + armor.getPrice()/2);
                hero.getInventory().remove(armor);
                System.out.println(hero.getName() +" sold "+ armor.getName() +
                        " and get $" + armor.getPrice()/2 +", $" + hero.getMoney() + " left.");
            }
        } else if (c == 'w') {
            ArrayList<Equipment> weapons = hero.getInventory().getEquipments()[Inventory.itemType.get("Weapon")];
            if (weapons.size() == 0)
                System.out.println("This hero has no weapon to sell.");
            else {
                for (int i = 0; i < weapons.size(); i++)
                    System.out.println(i + 1 + " " + weapons.get(i).getName());
                System.out.print("Please enter the id of the weapon you want to sell(<size): ");
                int id = GameCenter.getNumeric();
                while (id < 1 || id > weapons.size()) {
                    System.out.print("Please enter a valid id: ");
                    id = GameCenter.getNumeric();
                }
                Weapon weapon = (Weapon) weapons.get(id - 1);
                hero.setMoney(hero.getMoney() + weapon.getPrice()/2);
                hero.getInventory().remove(weapon);
                System.out.println(hero.getName() +" sold "+ weapon.getName() +
                        " and get $" + weapon.getPrice()/2 +", $" + hero.getMoney() + " left.");
            }
        }
    }

    public String[] getSpecItem(String itemType) throws IOException {
        String filePath = System.getProperty("user.dir") + "/ConfigFiles/" + itemType +".txt";
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int index = 0;
        while ((line = br.readLine()) != null) {
            System.out.println(index + " " + line);
            index += 1;
        }
        System.out.println("Please choose 1 of the " + itemType.toLowerCase()+ " from list(<list size):");
        int itemId = GameCenter.getNumeric();
        if(itemId <1|| itemId >index){
            index -= 1;
            System.out.print("Please enter a number between 1 and " + index + ": ");
            itemId = GameCenter.getNumeric();
        }
        String item = Files.readAllLines(Paths.get(filePath)).get(itemId);
        String[] itemInfo = item.split("\\s+");
        return itemInfo;
    }

    public Hero chooseHero(Team<Hero> team){
        System.out.printf(" ID |    Hero Name    |\n");
        for (int i = 0; i < team.getTeamSize(); i++) {
            Hero h = team.getHeroes().get(i);
            System.out.printf(i+1 + "  " + h.getName() + "\n");
        }
        System.out.print("Please enter the id of the hero that wants to buy equipments(<team size): ");
        int id  = GameCenter.getNumeric();
        while(id<1||id>team.getTeamSize()) {
            System.out.print("Please enter a valid id: ");
            id = GameCenter.getNumeric();
        }
        Hero h = team.getHeroes().get(id-1);
        return h;
    }

}
