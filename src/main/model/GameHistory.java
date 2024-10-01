package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents all recorded games
public class GameHistory implements Writable {

    private static GameHistory instance = new GameHistory(); // a singleton object of type GameHistory
    private ArrayList<Game> gameHistory; // an arraylist of all recorded games

    /*
     * EFFECTS: creates a new game history
     */
    private GameHistory() {
        gameHistory = new ArrayList<Game>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns the unique game history
     */
    public static GameHistory getInstance() {
        return instance;
    }

    /*
     * EFFECTS: returns a list of IDs of all recorded games
     */
    public ArrayList<Integer> getAllGameId() {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        for (Game game : gameHistory) {
            idList.add(game.getGameId());
        }
        return idList;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the given game to the game history
     */
    public void addGame(Game game) {
        gameHistory.add(game);
        EventLog.getInstance().logEvent(new Event("Game #" + game.getGameId() + " added to game history"));
    }

    /*
     * EFFECTS: returns the game with the given ID, null if the given ID does not match any game
     */
    public Game getGameById(int id) {
        for (Game game : gameHistory) {
            if (game.getGameId() == id) {
                return game;
            }
        }
        return null;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes the game with the given game ID from the game history,
     * if the game ID does not exist, do nothing
     */
    public void deleteGame(int gameId) {
        for (int i = gameHistory.size() - 1; i >= 0; i--) {
            if (gameHistory.get(i).getGameId() == gameId) {
                gameHistory.remove(i);
            }
        }
        EventLog.getInstance().logEvent(new Event("Game #" + gameId + " deleted from game history"));
    }

    /*
     * EFFECTS: returns the win rate of the specified player,
     * return "0%" if the player has not played any game or does not exist
     */
    public String getPlayerWinRate(String playerName) {
        int totalGame = 0;
        int wonGame = 0;
        for (Game game : gameHistory) {
            if (game.getBlackPlayer().getName().equals(playerName)) {
                totalGame++;
                System.out.print("playerName");
                if (game.getWinner().equals("b")) {
                    wonGame++;
                }
            } else if (game.getWhitePlayer().getName().equals(playerName)) {
                totalGame++;
                if (game.getWinner().equals("w")) {
                    wonGame++;
                }
            }
        }
        if (totalGame == 0) {
            return "0%";
        }
        return (String.valueOf(Math.round(Double.valueOf(wonGame) / Double.valueOf(totalGame) * 100)) + "%");
    }

    /*
     * EFFECTS: filters the given game list and returns games involving the given
     * player
     * returns the given game list if the player is not specified or does not exist
     */
    public ArrayList<Game> filterByPlayer(String playerName, ArrayList<Game> games) {
        ArrayList<Game> filteredList = new ArrayList<Game>();
        filteredList.addAll(games);
        for (int i = filteredList.size() - 1; i >= 0; i--) {
            Game game = filteredList.get(i);
            if (!game.getBlackPlayer().getName().equals(playerName)
                    && !game.getWhitePlayer().getName().equals(playerName)) {
                filteredList.remove(i);
            }
        }
        return filteredList;
    }

    /*
     * EFFECTS: filters the given game list and returns games of the given date,
     * returns the given game list if the date is not specified,
     * throws IllegalArgumentException if the date is invalid
     */
    public ArrayList<Game> filterByDate(String date, ArrayList<Game> games) throws IllegalArgumentException {
        ArrayList<Game> filteredList = new ArrayList<Game>();
        filteredList.addAll(games);
        if (date.equals("")) {
            return filteredList;
        }
        if (!date.matches(Game.REGEX_DATE)) {
            throw new IllegalArgumentException("Date must be in the format yyyy-mm-dd");
        }
        for (int i = filteredList.size() - 1; i >= 0; i--) {
            Game game = filteredList.get(i);
            if (!game.getDate().equals(date)) {
                filteredList.remove(i);
            }
        }
        return filteredList;
    }

    /*
     * EFFECTS: filters the given game list and returns games with the specified
     * result of the given player,
     * returns the given game list if the result is not specified,
     * throws IllegalArgumentException if the result is invalid
     */
    public ArrayList<Game> filterByResult(String result, String player, ArrayList<Game> games)
            throws IllegalArgumentException {
        ArrayList<Game> filteredList = new ArrayList<Game>();
        filteredList.addAll(games);
        if (result.equals("")) {
            return filteredList;
        } else if (!result.equals("w") && !result.equals("l") && !result.equals("d")) {
            throw new IllegalArgumentException("Please indicate the game result with w/l/d");
        } else if (result.equals("w")) {
            return filterWonGames(player, filteredList);
        } else if (result.equals("l")) {
            return filterLostGames(player, filteredList);
        } else { //if (result.equals("d"))
            return filterDrawnGames(player, filteredList);
        }
    }

    /*
     * REQUIRES: player must be a valid player name
     * EFFECTS: filters the given game list and returns games that the player won
     */
    private ArrayList<Game> filterWonGames(String player, ArrayList<Game> games) {
        ArrayList<Game> filteredList = new ArrayList<Game>();
        filteredList.addAll(games);
        for (int i = filteredList.size() - 1; i >= 0; i--) {
            Game game = filteredList.get(i);
            if (!game.getBlackPlayer().getName().equals(player) && !game.getWhitePlayer().getName().equals(player)) {
                filteredList.remove(i);
            } else if ((game.getBlackPlayer().getName().equals(player) && game.getWinner().equals("w"))
                    || (game.getWhitePlayer().getName().equals(player) && game.getWinner().equals("b"))) {
                filteredList.remove(i);
            }
        }
        return filteredList;
    }

    /*
     * REQUIRES: player must be a valid player name
     * EFFECTS: filters the given game list and returns games that the player lost
     */
    private ArrayList<Game> filterLostGames(String player, ArrayList<Game> games) {
        ArrayList<Game> filteredList = new ArrayList<Game>();
        filteredList.addAll(games);
        for (int i = filteredList.size() - 1; i >= 0; i--) {
            Game game = filteredList.get(i);
            if (!game.getBlackPlayer().getName().equals(player) && !game.getWhitePlayer().getName().equals(player)) {
                filteredList.remove(i);
            } else if ((game.getBlackPlayer().getName().equals(player) && game.getWinner().equals("b"))
                    || (game.getWhitePlayer().getName().equals(player) && game.getWinner().equals("w"))) {
                filteredList.remove(i);
            }
        }
        return filteredList;
    }

    /*
     * REQUIRES: player must be a valid player name
     * EFFECTS: filters the given game list and returns games that the player drawn
     */
    private ArrayList<Game> filterDrawnGames(String player, ArrayList<Game> games) {
        ArrayList<Game> filteredList = new ArrayList<Game>();
        filteredList.addAll(games);
        for (int i = filteredList.size() - 1; i >= 0; i--) {
            Game game = filteredList.get(i);
            if (!game.getBlackPlayer().getName().equals(player) && !game.getWhitePlayer().getName().equals(player)) {
                filteredList.remove(i);
            } else if (!game.getWinner().equals("d")) {
                filteredList.remove(i);
            }
        }
        return filteredList;
    }

    /*
     * REQUIRES: player must be a valid player name
     * EFFECTS: returns a list of the player's past ratings
     */
    public ArrayList<Integer> getRatingHistory(String playername) {
        ArrayList<Integer> ratingHistory = new ArrayList<Integer>();
        for (int i = 0; i < gameHistory.size(); i++) {
            Game game = gameHistory.get(i);
            if (game.getBlackPlayer().getName().equals(playername)) {
                ratingHistory.add(game.getBlackRating());
            }
            if (game.getWhitePlayer().getName().equals(playername)) {
                ratingHistory.add(game.getWhiteRating());
            }
        }
        return ratingHistory;
    }

    public ArrayList<Game> getGameHistory() {
        return gameHistory;
    }

    /*
     * EFFECTS: converts game history to JSON object
     */
    @Override
    public JSONObject toJson() {
        EventLog.getInstance().logEvent(new Event("App data saved"));
        JSONObject json = new JSONObject();
        json.put("gameHistory", gameHistoryToJson());
        return json;
    }

    /* 
     * EFFECTS: returns games in game history as a JSON array
     */
    private JSONArray gameHistoryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Game g : gameHistory) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }
}
