/**
 * COMP90041 Programming and Software Developing Assignment 3:
 * Nim is a two-player game that the first one who remove the last one stone will lose
 *
 * @author Yuan
 * Name: Yuan Hung Lin
 * StudentID: 1119147
 * Username: yuanhungl
 */
public class NimHumanPlayer extends NimPlayer {

    public NimHumanPlayer(String userName, String familyName, String givenName) {
        super(userName, familyName, givenName);
    }

    @Override
    public int removeStone() {
        System.out.printf("\n%s's turn - remove how many?\n", this.getGivenName());
        return Nimsys.scan.nextInt();
    }
}
