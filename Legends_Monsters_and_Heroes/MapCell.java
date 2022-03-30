/**
 * This class represents map cells, extended by 3 types of map cells:
 * common cells, market cells and inaccessible cells.
 */
import java.io.IOException;

public abstract class MapCell {
    private String checker;
    MapCell(String checker) {
        this.checker = checker;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public abstract boolean moveTo(Team<Hero> team, int[] pos) throws IOException;
}
