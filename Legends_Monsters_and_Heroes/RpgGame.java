/**
 * This class represents RpgGame, extended by Legends.
 */
import java.io.IOException;

public abstract class RpgGame {
    public RpgGame() {}
    public abstract void startGame() throws IOException;
    public abstract void printIntro();
}
