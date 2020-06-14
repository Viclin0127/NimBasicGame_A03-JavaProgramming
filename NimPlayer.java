import java.io.Serializable;

/**
 * COMP90041 Programming and Software Developing Assignment 3:
 * Nim is a two-player game that the first one who remove the last one stone will lose
 *
 * @author Yuan
 * Name: Yuan Hung Lin
 * StudentID: 1119147
 * Username: yuanhungl
 */

public abstract class NimPlayer implements Serializable {

    // set variable names
    private String userName;
    private String givenName;
    private String familyName;
    private int numOfGames;
    private int numOfWins;

    /**
     * constructor
     *
     * @param userName
     * @param familyName
     * @param givenName
     */
    public NimPlayer(String userName, String familyName, String givenName) {
        this.userName = userName;
        this.familyName = familyName;
        this.givenName = givenName;
        numOfWins = 0;
        numOfGames = 0;
    }

    /**
     * This function would be overrode in derive class
     */
    public abstract int removeStone();

    /**
     * This function returns player's username which is a String variable
     *
     * @return
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * This function returns player's family name which is a String variable
     *
     * @return
     */
    public String getFamilyName() {
        return this.familyName;
    }

    /**
     * This function returns player's given name which is a String variable
     *
     * @return
     */
    public String getGivenName() {
        return this.givenName;
    }

    /**
     * This function returns the amount of games that player has already played.
     *
     * @return
     */
    public int getNumOfGames() {
        return this.numOfGames;
    }

    /**
     * This function could revise the number of games that player has already played
     *
     * @param input
     */
    public void setNumOfGames(int input) {
        this.numOfGames = input;
    }

    /**
     * This function could add 1 numOfGames
     */
    public void addNumOfGames() {
        this.numOfGames += 1;
    }

    /**
     * This function could revise the number of games which player has already win
     *
     * @param input
     */
    public void setNumOfWins(int input) {
        this.numOfWins = input;
    }

    /**
     * This function could add 1 numOfWins
     */
    public void addNumOfWins() {
        this.numOfWins += 1;
    }

    /**
     * This function could get a player's wining ratio
     * If numOfGames equals to 0 , then return 0.0
     *
     * @return
     */
    public double getWinRatio() {
        if (this.numOfGames == 0) {
            return 0.0;
        } else {
            return this.numOfWins / (double) this.numOfGames;
        }
    }

    /**
     * This function could revise a player's family name and given name
     *
     * @param familyName
     * @param givenName
     */
    public void editPlayerName(String familyName, String givenName) {
        this.familyName = familyName;
        this.givenName = givenName;
    }

    /**
     * This method could print out a certain format of displaying players data
     */
    public void display() {
        System.out.println(this.userName + "," + this.givenName + "," +
                this.familyName + "," + this.numOfGames + " games," +
                this.numOfWins + " wins");
    }

}
