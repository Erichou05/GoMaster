package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class PlayerListTest {

    private Player player1;
    private Player player2;
    private Player player3;
    private PlayerList playerList;
    private Game testGame;
    private Game testGame2;
    private Game testGame3;
    private GameHistory gameHistory;

    @BeforeEach
    void runBefore() {
        player1 = new Player("x");
        player2 = new Player("y");
        player3 = new Player("z");
        playerList = PlayerList.getInstance();
        testGame = new Game("2024-07-01", player1, player2, "b");
        testGame2 = new Game("2024-07-01", player2, player3, "w");
        testGame3 = new Game("2024-07-05", player3, player1, "d");
        gameHistory = GameHistory.getInstance();
        gameHistory.getGameHistory().clear();
        gameHistory.getGameHistory().add(testGame);
        gameHistory.getGameHistory().add(testGame2);
        gameHistory.getGameHistory().add(testGame3);
    }

    // the tests are merged to avoid singleton instantiation issues
    @Test
    @Order(1)
    void test() {
        // playerListConstructorTest
        playerList.addPlayer(player1);
        playerList.addPlayer(player2);
        assertEquals(playerList.getPlayers().size(), 2);
        // getSingletonTest
        assertEquals(PlayerList.getInstance(), playerList);
        // addPlayerTest
        playerList.addPlayer(player3);
        assertEquals(playerList.getPlayers().size(), 3);
        assertEquals(playerList.getPlayers().get(2), player3);
        // findPlayerTest
        assertEquals(playerList.findPlayer("y"), player2);
        assertEquals(playerList.findPlayer("z"), player3);
        assertNull(playerList.findPlayer("123"));
        // removePlayerTest
        playerList.removePlayer("y");
        assertEquals(playerList.getPlayers().size(), 2);
        assertFalse(playerList.getPlayers().contains(player2));
        assertEquals(gameHistory.getGameHistory().size(), 1);
        // getPlayersTest
        assertEquals(playerList.getPlayers().size(), 2);
        assertEquals(playerList.getPlayers().get(0), player1);
        assertEquals(playerList.getPlayers().get(1), player3);
    }

}
