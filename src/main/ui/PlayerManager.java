package ui;

import java.util.Scanner;

import model.GameHistory;
import model.Player;
import model.PlayerList;

public class PlayerManager {

    private Scanner input;
    private PlayerList playerlist = PlayerList.getInstance();
    private GameHistory gameHistory = GameHistory.getInstance();

    /*
     * EFFECTS: runs the player manager
     */
    public PlayerManager() {
        runPlayerManager();
    }

    /*
     * MODIFIES: this
     * EFFECTS: display command options, process commands
     */
    public void runPlayerManager() {
        printCommandOptions();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        String command = input.next();
        processCommand(command);
    }

    /*
     * EFFECTS: display command options
     */
    public void printCommandOptions() {
        System.out.println("Enter \"a\" to add new player.");
        System.out.println("Enter \"p\" to view player list.");
        System.out.println("Enter \"r\" to remove player.");
        System.out.println("Enter \"u\" to update player rating.");
        System.out.println("Enter \"w\" to view player win rate.");
        System.out.println("Enter \"c\" to view player' performance curve.");
        System.out.println("Enter \"q\" to quit player manager.");
    }

    /*
     * EFFECTS: prints names of all players
     */
    public void displayPlayerList() {
        for (Player player : playerlist.getPlayers()) {
            System.out.println(player.getName());
        }
    }

    /*
     * EFFECTS: process command
     *          throws IllegalArgument Exception if the command is not a/p/r/u/w/c/q
     */
    public void processCommand(String command) throws IllegalArgumentException {
        if (command.equals("a")) {
            addNewPlayer();
        } else if (command.equals("p")) {
            viewPlayerList();
        } else if (command.equals("r")) {
            deletePlayer();
        } else if (command.equals("u")) {
            updatePlayerRating();
        } else if (command.equals("w")) {
            viewPlayerWinRate();
        } else if (command.equals("c")) {
            viewPerformanceCurve();
        } else if (!command.equals("q")) {
            throw new IllegalArgumentException("Enter a/p/r/u/q");
        } 
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a player with the given name and the default rating
     */    
    public void addNewPlayer() {
        System.out.println("Enter player's name");
        String name = input.next();
        Player player = new Player(name);
        playerlist.addPlayer(player);
        runPlayerManager();
    }

    /*
     * EFFECTS: prints the list of names of all players
     */    
    public void viewPlayerList() {
        System.out.println(" \n List of all players: \n");
        for (Player player : playerlist.getPlayers()) {
            System.out.println(player.getName());
        }
        runPlayerManager();
    }

    /*
     * MODIFIES: this
     * EFFECTS: deletes the player with the given name
     */  
    public void deletePlayer() {
        System.out.println(" \n Enter the name of the player to be deleted");
        String name = input.next();
        playerlist.removePlayer(name);
        System.out.println(" \n Player " + name + " deleted successfully ");
        runPlayerManager();
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the player's rating with the given rating
     */  
    public void updatePlayerRating() {
        System.out.println(" \n Enter the name of the player to update rating");
        String name = input.next();
        System.out.println(" \n Enter the player's new rating ");
        int rating = Integer.parseInt(input.next());
        playerlist.findPlayer(name).setRating(rating);
        System.out.println(" \n Player Rating updated successfully ");
        runPlayerManager();
    }

    /*
     * EFFECTS: prints the win rate of the given player
     */  
    public void viewPlayerWinRate() {
        System.out.println(" \n Enter the name of the player");
        String name = input.next();
        System.out.println(" \n Win rate: " + gameHistory.getPlayerWinRate(name));
        runPlayerManager();
    }

    /*
     * EFFECTS: prints the player's past ratings and the current rating
     */  
    public void viewPerformanceCurve() {
        System.out.println(" \n Enter the name of the player");
        String name = input.next();
        System.out.println(" \n Past rating:");
        System.out.println(gameHistory.getRatingHistory(name));
        System.out.println(" \n Current rating:");
        System.out.println(playerlist.findPlayer(name).getRating());
        runPlayerManager();
    }
}
