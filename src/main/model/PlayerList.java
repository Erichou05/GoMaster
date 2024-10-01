package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// represents an ArrayList of all recorded players
public class PlayerList implements Writable {

    private static PlayerList instance = new PlayerList(); // a singleton object of type Players
    private ArrayList<Player> players; // an ArrayList of players

    /*
     * EFFECTS: creates a new player list
     */
    private PlayerList() {
        players = new ArrayList<Player>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns the unique player list
     */
    public static PlayerList getInstance() {
        return instance;
    }

    /*
     * REQUIRES: player must be in the player list
     * EFFECTS:  returns the player with the given name of the player
     */
    public Player findPlayer(String playername) {
        for (Player player : players) {
            if (player.getName().equals(playername)) {
                return player;
            }
        }
        return null;
    }

    /*
     * MODIFIES: this, gameHistory
     * EFFECTS: removes the player from the player list with the given name,
     *          removes all games the player has participated
     */
    public void removePlayer(String playerName) {
        for (int i = players.size() - 1; i >= 0; i--) {
            Player player = players.get(i);
            if (player.getName().equals(playerName)) {
                players.remove(i);
            }
        }
        GameHistory gameHistory = GameHistory.getInstance();

        List<Game> games = gameHistory.getGameHistory();
        for (int i = games.size() - 1; i >= 0; i--) {
            Game game = games.get(i);
            if (game.getBlackPlayer().getName().equals(playerName)
                    || game.getWhitePlayer().getName().equals(playerName)) {
                games.remove(i);

            }
        }

        EventLog.getInstance().logEvent(new Event("Player " + playerName + " with all associated games deleted"));
    }

    public void addPlayer(Player player) {
        players.add(player);
        EventLog.getInstance().logEvent(new Event("Player " + player.getName() + " added to player list"));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /*
     * EFFECTS: converts player list to JSON object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerList", playerListToJson());
        return json;
    }

    /* 
     * EFFECTS: returns players in player list  as a JSON array
     */
    private JSONArray playerListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p : players) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}
