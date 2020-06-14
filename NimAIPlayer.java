/**
 * COMP90041 Programming and Software Developing Assignment 3:
 * Nim is a two-player game that the first one who remove the last one stone will lose
 *
 * @author Yuan
 * Name: Yuan Hung Lin
 * StudentID: 1119147
 * Username: yuanhungl
 */
/*
	NimAIPlayer.java

	This class is provided as a skeleton code for the tasks of
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks.
*/

public class NimAIPlayer extends NimPlayer implements Testable {
    // you may further extend a class or implement an interface
    // to accomplish the tasks.

    public NimAIPlayer(String userName, String familyName, String givenName) {
        super(userName, familyName, givenName);
    }

    // k belong to {0,1,2,3....}
    // rest of stone  = k(M+1)+1 , rival player move first
    // rest of stone != k(M+1)+1 , rival player move second
    // AI can win
    // If the winning condition does not hold, the AI player can remove any number of stones up to M,
    // and hope the other player makes a mistake.
    @Override
    public int removeStone() {
        System.out.printf("\n%s's turn - remove how many?\n", this.getGivenName());
        int M = NimGame.getUpperBound();
        int stones = NimGame.getInitialStones();
        if ((stones - 1) % (M + 1) != 0) {
            return ((stones - 1) % (M + 1));
        }
        // strategy isn't work, remove 1 stones and wait until strategy work
        else {
            return 1;
        }
    }

    public String advancedMove(boolean[] available, String lastMove) {
        // the implementation of the victory
        // guaranteed strategy designed by you
        String move = "";

        return move;
    }
}
