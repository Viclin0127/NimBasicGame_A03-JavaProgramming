/**
 * COMP90041 Programming and Software Developing Assignment 3:
 * Nim is a two-player game that the first one who remove the last one stone will lose
 *
 * @author Yuan
 * Name: Yuan Hung Lin
 * StudentID: 1119147
 * Username: yuanhungl
 */

public class NimGame {
    private static int initialStones;
    private static int upperBound;

    /**
     * Constructor / NimGame class could create a NimGame with 4 parameters
     *
     * @param initialStones
     * @param upperBound
     * @param player1
     * @param player2
     */
    public void NimGame(int initialStones, int upperBound, NimPlayer player1, NimPlayer player2) {
        this.initialStones = initialStones;
        this.upperBound = upperBound;
        player1.addNumOfGames();
        player2.addNumOfGames();

        System.out.println();
        System.out.println("Initial stone count: " + initialStones);
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.println("Player 1: " + player1.getGivenName() + " " + player1.getFamilyName());
        System.out.println("Player 2: " + player2.getGivenName() + " " + player2.getFamilyName());

        // recursively play game
        // create turns variable in order to play game in turns
        int turns = 1;
        while (initialStones != 0) {
            checkStones(initialStones);

            if (upperBound > initialStones) {
                upperBound = initialStones;
            }

            if ((turns % 2) != 0) {         // if turns is odd, it's player1's turn
                int out = player1.removeStone();
                if (out >= 1 && out <= upperBound) {
                    initialStones -= out;
                    turns++;
                } else {
                    System.out.println();
                    System.out.printf("Invalid move. You must remove between 1 and %d stones.", upperBound);
                    System.out.println();
                }
            } else {                         // if turns is even, it's player2's turn
                int out = player2.removeStone();
                if (out >= 1 && out <= upperBound) {
                    initialStones -= out;
                    turns++;
                } else {
                    System.out.println();
                    System.out.printf("Invalid move. You must remove between 1 and %d stones.", upperBound);
                    System.out.println();
                }
            }
        }
        // game over
        System.out.println("\nGame Over");

        // print the winner
        if ((turns % 2) == 0) {
            String winner = player2.getGivenName() + " " + player2.getFamilyName() + " wins!";
            System.out.println(winner);
            player2.addNumOfWins();
        } else {
            String winner = player1.getGivenName() + " " + player1.getFamilyName() + " wins!";
            System.out.println(winner);
            player1.addNumOfWins();
        }
    }

    /**
     * This is a function to print the total stones
     *
     * @param initialStones
     */
    public void checkStones(int initialStones) {
        System.out.printf("\n%d stones left:", initialStones);
        for (int i = 0; i < initialStones; i++) {
            System.out.print(" *");
        }
    }

    public static int getInitialStones() {
        return initialStones;
    }

    public static int getUpperBound() {
        return upperBound;
    }
}
