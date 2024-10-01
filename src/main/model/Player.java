package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a player
public class Player implements Writable {
    public static final int DEFAULT_RATING = 1200; // the default rating of a new player created
    private String name;                           // player's name
    private int rating;                            // player's rating

    /*
     * REQUIRES: name must be unique and non-empty
     * EFFECTS: creates a new player with the given name
     */
    public Player(String name) {
        this.name = name;
        rating = DEFAULT_RATING;
    }

    /*
     * MODIFIES: this
     * EFFECTS: change the player's rating to the given rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    /*
     * EFFECTS: converts player to JSON object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("rating", rating);
        return json;
    }

}
