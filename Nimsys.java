/**
 * COMP90041 Programming and Software Developing Assignment 3:
 * Nim is a two-player game that the first one who remove the last one stone will lose
 *
 * @author Yuan
 * Name: Yuan Hung Lin
 * StudentID: 1119147
 * Username: yuanhungl
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Nimsys {
    public static Scanner scan = new Scanner(System.in);
    public static NimPlayer[] players = new NimPlayer[100];
    public static int playerNo = 0;
    private static List<String> commandArray = Arrays.asList(
            "startgame", "addplayer", "removeplayer", "editplayer", "resetstats"
            , "displayplayer", "rankings", "exit", "addaiplayer");

    public static void main(String[] args) {

        // create file "player.dat" if file does not exist
        // if file has already existed, read the data in file into players array
        File f = new File("player.dat");
        if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream("player.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                players = (NimPlayer[]) ois.readObject();
                for (int i = 0; i < players.length; i++) {
                    if (players[i] != null) {
                        playerNo++;
                    }
                }
                ois.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        // welcome sentence.
        System.out.print("Welcome to Nim\n");
        commandFormat();
        Nimsys nim = new Nimsys();

        while (true) {
            // in order to check command and argument exception
            // use try catch method
            try {
                String command = scan.next();
                if (!commandArray.contains(command)) {
                    scan.nextLine();
                    throw new InvalidCommandException(command);
                }
                if (command.equals("startgame")) {
                    String input = scan.nextLine();
                    input = input.trim();
                    String[] array = input.split(",");
                    if (array.length < 4) {
                        throw new InvalidArgumentException();
                    }
                    nim.startGame(Integer.parseInt(array[0]), Integer.parseInt(array[1]), array[2], array[3]);
                }

                if (command.equals("addplayer")) {
                    String input = scan.next();
                    String[] array = input.split(",");
                    if (array.length < 3) {
                        throw new InvalidArgumentException();
                    }
                    nim.addPlayer(array[0], array[1], array[2]);
                }

                if (command.equals("addaiplayer")) {
                    String input = scan.next();
                    String[] array = input.split(",");
                    if (array.length < 3) {
                        throw new InvalidArgumentException();
                    }
                    nim.addAiPlayer(array[0], array[1], array[2]);
                }

                if (command.equals("removeplayer")) {
                    String input = scan.nextLine();
                    nim.removePlayer(input);
                }

                if (command.equals("editplayer")) {
                    String input = scan.next();
                    String[] array = input.split(",");
                    if (array.length < 3) {
                        throw new InvalidArgumentException();
                    }
                    nim.editPlayer(array[0], array[1], array[2]);
                }

                if (command.equals("resetstats")) {
                    String input = scan.nextLine();
                    nim.resetstats(input);
                }

                if (command.equals("displayplayer")) {
                    String input = scan.nextLine();
                    nim.displayPlayer(input);
                }

                if (command.equals("rankings")) {
                    String input = scan.nextLine();
                    nim.rankings(input);
                }

                // before exiting the project, we have to save our players data into file "player.dat"
                if (command.equals("exit")) {
                    try {
                        FileOutputStream fos = new FileOutputStream("player.dat");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(players);
                        oos.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    System.exit(0);
                }
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
                commandFormat();
            } catch (InvalidArgumentException e) {
                System.out.println(e.getMessage());
                commandFormat();
            }
        }
    }

    /**
     * format to specific requirement of assignment
     */
    private static void commandFormat() {
        System.out.println();
        System.out.print("$");
    }

    /**
     * This method will be used when command equals to "addplayer"
     * Create a new player in array if it doesn't exist
     * If it has already existed in players[], print "The player already exists."
     *
     * @param userName
     * @param familyName
     * @param givenName
     */
    private void addPlayer(String userName, String familyName, String givenName) {
        for (int i = 0; i < playerNo; i++) {
            if (players[i].getUserName().equals(userName)) {
                System.out.println("The player already exists.");
                commandFormat();
                return;
            }
        }
        players[playerNo] = new NimHumanPlayer(userName, familyName, givenName);
        playerNo++;
        commandFormat();
    }

    /**
     * This method will be used when command equals to "addaiplayer"
     * Create a new AI player in array if it doesn't exist
     * If it has already existed in players[], print "The player already exists."
     *
     * @param userName
     * @param familyName
     * @param givenName
     */
    private void addAiPlayer(String userName, String familyName, String givenName) {
        for (int i = 0; i < playerNo; i++) {
            if (players[i].getUserName().equals(userName)) {
                System.out.println("The player already exists.");
                commandFormat();
                return;
            }
        }
        players[playerNo] = new NimAIPlayer(userName, familyName, givenName);
        playerNo++;
        commandFormat();
    }

    /**
     * This method will be used when command equals to "removeplayer"
     * if userName = " ", then will remove all players
     * if userName equals to one of the player in array, move all the players behind this
     * one positions forward
     * if userName doesn't exist, print "The player does not exist."
     *
     * @param userName
     */
    private void removePlayer(String userName) {
        userName = userName.trim();         // remove space

        // remove all players
        if (userName.equals("")) {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            String removeAll = scan.nextLine();
            if (removeAll.equals("y")) {
                playerNo = 0;
                players = new NimPlayer[100];
            }
            commandFormat();
            return;
        }

        // remove an exist player
        for (int i = 0; i < playerNo; i++) {
            if (players[i].getUserName().equals(userName)) {
                // move all players behind this one position forward
                for (int j = i; j < playerNo - 1; j++) {
                    players[j] = players[j + 1];
                }
                // modify players[] and playerNo
                players[playerNo - 1] = null;
                playerNo--;
                commandFormat();
                return;
            }
        }

        // player doesn't exist
        System.out.println("The player does not exist.");
        commandFormat();
    }

    /**
     * This method will be used when command equals to "editplayer"
     * If userName doesn't exist in players array, print "The player does not exist."
     * If userName exists in players array, then call editPlayerName function to revise player's name
     *
     * @param userName
     * @param familyName
     * @param givenName
     */
    private void editPlayer(String userName, String familyName, String givenName) {
        for (int i = 0; i < playerNo; i++) {
            if (players[i].getUserName().equals(userName)) {
                players[i].editPlayerName(familyName, givenName);
                commandFormat();
                return;
            }
        }
        System.out.println("The player does not exist.");
        commandFormat();
    }

    /**
     * This method will be used when command equals to "resetstats"
     * If resetUserName doesn't exist, print "The player does not exist."
     * If resetUserName equals to "", reset all players statistics
     * Else, it would reset that specific player's statistics
     *
     * @param resetUserName
     */
    private void resetstats(String resetUserName) {
        resetUserName = resetUserName.trim();       // remove space

        // reset all player statistics
        if (resetUserName.equals("")) {
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            String input = scan.nextLine();
            if (input.equals("y")) {
                for (int i = 0; i < playerNo; i++) {
                    players[i].setNumOfGames(0);
                    players[i].setNumOfWins(0);
                }
            }
            commandFormat();
            return;
        }

        // reset an exist player statistics
        for (int i = 0; i < playerNo; i++) {
            if (players[i].getUserName().equals(resetUserName)) {
                players[i].setNumOfGames(0);
                players[i].setNumOfWins(0);
                commandFormat();
                return;
            }
        }

        // player doesn't exist
        System.out.println("The player does not exist.");
        commandFormat();
    }

    /**
     * This method will be used when command equals to "displayplayer"
     * If displayUserName equals to "", display all players data which is sorted alphabetically
     * If displayUserName exists, display that player's data
     * Else, print "The player does not exist."
     *
     * @param displayUserName
     */
    private void displayPlayer(String displayUserName) {
        displayUserName = displayUserName.trim();   // remove space

        // display all players
        if (displayUserName.equals("")) {
            sortByAlphabet(players);                // sorted alphabetically
            for (int i = 0; i < playerNo; i++) {
                players[i].display();
            }
            commandFormat();
            return;
        }

        // display an exist players
        for (int i = 0; i < playerNo; i++) {
            if (players[i].getUserName().equals(displayUserName)) {
                players[i].display();
                commandFormat();
                return;
            }
        }

        // player doesn't exist
        System.out.println("The player does not exist.");
        commandFormat();
    }

    /**
     * This method will be used when command equals to "rankings"
     * If user types "" or "desc", then ranking data would be printed and sorted descending
     * If user types "asc", the ranking data would be printed and sorted ascending
     * Display details should be well formatted and no more than 10 players
     *
     * @param sortMethod
     */
    private void rankings(String sortMethod) {
        sortMethod = sortMethod.trim();         // remove space

        //descending
        if (sortMethod.equals("") || sortMethod.equals("desc")) {
            sortDesc(players);
            int displayNumber = 10;
            if (playerNo <= displayNumber) {
                displayNumber = playerNo;
            }
            for (int i = 0; i < displayNumber; i++) {
                int displayPercentage = (int) Math.round(players[i].getWinRatio() * 100);
                System.out.printf("%-4s | %02d games | %s %s",
                        (String) (displayPercentage + "%"), players[i].getNumOfGames(),
                        players[i].getGivenName(), players[i].getFamilyName());
                System.out.println();
            }
            commandFormat();
            return;
        }

        //ascending
        sortAsc(players);
        int displayNumber = 10;
        if (playerNo <= displayNumber) {
            displayNumber = playerNo;
        }
        for (int i = 0; i < displayNumber; i++) {
            int displayPercentage = (int) Math.round(players[i].getWinRatio() * 100);
            System.out.printf("%-4s | %02d games | %s %s",
                    (String) (displayPercentage + "%"), players[i].getNumOfGames(),
                    players[i].getGivenName(), players[i].getFamilyName());
            System.out.println();
        }
        commandFormat();
    }

    /**
     * This method will be used when command equals to "startgame"
     * If one of the players does not exist in players array, print "One of the players does not exist."
     * Else create a new NimGame variable and execute NimGame function
     *
     * @param initialStones
     * @param upperBound
     * @param playerName1
     * @param playerName2
     */
    private void startGame(int initialStones, int upperBound, String playerName1, String playerName2) {

        // find players
        for (int i = 0; i < playerNo; i++) {
            if (players[i].getUserName().equals(playerName1)) {
                for (int j = 0; j < playerNo; j++) {
                    if (players[j].getUserName().equals(playerName2)) {
                        NimGame nimGame = new NimGame();
                        nimGame.NimGame(initialStones, upperBound, players[i], players[j]);
                        commandFormat();
                        return;
                    }
                }
            }
        }

        // one of players does not exist
        System.out.println("One of the players does not exist.");
        commandFormat();
    }

    /**
     * This is a alphabetical sort method (using selection sort)
     * parameter is an array of NimPlayer
     *
     * @param args
     */
    private void sortByAlphabet(NimPlayer[] args) {
        for (int i = 0; i < playerNo - 1; i++) {
            int min = i;
            for (int j = i + 1; j < playerNo; j++) {
                if ((args[j].getUserName().toLowerCase().compareTo(args[min].getUserName().toLowerCase())) < 0) {
                    min = j;
                }
            }
            NimPlayer temp = args[i];
            args[i] = args[min];
            args[min] = temp;
        }
    }

    /**
     * This is an ascending sort method (using selection sort)
     * If there are the same rankings, sort alphabetically
     *
     * @param args
     */
    private void sortAsc(NimPlayer[] args) {
        for (int i = 0; i < playerNo - 1; i++) {
            int min = i;
            for (int j = i + 1; j < playerNo; j++) {
                if (args[j].getWinRatio() < args[min].getWinRatio()) {
                    min = j;
                }
            }
            NimPlayer temp = args[i];
            args[i] = args[min];
            args[min] = temp;
        }

        // if there are the same rankings, sort alphabetically
        for (int i = 0; i < playerNo - 1; i++) {
            int min = i;
            for (int j = i + 1; j < playerNo; j++) {
                if (args[i].getWinRatio() == args[j].getWinRatio()) {
                    if (args[j].getUserName().toLowerCase().compareTo(args[min].getUserName().toLowerCase()) < 0) {
                        min = j;
                    }
                }
            }
            NimPlayer temp = args[i];
            args[i] = args[min];
            args[min] = temp;
        }
    }

    /**
     * This is a descending sort method (using selection sort)
     * If there are the same rankings, sort alphabetically
     *
     * @param args
     */
    private void sortDesc(NimPlayer[] args) {
        for (int i = 0; i < playerNo - 1; i++) {
            int max = i;
            for (int j = i + 1; j < playerNo; j++) {
                if (args[j].getWinRatio() > args[max].getWinRatio()) {
                    max = j;
                }
            }
            NimPlayer temp = args[i];
            args[i] = args[max];
            args[max] = temp;
        }

        // if there are the same rankings, sort alphabetically
        for (int i = 0; i < playerNo - 1; i++) {
            int min = i;
            for (int j = i + 1; j < playerNo; j++) {
                if (args[i].getWinRatio() == args[j].getWinRatio()) {
                    if (args[j].getUserName().toLowerCase().compareTo(args[min].getUserName().toLowerCase()) < 0) {
                        min = j;
                    }
                }
            }
            NimPlayer temp = args[i];
            args[i] = args[min];
            args[min] = temp;
        }
    }

    /**
     * Identify a new type of Exception
     * when user type in a invalid command
     */
    public static class InvalidCommandException extends Exception {
        // constructor
        private InvalidCommandException(String command) {
            super("\'" + command + "\' is not a valid command.");
        }
    }

    /**
     * Identify a new type of Exception
     * when user type in some command with invalid argument
     */
    public static class InvalidArgumentException extends Exception {
        //constructor
        private InvalidArgumentException() {
            super("Incorrect number of arguments supplied to command.");
        }
    }
}

