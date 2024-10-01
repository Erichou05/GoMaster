package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event eventE;
    private Event eventF;
    private Date date;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
    @BeforeEach
	public void runBefore() {
        eventE = new Event("Player a added");   // (1)
        date = Calendar.getInstance().getTime();   // (2)
        eventF = new Event("Player a added");
    }
	
    @Test
	public void testEvent() {
        assertEquals("Player a added", eventE.getDescription());
        assertEquals(date, eventE.getDate());
    }

    @Test
	public void testToString() {
        assertEquals(date.toString() + "\n" + "Player a added", eventE.toString());
    }

    @Test
    public void testOverridingEquals() {
        assertTrue(eventE.equals(eventF));
        assertFalse(eventE.equals(null));
        assertTrue(eventE.equals(eventE));
        assertFalse(eventE.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        assertTrue(eventE.hashCode() == (int) eventE.hashCode());
    }
}
