/**
 * This class represents our game: Legends: Heroes and Monsters, extend RPGgame,
 * contains basic game processes.
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Legends extends RpgGame{

    private Map map;
    private Team<Hero> team;
    private int size;


    public Legends() throws IOException {
        printIntro();
        map = new Map();
        team = new Team<>(getInputTeamSize(),0,0);
        for(int i = 0; i<team.getTeamSize(); i++){
            Hero hero = chooseHero();
            team.setHeroes(hero);
            System.out.printf(hero.getName() + " joins the team!\n");
        }
        size = map.getSize();
    }


    public void startGame() throws IOException {
        map.printMap(new int[]{0, 0});
        while(true){
            int[] pos = team.getPos().clone();
            System.out.print("Please enter your next move(w/a/s/d): ");
            char nxt = GameCenter.getChar();
            while(nxt!='a'&&nxt!='w'&&nxt!='s'&&nxt!='d') {
                System.out.print("Please enter a valid char: ");
                nxt = GameCenter.getChar();
            }
            nxt = checkBound(nxt, pos);
            if(nxt=='w') pos[0]--;
            if(nxt=='a') pos[1]--;
            if(nxt=='s') pos[0]++;
            if(nxt=='d') pos[1]++;
            if(map.moveTo(team, pos))
                map.printMap(team.getPos());
        }
    }

    public char checkBound(char nxt, int[] pos) {
        while((nxt=='w' && pos[0]== 0) || (nxt=='a' && pos[1]== 0)
                ||(nxt=='s' && pos[0]== size-1)||(nxt=='d' && pos[1]== size-1)) {
            System.out.print("Out of Boundary! Please enter another move: ");
            nxt = GameCenter.getChar();
        }
        return nxt;
    }

    public void printIntro(){
        System.out.println(GameCenter.ANSI_BLACK + GameCenter.ANSI_WHITE_BACKGROUND
                + "Welcome to Legends: Monsters and Heroes!" + GameCenter.ANSI_RESET);
        System.out.println(GameCenter.ANSI_BLACK + GameCenter.ANSI_WHITE_BACKGROUND
                + "You will be playing heroes against monsters." + GameCenter.ANSI_RESET);
    }

    public int getInputTeamSize(){
        System.out.print("Please enter the size of your team(1~3): ");
        int size = GameCenter.getNumeric();
        while(size<1||size>3){
            System.out.print("Please enter a valid number: ");
            size = GameCenter.getNumeric();
        }
        return size;
    }

    public Hero chooseHero() throws IOException {
        //choose occupation
        System.out.println("1. Warrior: favored on strength and agility.");
        System.out.println("2. Sorcerer: favored on dexterity and agility.");
        System.out.println("3. Paladin: favored on strength and dexterity.");
        System.out.print("Please choose 1 of the 3 occupations(enter 1~3): ");
        int occupation = GameCenter.getNumeric();
        if(occupation<1||occupation>3){
            System.out.print("Please enter a number between 1 and 3: ");
            occupation = GameCenter.getNumeric();
        }
        String[] heroInfo;
        String type;
        if(occupation==1) {
            type = "Warriors";
            heroInfo = getSpecHero(type);
            return new Warrior(heroInfo, type);
        }
        else if(occupation==2) {
            type = "Sorcerers";
            heroInfo = getSpecHero(type);
            return new Sorcerer(heroInfo, type);
        }
        else {
            type = "Paladins";
            heroInfo = getSpecHero(type);
            return new Paladin(heroInfo, type);
        }
    }

    public String[] getSpecHero(String occupation) throws IOException {
        String filePath = System.getProperty("user.dir") + "/ConfigFiles/" + occupation +".txt";
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        System.out.println("ID/" + line);
        int index = 1;
        while ((line = br.readLine()) != null) {
            System.out.println(index + " " + line);
            index += 1;
        }

        System.out.println("Please choose 1 of the " + occupation+ " from list(1~6):");
        int heroId = GameCenter.getNumeric();
        if(heroId<1||heroId>6){
            System.out.print("Please enter a number between 1 and 6: ");
            heroId = GameCenter.getNumeric();
        }
        String hero = Files.readAllLines(Paths.get(filePath)).get(heroId);
        String[] heroInfo = hero.split("\\s+");
        return heroInfo;
    }

}
