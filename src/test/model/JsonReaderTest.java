package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonReader;

public class JsonReaderTest {

    @BeforeEach
    void runBefore() {
        PlayerList.getInstance().getPlayers().clear();
        GameHistory.getInstance().getGameHistory().clear();
    }

    @Test
    void testGameHistoryReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameHistory gameHistory = reader.readGameHistory();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testPlayerListReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayerList playerList = reader.readPlayerList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGameHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGameHistory.json");
        try {
            GameHistory gameHistory = reader.readGameHistory();
            assertEquals(gameHistory.getGameHistory().size(), 0);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyPlayerList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlayerList.json");
        try {
            PlayerList playerList = reader.readPlayerList();
            assertEquals(playerList.getPlayers().size(), 0);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameHistory.json");
        try {
            GameHistory gameHistory = reader.readGameHistory();
            assertEquals(gameHistory.getGameHistory().size(), 2);
            assertEquals(gameHistory.getGameHistory().get(0).getDate(), "2024-01-24");
            assertEquals(gameHistory.getGameHistory().get(1).getDate(), "2024-01-26");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlayerList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayerList.json");
        try {
            PlayerList playerList = reader.readPlayerList();
            assertEquals(playerList.getPlayers().size(), 5);
            assertEquals(playerList.getPlayers().get(0).getName(), "player1");
            assertEquals(playerList.getPlayers().get(1).getName(), "player2");
            assertEquals(playerList.getPlayers().get(2).getName(), "1");
            assertEquals(playerList.getPlayers().get(3).getName(), "2");
            assertEquals(playerList.getPlayers().get(4).getName(), "3");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
