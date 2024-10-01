package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Game;
import model.GameHistory;
import model.PlayerList;

public class GameRecorder {

    private Scanner input;
    private GameHistory gameHistory = GameHistory.getInstance();
    private PlayerList playerList = PlayerList.getInstance();

    /*
     * EFFECTS: runs the game recorder
     */
    public GameRecorder() {
        runGameRecorder();
    }

    /*
     * EFFECTS: records date, players, winner, moves, annotation into a game and adds the game to game history
     */
    public void runGameRecorder() {
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        System.out.println("Enter black player's name (must be a registered player)");
        String black = input.next();
        System.out.println("Enter white player's name (must be a registered player)");
        String white = input.next();
        System.out.println("Enter date (yyyy-mm-dd)");
        String date = input.next();
        System.out.println("Enter winner by choosing b/w/d (black/white/draw)");
        String winner = input.next();
        Game game = new Game(date, playerList.findPlayer(black), playerList.findPlayer(white), winner);
        List<Integer> moves = addMove();
        List<String> annotation = addAnnotation();
        for (Integer move : moves) {
            game.addMove(move);
        }
        for (String s : annotation) {
            game.addAnnotation(s);
        }
        gameHistory.addGame(game);
        System.out.println("Game created successfully!");
    }

    /*
     * EFFECTS: records moves, terminates when user enters "q"
     */
    public List<Integer> addMove() {
        List<Integer> moves = new ArrayList<Integer>();
        System.out.println("Enter move in the format \"abcd\" such that (ab, cd) is the coordinate on the board.");
        // There is no need to restrict how the coordinate is determined as the board is symmetric
        System.out.println("(a cannot be 0)");
        System.out.println("Enter d to delete last move.");
        System.out.println("Enter q when finishes entering moves.");
        while (true) {
            String move = input.next();
            if (move.equals("q")) {
                break;
            } else if (move.equals("d")) {
                moves.remove(moves.size() - 1);
            } else {
                moves.add(Integer.parseInt(move));
            }
            
        }
        return moves;
    }

    /*
     * EFFECTS: records annotation, terminates when user enters "q"
     */
    public List<String> addAnnotation() {
        List<String> annotation = new ArrayList<String>();
        System.out.println("Enter annotation.");
        System.out.println("Enter d to delete last annotation.");
        System.out.println("Enter q when finishes entering annotation.");
        while (true) {
            String s = input.next();
            if (s.equals("q")) {
                break;
            } else if (s.equals("d")) {
                annotation.remove(annotation.size() - 1);
            } else {
                annotation.add(s);
            }
        }
        return annotation;
    }
}
