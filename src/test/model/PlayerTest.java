package model;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private Player player;
    
    @BeforeEach
    void runBefore() {
        player = new Player("x");
    }

    @Test
    void playerConstructorTest() {
        assertEquals(player.getName(), "x");
        assertEquals(player.getRating(), 1200);
    }

    @Test
    void setRatingTest() {
        assertEquals(player.getRating(), 1200);
        player.setRating(1250);
        assertEquals(player.getRating(), 1250);
    }

    @Test
    void getNameTest() {
        assertEquals(player.getName(), "x");
    }

    @Test
    void getRatingTest() {
        assertEquals(player.getRating(), 1200);
    }
}
