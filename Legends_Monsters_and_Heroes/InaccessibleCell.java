/**
 * This class represents common cells on map, extends map cells. The team
 * cannot enter inaccessible cells
 */
public class InaccessibleCell extends MapCell{
    InaccessibleCell(String checker) {
        super(checker);
    }

    InaccessibleCell() {
        super("███");
    }

    @Override
    public boolean moveTo(Team<Hero> team, int[] pos) {
        System.out.println("It's an inaccessible cell. Please enter another move. ");
        return false;
    }
}
