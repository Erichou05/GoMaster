package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonReader;
import persistence.JsonWriter;

public class JsonWriterTest {

    @BeforeEach
    void runBefore() {
        PlayerList.getInstance().getPlayers().clear();
        GameHistory.getInstance().getGameHistory().clear();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            GameHistory gameHistory = GameHistory.getInstance();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGameHistory() {
        try {
            GameHistory gameHistory = GameHistory.getInstance();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameHistory.json");
            writer.open();
            writer.writeGameHistory(gameHistory);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyGameHistory.json");
            gameHistory = reader.readGameHistory();
            assertEquals(0, gameHistory.getGameHistory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyPlayerList() {
        try {
            PlayerList playerList = PlayerList.getInstance();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlayerList.json");
            writer.open();
            writer.writePlayerList(playerList);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyPlayerList.json");
            playerList = reader.readPlayerList();
            assertEquals(0, playerList.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
