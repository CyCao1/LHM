/**
 * This class contains everything that happens in a fight, including attack,
 * cast spell, be attacked, win, recover, etc.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Fight {
    private Team<Hero> team;
    private Monster[] monsters;
    public Fight(Team<Hero> team) throws IOException {
        this.team = team;
        generateMonsters();
        fight();
    }

    public void fight(){
        System.out.println(GameCenter.ANSI_BLACK + GameCenter.ANSI_WHITE_BACKGROUND
                + "You encountered monsters!" + GameCenter.ANSI_RESET);
        printMonsterInfo();
        while(true){
            System.out.println(GameCenter.ANSI_RED + "Hero's turn!" + GameCenter.ANSI_RESET);
            for(int i = 0; i<team.getTeamSize(); i++) {
                Hero h = team.getHeroes().get(i);
                if(h.getHP()<=0) break;
                System.out.print("Please choose the movement for " + h.getName() + ".\n");
                System.out.print("1. Change armor\n" + "2. Change weapon\n" +
                        "3. Regular attack\n" + "4. Cast a spell\n" + "5. Use a potion\n");
                int action = GameCenter.getNumeric();
                if (action < 1 || action > 5) {
                    System.out.print("Please enter a number between 1 and 5: ");
                    action = GameCenter.getNumeric();
                }
                if(action==1) h.changeArmor();
                else if(action==2) h.changeWeapon();
                else if(action==3||action==4){
                    if(action==3) attack(h);
                    else castSpell(h);
                    if(isWin()){
                        win(team);
                        return;
                    }
                }
                else if(action==5) h.usePotion();
            }
            beAttacked();
            if(isLose()){
                System.out.println("All heroes died. They lost half of their money.");
                for (int i = 0; i < team.getTeamSize(); i++) {
                    Hero hero = team.getHeroes().get(i);
                    hero.setMoney(hero.getMoney()/2);
                }
            }
            recover(team);
        }
    }

    public void win(Team<Hero> team){
        System.out.println("Hero(s) win!");
        gainExpAndMoney(team);
        for(int i = 0; i<team.getTeamSize(); i++) {
            Hero hero = team.getHeroes().get(i);
            if(hero.getHP()==0) {
                hero.setHP(hero.getMaxHP()/2);
                System.out.println(hero.getName() + " is revived. His HP is now" + hero.getHP());
            }
        }
    }
    public int chooseTarget(Hero h){
        printMonsterInfo();
        System.out.print("Please choose the monster " + h.getName() + " want to attack(<=size): ");
        int targetId = GameCenter.getNumeric();
        while( targetId < 1 || targetId > monsters.length) {
            System.out.print("Please enter a number between 1 and" + monsters.length + ": ");
            targetId = GameCenter.getNumeric();
        }
        while(monsters[targetId-1].getState()=="Dead") {
            System.out.print("Please choose a living target: ");
            targetId = GameCenter.getNumeric();
        }
        return targetId-1;
    }

    public Monster initMonster(int level) throws IOException {
        Random rnd = new Random();
        int num = rnd.nextInt(3);
        String[] monsterInfo;
        if(num==1) {
            monsterInfo = getSpecMonster("Exoskeletons", level);
            return new Exoskeletons(monsterInfo);
        }
        else if(num==2) {
            monsterInfo = getSpecMonster("Spirits", level);
            return new Spirits(monsterInfo);
        }
        else if(num==0) {
            monsterInfo = getSpecMonster("Dragons", level);
            return new Dragons(monsterInfo);
        }

        return null;
    }

    public void attack(Hero h){
        int targetId = chooseTarget(h);
        Monster m = monsters[targetId];
        Random rnd = new Random();
        if(rnd.nextInt(100)<=m.getDodgeChance()) {
            System.out.printf("%s dodged %s's attack!\n", m.getName(), h.getName());
            return;
        }
        int damage = (int) (h.getStrengthVal()*0.05);
        if(h.getWeapon()!=null)
            damage += h.getWeapon().getDamage()*0.05;
        System.out.printf("%s attacked %s and did %d damage!\n", h.getName(), m.getName(), damage);
        int HP = m.getHP()-damage;
        if(HP<=0) {
            m.setHP(0);
            m.setState("Dead");
            System.out.println(m.getName() + " is dead.");
        }
        else {
            m.setHP(HP);
            System.out.println(m.getName() + " now has " + HP +" HP.");
        }
    }

    public void beAttacked(){
        System.out.println(GameCenter.ANSI_RED + "Monsters' turn!" + GameCenter.ANSI_RESET);
        Random rnd = new Random();
        for (int i = 0; i < monsters.length; i++) {
            Monster m = monsters[i];
            if(m.getHP()>0){
                int heroID = rnd.nextInt(team.getTeamSize());
                Hero h = team.getHeroes().get(heroID);
                if(rnd.nextInt(100)<=h.getAgilityVal()*0.002) {
                    System.out.printf("%s dodged %s's attack!\n", h.getName(), m.getName());
                    return;
                }
                int damage = (int) (m.getAttack()*0.05);
                if(h.getArmor()!=null)
                    damage -= h.getArmor().getDamageReduction()*0.05;
                System.out.printf("%s attacked %s and did %d damage!\n", m.getName(), h.getName(), damage);
                int HP = h.getHP()-damage;
                if(HP<=0) {
                    h.setHP(0);
                    System.out.println(h.getName() + " is fainted.");
                }
                else {
                    h.setHP(HP);
                    System.out.println(h.getName() + " now has " + HP + " HP.");
                }
            }
        }
    }

    public void castSpell(Hero h){
        ArrayList<Equipment> spells = h.getInventory().getEquipments()[Inventory.itemType.get("Spell")];
        if(spells.size()==0) System.out.println("This hero has no spell to cast.");
        else {
            for (int i = 0; i < spells.size(); i++)
                System.out.println(i + 1 + " " + spells.get(i).getName());
            System.out.print("Please enter the id of the spell you choose(<size): ");
            int id = GameCenter.getNumeric();
            while (id < 1 || id > spells.size()) {
                System.out.print("Please enter a valid id: ");
                id = GameCenter.getNumeric();
            }
            int targetId = chooseTarget(h);
            Monster m = monsters[targetId];
            Spell spell = (Spell) spells.get(id-1);
            int damage = spell.getDamage() * (h.getDexterityVal()/10000 + 1);
            System.out.printf("%s cast %s to %s and did %d damage!\n",
                    h.getName(), spell.getName(), m.getName(), damage);
            h.setMana(h.getMana()-spell.getManaCost());
            int HP = m.getHP()-damage;
            if(HP<=0) {
                m.setHP(0);
                m.setState("Dead");
                System.out.println(m.getName() + " is dead.");
            }
            else {
                m.setHP(HP);
                System.out.println(m.getName() + " now has " + HP +" HP.");
            }
        }
    }

    public void recover(Team<Hero> team){
        for (int i = 0; i < team.getTeamSize(); i++) {
            Hero hero = team.getHeroes().get(i);
            if(hero.getHP()>0 && hero.getHP()<hero.getMaxHP()) {
                int HP = hero.getHP() + hero.getMaxHP() / 10;
                hero.setHP(HP > hero.getMaxHP() ? hero.getMaxHP() : HP);
                System.out.printf("%s's HP recovered to %d.\n", hero.getName(), hero.getHP());
            }
            if(hero.getMana()<hero.getMaxMana()) {
                int mana = hero.getMana() + hero.getMaxMana() / 10;
                hero.setMana(mana > hero.getMaxMana() ? hero.getMaxMana() : mana);
                System.out.printf("%s's mana recovered to %d.\n", hero.getName(), hero.getMana());
            }
        }
    }

    public void gainExpAndMoney(Team<Hero> team){
        for (int i = 0; i < team.getTeamSize(); i++){
            Hero hero = team.getHeroes().get(i);
            hero.setMoney(hero.getMoney() + 100*team.getTeamSize());
            int exp = hero.getExp() + 2*team.getTeamSize();
            if(exp > hero.getLevel()*10) {
                hero.setExp(exp-hero.getLevel()*10);
                hero.levelUp();
            }
            else hero.setExp(exp);
        }
    }

    public void generateMonsters() throws IOException {
        monsters = new Monster[team.getTeamSize()];
        for (int i = 0; i < team.getTeamSize(); i++)
            monsters[i] = initMonster(team.getHeroes().get(i).getLevel());
    }

    public String[] getSpecMonster(String type, int level) throws IOException {
        String filePath = System.getProperty("user.dir") + "/ConfigFiles/" + type +".txt";
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String[]> monstersInfo = new ArrayList();
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] monsterInfo = line.split("\\s+");
            if(Integer.parseInt(monsterInfo[1])<=level)
                monstersInfo.add(monsterInfo);
        }
        Random rnd = new Random();
        int num = rnd.nextInt(monstersInfo.size());
        return monstersInfo.get(num);
    }

    public void printMonsterInfo(){
        System.out.println(GameCenter.ANSI_RED_BACKGROUND + "MONSTERS INFORMATION"
                + GameCenter.ANSI_RESET);
        System.out.println("ID | Monster Name | Lv | ATK | DEF | HP | DodgeChance");
        for (int i = 0; i < monsters.length; i++) {
            Monster monster = monsters[i];
            System.out.printf(i+1 + "  " + monster.getName()
                    + " " + monster.getLevel() + "  " + monster.getAttack() + "  " +
                    monster.getDefense() + "  " + monster.getHP() + "  " +
                    monster.getDodgeChance() + "\n" );
        }
    }

    public Boolean isWin(){
        for (int i = 0; i < monsters.length; i++) {
            Monster monster = monsters[i];
            if(monster.getHP()>0) return false;
        }
        return true;
    }

    public Boolean isLose(){
        for (int i = 0; i < team.getTeamSize(); i++) {
            Hero hero = team.getHeroes().get(i);
            if(hero.getHP()<=0) return true;
        }
        return false;
    }
}
