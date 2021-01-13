package donaldgame_v3;

import java.util.InputMismatchException;
import java.util.Scanner;
public class _main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n------Welcome to DONALD card game!------");

/*-----SELECT GAME MODES FOR DONALD GAME-----*/
        boolean repeat = true;
        do {
            System.out.println("Select game mode:");
            System.out.println("""
                    (1) Singleplayer
                    (2) 2 Players
                    (3) 3 Players
                    (4) 4 Players """);
            int opt1 = 0;
            try {
                opt1 = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number between 1 to 4!");
                input.next();
            }

            /*do the VS computer feature parts in case 1,2,3*/
            switch (opt1) {
                case 1 -> {
                    //(1 player, 3 computers)
                    _1Player mode1 = new _1Player();
                    mode1.play();
                    System.out.println();
                }
                case 2 -> {
                    //(2 players, 2 computers)
                    _2Players mode2 = new _2Players();
                    mode2.play();
                    System.out.println();
                }
                case 3 -> {
                    //(3 players, 1 computer)
                    _3Players mode3 = new _3Players();
                    mode3.play();
                    System.out.println();
                }
                case 4 -> {
                    //(4 players)
                    System.out.println("You picked 4 Players game mode!");
                    _4Players mode4 = new _4Players();
                    mode4.play();
                    repeat = false;
                }
                default -> {
                    System.out.println("Wrong command!\n");
                    repeat = true;
                }
            }
        }while(repeat);
    }
}
