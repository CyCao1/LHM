/**
 * This class represents game center, including color information and get keyboard input
 */
import java.util.Scanner;

public class GameCenter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static int getNumeric(){
        Scanner scan = new Scanner(System.in);
        boolean isNum = false;
        int num = 0;
        while(!isNum){
            try{
                num = scan.nextInt();
                isNum = true;
            }catch(Exception e){
                System.out.print("Please enter a number:");
                continue;
            }
        }
        return num;
    }

    public static char getChar(){
        Scanner scan = new Scanner(System.in);
        boolean isChar = false;
        char res = 'a';
        while(!isChar){
            try{
                res = scan.next().charAt(0);
                isChar = true;
            }catch(Exception e){
                System.out.print("Please enter a letter:");
                continue;
            }
        }
        return res;
    }
}
