package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Game;
import model.GameHistory;

public class GameManager {

    private Scanner input;
    private GameHistory gameHistory = GameHistory.getInstance();

    /*
     * EFFECTS: runs the game manager
     */
    public GameManager() {
        runGameManager();
    }

    /*
     * MODIFIES: this
     * EFFECTS: display command options, process commands
     */
    public void runGameManager() {
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
        System.out.println("Enter \"n\" to record new game.");
        System.out.println("Enter \"a\" to overview all recorded games.");
        System.out.println("Enter \"v\" to view game.");
        System.out.println("Enter \"d\" to delete game.");
        System.out.println("Enter \"f\" to filter recorded games.");
        System.out.println("Enter \"q\" to quit game manager.");
    }

    /*
     * EFFECTS: process command
     * throws IllegalArgument Exception if the command is not n/a/v/d/f/q
     */
    public void processCommand(String command) throws IllegalArgumentException {
        if (command.equals("n")) {
            recordNewGame();
        } else if (command.equals("a")) {
            overviewGames();
        } else if (command.equals("v")) {
            viewGame();
        } else if (command.equals("d")) {
            deleteGame();
        } else if (command.equals("f")) {
            filterGames();
        } else if (!command.equals("q")) {
            throw new IllegalArgumentException("Enter n/a/v/d/f/q");
        } 
    }

    /*
     * MODIFIES: this
     * EFFECTS: records new game
     */
    public void recordNewGame() {
        new GameRecorder();
        System.out.println("Enter anything to leave this page.");
        input.next();
        runGameManager();
    }

    /*
     * EFFECTS: prints the game ID, date, players, and winner of all games
     */
    public void overviewGames() {
        for (Game game : gameHistory.getGameHistory()) {
            System.out.println("Game ID: "
                    + game.getGameId() + " date: "
                    + game.getDate() + " black player : "
                    + game.getBlackPlayer().getName() + " white player: "
                    + game.getWhitePlayer().getName() + " winner: "
                    + game.getWinner());
        }
        System.out.println("Enter anything to leave this page.");
        input.next();
        runGameManager();
    }

    /*
     * REQUIRES: must enter a valid game ID
     * EFFECTS: prints date, players, player's ratings, winner, moves and annotation of the game with given ID
     */
    public void viewGame() {
        System.out.println("Enter Game ID.");
        int id = Integer.parseInt(input.next());
        Game game = gameHistory.getGameById(id);
        System.out.println("date: "
                    + game.getDate() + " black player : "
                    + game.getBlackPlayer().getName() + " (" + game.getBlackRating() + ") white player: "
                    + game.getWhitePlayer().getName() + " (" + game.getWhiteRating() + ") winner: "
                    + game.getWinner());
        System.out.println("Moves: " + "\n" + game.getMoves());
        System.out.println("Annotation: " + "\n" + game.getAnnotation());
        System.out.println("Enter anything to leave this page.");
        input.next();
        runGameManager();
    }

    /*
     * MODIFIES: this
     * REQUIRES: must enter a valid game ID
     * EFFECTS: deletes the game with the given ID
     */
    public void deleteGame() {
        System.out.println("Enter Game ID.");
        int id = Integer.parseInt(input.next());
        gameHistory.deleteGame(id);
        System.out.println("Game deleted successfully.");
        runGameManager();
    }

    /*
     * REQUIRES: must enter "player/date/result"
     * EFFECTS: filters games by the given category and prints game ID, date, players, and winner of the games
     */
    public void filterGames() {
        System.out.println("Enter player/date/result.");
        String category = input.next();
        ArrayList<Game> filteredList;
        if (category.equals("player")) {
            filteredList = filterByPlayer();
        } else if (category.equals("date")) {
            filteredList = filterByDate();
        } else if (category.equals("result")) {
            filteredList = filterByResult();
        } else {
            filteredList = gameHistory.getGameHistory();
        }
        System.out.println("Filtered Games Log: \n");
        for (Game game : filteredList) {
            System.out.println("Game ID: " + game.getGameId() 
                    + " date: " + game.getDate() + " black player : " + game.getBlackPlayer().getName() 
                    + " white player: " + game.getWhitePlayer().getName() + " winner: " + game.getWinner());
        }
        System.out.println("Enter anything to leave this page.");
        input.next();
        runGameManager();
    }

    /*
     * REQUIRES: must enter a valid player name
     * EFFECTS: filters games by player name and returns the filtered list
     */
    public ArrayList<Game> filterByPlayer() {
        System.out.println("Enter player name.");
        String name = input.next();
        return gameHistory.filterByPlayer(name, gameHistory.getGameHistory());
    }

    /*
     * REQUIRES: must enter a valid date
     * EFFECTS: filters games by date and returns the filtered list
     */
    public ArrayList<Game> filterByDate() {
        System.out.println("Enter date in the format yyyy-mm-dd.");
        String date = input.next();
        return gameHistory.filterByDate(date, gameHistory.getGameHistory());
    }

    /*
     * REQUIRES: must enter a valid player name and result(w/l/d)
     * EFFECTS: filters games by player and result and returns the filtered list
     */
    public ArrayList<Game> filterByResult() {
        System.out.println("Enter player name.");
        String name = input.next();
        System.out.println("Enter result by choosing w/l/d (win/loss/draw).");
        String result = input.next();
        return gameHistory.filterByResult(result, name, gameHistory.getGameHistory());
    }



    


}
