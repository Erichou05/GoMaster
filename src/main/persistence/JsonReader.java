package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

import model.Game;
import model.GameHistory;
import model.Player;
import model.PlayerList;

// Represents a reader that reads  game history and player list from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game history from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GameHistory readGameHistory() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameHistory(jsonObject);
    }

    // EFFECTS: reads player list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlayerList readPlayerList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayerList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game history from JSON object and returns it
    private GameHistory parseGameHistory(JSONObject jsonObject) {
        GameHistory gameHistory = GameHistory.getInstance();
        addGames(gameHistory, jsonObject);
        return gameHistory;
    }

    // EFFECTS: parses player list from JSON object and returns it
    private PlayerList parsePlayerList(JSONObject jsonObject) {
        PlayerList playerList = PlayerList.getInstance();
        addPlayers(playerList, jsonObject);
        return playerList;
    }

    // MODIFIES: gameHistory
    // EFFECTS: parses games from JSON object and adds them to game history
    private void addGames(GameHistory gameHistory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gameHistory");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(gameHistory, nextGame);
        }
    }

    // MODIFIES: playerList
    // EFFECTS: parses players from JSON object and adds them to player list
    private void addPlayers(PlayerList playerList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("playerList");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(playerList, nextPlayer);
        }
    }

    // MODIFIES: gameHistory
    // EFFECTS: parses game from JSON object and adds it to game history
    private void addGame(GameHistory gameHistory, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        Player blackPlayer = getPlayerFromJson(jsonObject.getJSONObject("blackPlayer"));
        Player whitePlayer = getPlayerFromJson(jsonObject.getJSONObject("whitePlayer"));
        int blackRating = jsonObject.getInt("blackRating");
        int whiteRating = jsonObject.getInt("whiteRating");
        String winner = jsonObject.getString("winner");
        Game game = new Game(date, blackPlayer, whitePlayer, winner);
        game.setBlackPlayerRating(blackRating);
        game.setWhitePlayerRating(whiteRating);
        JSONArray movesJsonArray = jsonObject.getJSONArray("moves");
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < movesJsonArray.length(); i++) {
            moves.add(movesJsonArray.getInt(i));
        }
        JSONArray annotationJsonArray = jsonObject.getJSONArray("annotation");
        List<String> annotation = new ArrayList<>();
        for (int i = 0; i < annotationJsonArray.length(); i++) {
            annotation.add(annotationJsonArray.getString(i));
        }
        game.setMoves(moves);
        game.setAnnotation(annotation);
        gameHistory.getGameHistory().add(game);
    }

    // EFFECTS: parses player from JSON object
    private Player getPlayerFromJson(JSONObject playerJson) {
        String name = playerJson.getString("name");
        int rating = playerJson.getInt("rating");
        Player player = new Player(name);
        player.setRating(rating);
        return player;
    }

    // MODIFIES: playerList
    // EFFECTS: parses player from JSON object and adds it to playerList
    private void addPlayer(PlayerList playerList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int rating = jsonObject.getInt("rating");
        Player player = new Player(name);
        player.setRating(rating);
        playerList.getPlayers().add(player);
    }    

}
