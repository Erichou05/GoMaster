package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameHistoryTest {

    private Player player1;
    private Player player2;
    private Player player3;
    private Game testGame;
    private Game testGame2;
    private Game testGame3;
    private GameHistory gameHistory;
    private Boolean caughtException;
    
    @BeforeEach
    void runBefore() {
        player1 = new Player("x");
        player2 = new Player("y");
        player3 = new Player("z");
        testGame = new Game("2024-07-01", player1, player2, "b");
        testGame.setGameId(1);
        testGame.setNextGameId(2);
        testGame2 = new Game("2024-07-05", player2, player3, "w");
        testGame3 = new Game("2024-07-06", player3, player1, "d");
        caughtException = false;
        gameHistory = GameHistory.getInstance();
        gameHistory.getGameHistory().clear();
        gameHistory.addGame(testGame);
        gameHistory.addGame(testGame2);
        gameHistory.addGame(testGame3);
    }

    // the tests are merged to avoid singleton instantiation issues
    @Test
    @SuppressWarnings("methodlength")
    void test() {
        // gameHistoryConstructorTest
        assertEquals(gameHistory.getGameHistory().size(), 3);
        // getAllGameIdTest
        ArrayList<Integer> idList = new ArrayList<Integer>();
        idList.add(1);
        idList.add(2);
        idList.add(3);
        assertEquals(gameHistory.getAllGameId(), idList);
        //getGameByIdTest
        assertEquals(gameHistory.getGameById(1), testGame);
        assertNull(gameHistory.getGameById(9));
        // deleteGameTest
        gameHistory.deleteGame(3);
        assertEquals(gameHistory.getGameHistory().size(), 2);
        assertEquals(gameHistory.getGameHistory().get(1), testGame2);
        // getPlayerWinRateTest
        assertEquals(gameHistory.getPlayerWinRate("y"), "0%");
        assertEquals(gameHistory.getPlayerWinRate("z"), "100%");
        assertEquals(gameHistory.getPlayerWinRate("x"), "100%");
        assertEquals(gameHistory.getPlayerWinRate("player4"), "0%");
        // filterByPlayerTest
        ArrayList<Game> filteredList = gameHistory.filterByPlayer("x", gameHistory.getGameHistory());
        assertEquals(filteredList.size(), 1);
        assertEquals(filteredList.get(0).getDate(), "2024-07-01");
        ArrayList<Game> filteredList2 = gameHistory.filterByPlayer("z", gameHistory.getGameHistory());
        assertEquals(filteredList2.size(), 1);
        assertEquals(filteredList2.get(0).getDate(), "2024-07-05");
        // filterByDateTest
        ArrayList<Game> filteredList3 = gameHistory.filterByDate("2024-07-05", gameHistory.getGameHistory());
        assertEquals(filteredList3.size(), 1);
        assertEquals(filteredList3.get(0).getBlackPlayer(), player2);
        assertEquals(filteredList3.get(0).getWhitePlayer(), player3);
        assertEquals(filteredList3.get(0).getWinner(), "w");
        try {
            gameHistory.filterByDate("2024-7-05", gameHistory.getGameHistory());
            fail();
        } catch (IllegalArgumentException e) {
            caughtException = true;
        } 
        assertTrue(caughtException);
        caughtException = false;
        ArrayList<Game> filteredList4 = gameHistory.filterByDate("", gameHistory.getGameHistory());
        assertEquals(filteredList4.size(), 2);
        // filterByResult
        // illegal argument
        try {
            gameHistory.filterByResult("typo", "player4", gameHistory.getGameHistory());
            fail();
        } catch (IllegalArgumentException e) {
            caughtException = true;
        } 
        assertTrue(caughtException);
        caughtException = false;
        // unspecified result
        ArrayList<Game> filteredList16 = gameHistory.filterByResult("", "player4", gameHistory.getGameHistory());
        assertEquals(filteredList16.size(), 2);
        // win
        ArrayList<Game> filteredList5 = gameHistory.filterByResult("w", "x", gameHistory.getGameHistory());
        assertEquals(filteredList5.size(), 1);
        assertEquals(filteredList5.get(0).getBlackPlayer(), player1);
        assertEquals(filteredList5.get(0).getWhitePlayer(), player2);
        ArrayList<Game> filteredList6 = gameHistory.filterByResult("w", "player4", gameHistory.getGameHistory());
        assertEquals(filteredList6.size(), 0);
        ArrayList<Game> filteredList7 = gameHistory.filterByResult("w", "z", gameHistory.getGameHistory());
        assertEquals(filteredList7.size(), 1);
        assertEquals(filteredList7.get(0).getBlackPlayer(), player2);
        assertEquals(filteredList7.get(0).getWhitePlayer(), player3);
        ArrayList<Game> filteredList8 = gameHistory.filterByResult("w", "y", gameHistory.getGameHistory());
        assertEquals(filteredList8.size(), 0);
        // loss
        ArrayList<Game> filteredList9 = gameHistory.filterByResult("l", "x", gameHistory.getGameHistory());
        assertEquals(filteredList9.size(), 0);
        ArrayList<Game> filteredList10 = gameHistory.filterByResult("l", "player4", gameHistory.getGameHistory());
        assertEquals(filteredList10.size(), 0);
        ArrayList<Game> filteredList11 = gameHistory.filterByResult("l", "x", gameHistory.getGameHistory());
        assertEquals(filteredList11.size(), 0);
        ArrayList<Game> filteredList12 = gameHistory.filterByResult("l", "y", gameHistory.getGameHistory());
        assertEquals(filteredList12.size(), 2);
        ArrayList<Game> filteredList13 = gameHistory.filterByResult("l", "z", gameHistory.getGameHistory());
        assertEquals(filteredList13.size(), 0);
        // draw
        ArrayList<Game> filteredList14 = gameHistory.filterByResult("d", "z", gameHistory.getGameHistory());
        assertEquals(filteredList14.size(), 0);
        ArrayList<Game> filteredList15 = gameHistory.filterByResult("d", "y", gameHistory.getGameHistory());
        assertEquals(filteredList15.size(), 0);
        gameHistory.addGame(testGame3);
        ArrayList<Game> filteredList17 = gameHistory.filterByResult("d", "x", gameHistory.getGameHistory());
        assertEquals(filteredList17.size(), 1);
        // getRatingHistoryTest
        player1.setRating(1250);
        Game testGame1 = new Game("2024-07-01", player1, player2, "b");
        gameHistory.addGame(testGame1);
        ArrayList<Integer> player1RatingHistory = new ArrayList<Integer>();
        player1RatingHistory.add(1200);
        player1RatingHistory.add(1200);
        player1RatingHistory.add(1250);
        assertEquals(player1RatingHistory, gameHistory.getRatingHistory("x"));
    }
}
