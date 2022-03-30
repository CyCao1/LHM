/**
 * This class represents a map of the game.
 */

import java.io.IOException;
import java.util.Random;

public class Map {
    private int size;
    private MapCell[][] gameMap;
    private Team team;
    public Map() {
        size = getInputSize();
        gameMap = new MapCell[size][size];
        initMap();
    }

    public void initMap(){
        gameMap[0][0] = new CommonCell();
        Random rnd = new Random(1);
        //inaccessible cell
        int inAccNum = (int) (size*size*0.2);
        for(int i = 0; i<inAccNum; i++){
            int tmp = rnd.nextInt(size*size);
            int row = tmp/size;
            int col = tmp%size;
            if(gameMap[row][col]==null)
                gameMap[row][col]=new InaccessibleCell();
        }
        //market cell
        int mktNum = (int) (size*size*0.3);
        for(int i = 0; i<mktNum; i++){
            int tmp = rnd.nextInt(size*size);
            int row = tmp/size;
            int col = tmp%size;
            if(gameMap[row][col]==null)
                gameMap[row][col]=new MarketCell();
        }
        //common cell
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++)
                if(gameMap[i][j]==null)
                    gameMap[i][j]=new CommonCell();
        }
    }

    public void printMap(int[] pos){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i<size; i++){
            s.append("+---".repeat(size) + "+\n");
            for(int j = 0; j<size; j++) {
                s.append("|");
                if(pos[0] == i && pos[1] == j)
                    s.append(" ◈ ");
                else s.append(gameMap[i][j].getChecker());
            }
            s.append("|\n");
        }
        s.append("+---".repeat(size)+"+");
        System.out.println(s);
    }

    public int getInputSize(){
        System.out.print("Please enter the size of the game board (8~15): ");
        int size = GameCenter.getNumeric();
        while(size<8||size>15){
            System.out.print("Please enter a valid number: ");
            size = GameCenter.getNumeric();
        }
        return size;
    }

    public int getSize() { return size; }

    public boolean moveTo(Team<Hero> team, int[] pos) throws IOException {
        return gameMap[pos[0]][pos[1]].moveTo(team, pos);
    }
}
