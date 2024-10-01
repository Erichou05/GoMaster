package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.GameHistory;
import model.PlayerList;
import persistence.JsonReader;
import persistence.JsonWriter;

public class GoGameApp {

    private static final String GAMEHISTORY_JSON_STORE = "./data/gameHistory.json";
    private static final String PLAYERLIST_JSON_STORE = "./data/playerList.json";
    private JsonWriter gameJsonWriter;
    private JsonReader gameJsonReader;
    private JsonWriter playerJsonWriter;
    private JsonReader playerJsonReader;
    private Scanner input;

    /*
     * EFFECTS: runs the application
     */
    public GoGameApp() throws IllegalArgumentException, FileNotFoundException {
        gameJsonWriter = new JsonWriter(GAMEHISTORY_JSON_STORE);
        gameJsonReader = new JsonReader(GAMEHISTORY_JSON_STORE);
        playerJsonWriter = new JsonWriter(PLAYERLIST_JSON_STORE);
        playerJsonReader = new JsonReader(PLAYERLIST_JSON_STORE);
        runApp();
    }

    /*
     * MODIFIES: this
     * EFFECTS: prints initial command options
     *          initializes input scanner
     */
    public void runApp() throws IllegalArgumentException, FileNotFoundException {
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        String command; 
        while (true) {
            System.out.println("\n Enter \"p\" to manage players.");
            System.out.println("\n Enter \"g\" to manage games.");
            System.out.println("\n Enter \"s\" to save players and games.");
            System.out.println("\n Enter \"l\" to load players and games.");
            System.out.println("\n Enter \"q\" to quit application.");
            command = input.next();
            processCommand(command);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user command
     *          throws IllegalArgument Exception if the command is not p/g/q
     */
    public void processCommand(String command) throws FileNotFoundException {
        if (command.equals("p")) {
            new PlayerManager();
        } else if (command.equals("g")) {
            new GameManager();
        } else if (command.equals("s")) {
            save();
        } else if (command.equals("l")) {
            loadGames();
            loadPlayers();
        } else if (command.equals("q")) {
            System.out.println("\n Goodbye!");
            System.exit(0);
        } else {
            throw new IllegalArgumentException("Please enter p/g/q");
        }
    }

    /*
     * EFFECTS: saves the players and games to file
     */
    private void save() {
        try {
            gameJsonWriter.open();
            gameJsonWriter.writeGameHistory(GameHistory.getInstance());
            gameJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + GAMEHISTORY_JSON_STORE);
        }
        try {
            playerJsonWriter.open();
            playerJsonWriter.writePlayerList(PlayerList.getInstance());
            playerJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + PLAYERLIST_JSON_STORE);
        }
        System.out.println("Saved Successfully!");
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads games from file
     */
    private void loadGames() {
        try {
            gameJsonReader.readGameHistory();
            System.out.println("Loaded game history from " + GAMEHISTORY_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + GAMEHISTORY_JSON_STORE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads players from file
     */
    private void loadPlayers() {
        try {
            playerJsonReader.readPlayerList();
            System.out.println("Loaded player list from " + PLAYERLIST_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + PLAYERLIST_JSON_STORE);
        }
    }

}
