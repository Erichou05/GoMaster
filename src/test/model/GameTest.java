package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class GameTest {

    private Game testGame;
    private Player player1;
    private Player player2;
    
    @BeforeEach
    void runBefore() {
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        testGame = new Game("2024-07-01", player1, player2, "b");
        testGame.setGameId(1);
        testGame.setNextGameId(2);
        testGame.addMove(404);
        testGame.addMove(1604);
        testGame.addMove(316);
        testGame.addMove(1919);
        testGame.addAnnotation("Move 4: fatal mistake");
    }

    @Test 
    @Order(1)
    void gameConstructorTest() {
        assertEquals(testGame.getGameId(), 1);
        assertEquals(testGame.getDate(), "2024-07-01");
        assertEquals(testGame.getBlackPlayer(), player1);
        assertEquals(testGame.getWhitePlayer(), player2);
        assertEquals(testGame.getWinner(), "b");
    }

    @Test
    void addMoveTest() {
        assertEquals(testGame.getMoves().size(), 4);
        testGame.addMove(517);
        assertEquals(testGame.getMoves().size(), 5);
        assertEquals(testGame.getMoves().get(4).intValue(), 517);
    }

    @Test
    void deleteMoveTest() {
        assertEquals(testGame.getMoves().size(), 4);
        testGame.deleteMove();
        assertEquals(testGame.getMoves().size(), 3);
        assertEquals(testGame.getMoves().get(2).intValue(), 316);
    }

    @Test
    void addAnnotationTest() {
        assertEquals(testGame.getAnnotation().size(), 1);
        testGame.addAnnotation("Test Annotation");
        assertEquals(testGame.getAnnotation().size(), 2);
        assertEquals(testGame.getAnnotation().get(1), "Test Annotation");
    }

    @Test
    void deleteAnnotationTest() {
        assertEquals(testGame.getAnnotation().size(), 1);
        testGame.deleteAnnotation();
        assertEquals(testGame.getAnnotation().size(), 0);
    }

    @Test
    @Order(2)
    void getGameIdTest() {
        assertEquals(testGame.getGameId(), 1);
    }

    @Test
    void getBlackPlayerTest() {
        assertEquals(testGame.getBlackPlayer(), player1);
    }

    @Test
    void getWhitePlayerTest() {
        assertEquals(testGame.getWhitePlayer(), player2);
    }

    @Test
    void getBlackPlayerRatingTest() {
        assertEquals(testGame.getBlackRating(), 1200);
    }

    @Test
    void getWhitePlayerRatingTest() {
        assertEquals(testGame.getWhiteRating(), 1200);
    }

    @Test
    void getDateTest() {
        assertEquals(testGame.getDate(), "2024-07-01");
    }

    @Test
    void getWinnerTest() {
        assertEquals(testGame.getWinner(), "b");
    }

    @Test
    void getMovesTest() {
        List<Integer> moves = new ArrayList<Integer>();
        moves.add(404);
        moves.add(1604);
        moves.add(316);
        moves.add(1919);
        assertEquals(testGame.getMoves(), moves);
    }

    @Test
    void getAnnotationTest() {
        List<String> annotation = new ArrayList<String>();
        annotation.add("Move 4: fatal mistake");
        assertEquals(testGame.getAnnotation(), annotation);
    }

    @Test
    void setGameIdTest() {
        testGame.setGameId(5);
        testGame.setNextGameId(6);
        assertEquals(testGame.getGameId(), 5);
        Game testGame2 = new Game("2024-07-01", player1, player2, "b");
        assertEquals(testGame2.getGameId(), 6);
    }

    @Test
    void setBlackPlayerRatingTest() {
        assertEquals(testGame.getBlackRating(), 1200);
        testGame.setBlackPlayerRating(1250);
        assertEquals(testGame.getBlackRating(), 1250);
    }

    @Test
    void setWhitePlayerRatingTest() {
        assertEquals(testGame.getWhiteRating(), 1200);
        testGame.setWhitePlayerRating(1250);
        assertEquals(testGame.getWhiteRating(), 1250);
    }

    @Test
    void setMovesTest() {
        List<Integer> moves = new ArrayList<Integer>();
        moves.add(1010);
        assertEquals(testGame.getMoves().size(), 4);
        testGame.setMoves(moves);
        assertEquals(testGame.getMoves().size(), 1);
        assertEquals((int)testGame.getMoves().get(0), 1010);
    }

    @Test
    void setAnnotationTest() {
        List<String> annotation = new ArrayList<String>();
        annotation.add("test annotation");
        testGame.setAnnotation(annotation);
        assertEquals(testGame.getAnnotation().get(0), "test annotation");
    }

    @Test
    void toJsonTest() {
        JSONObject json = testGame.toJson();
        assertEquals(json.get("date"), "2024-07-01");
        assertEquals(json.get("blackRating"), player1.getRating());
        assertEquals(json.get("whiteRating"), player2.getRating());
        assertEquals(json.get("winner"), "b");
    }

}
