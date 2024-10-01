package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import persistence.Writable;

// represents a game
public class Game implements Writable {

    public static final String REGEX_DATE = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])"; 
    //                                    regex for date in the format yyyy-mm-dd
    private static int nextGameId = 1; // tracks ID of next account created
    private int gameId;                // game ID
    private String date;               // date of game in the format yyyy-mm-dd
    private Player blackPlayer;        // black player
    private Player whitePlayer;        // white player
    private int blackRating;           // rating of black player at the time of the game
    private int whiteRating;           // rating of white player at the time of the game
    private String winner;             // winner of the game, one of "b"/"w"/"d" (black/white/draw)
    private List<Integer> moves;       // position of moves of a game, the position (x, y) 
    //                                    with the top left corner being (1, 1) is recorded as 100x+y
    private List<String> annotation;   // annotation of moves in a game

    /*
     * REQUIRES: date must be in the format yyyy-mm-dd,
     *           winner must be one of "b"/"w"/"d" (black/white/draw)
     * EFFECTS: creates a new player with the given name
     */
    public Game(String date, Player blackPlayer, Player whitePlayer, String winner) {
        gameId = nextGameId++;
        this.date = date;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        blackRating = blackPlayer.getRating();
        whiteRating = whitePlayer.getRating();
        this.winner = winner;
        moves = new ArrayList<Integer>();
        annotation = new ArrayList<String>();
    }

    /*
     * REQUIRES: move must be a valid move
     * MODIFIES: this
     * EFFECTS: adds the given move to moves
     */
    public void addMove(int move) {
        moves.add(move);
    }

    /*
     * MODIFIES: this
     * REQUIRES: moves.size() >= 1
     * EFFECTS: deletes the last move
     */
    public void deleteMove() {
        int index = moves.size() - 1;
        moves.remove(index);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a string to annotation
     */
    public void addAnnotation(String s) {
        annotation.add(s);
    }

    /*
     * MODIFIES: this
     * REQUIRES: annotation.size() >= 1
     * EFFECTS: deletes the last annotation
     */
    public void deleteAnnotation() {
        int index = annotation.size() - 1;
        annotation.remove(index);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets blackPlayerRating to given rating
     */
    public void setBlackPlayerRating(int rating) {
        blackRating = rating;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets whitePlayerRating to given rating
     */
    public void setWhitePlayerRating(int rating) {
        whiteRating = rating;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets moves to given moves
     */
    public void setMoves(List<Integer> moves) {
        this.moves = moves;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets annotation to given annotation
     */
    public void setAnnotation(List<String> annotation) {
        this.annotation = annotation;
    }

    /*
     * for testing purposes only
     * MODIFIES: this
     * EFFECTS: sets nextGameId to id
     */
    public void setNextGameId(int id) {
        nextGameId = id;
    }

    /*
     * for testing purposes only
     * MODIFIES: this
     * EFFECTS: sets GameId to id
     */
    public void setGameId(int id) {
        gameId = id;
    }

    public int getGameId() {
        return gameId;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public int getBlackRating() {
        return blackRating;
    }

    public int getWhiteRating() {
        return whiteRating;
    }

    public String getDate() {
        return date;
    }

    public String getWinner() {
        return winner;
    }

    public List<Integer> getMoves() {
        return moves;
    }

    public List<String> getAnnotation() {
        return annotation;
    }

    /*
     * EFFECTS: converts game to JSON object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("blackPlayer", blackPlayer.toJson());
        json.put("whitePlayer", whitePlayer.toJson());
        json.put("blackRating", blackRating);
        json.put("whiteRating", whiteRating);
        json.put("winner", winner);
        json.put("moves", moves);
        json.put("annotation", annotation);
        return json;
    }

}
