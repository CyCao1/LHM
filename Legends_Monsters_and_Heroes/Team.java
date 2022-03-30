/**
 * This class represents heroes' team.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team<Hero> {
    private int teamSize;
    private List<Hero> heroes;
    private int[] pos = new int[2];

    public List<Hero> getHeroes() {return heroes;}

    public void setHeroes(Hero hero) {this.heroes.add(hero);}

    public int[] getPos() {
        return new int[]{ pos[0], pos[1]};
    }
    public void setPos(int[] pos) {
        this.pos = pos;
    }

    public Team(int size, int row, int col) {
        this.heroes = new ArrayList<Hero>(size);
        this.teamSize = size;
        this.pos[0] = row;
        this.pos[1] = col;
    }

    public Team(int size) {
        this(size, 0, 0);
    }

    public Team(Hero[] heroes, int row, int col) {
        this.heroes = Arrays.asList(heroes);
        this.teamSize = heroes.length;
        this.pos[0] = row;
        this.pos[1] = col;
    }

    public int getTeamSize() { return teamSize; }

}
