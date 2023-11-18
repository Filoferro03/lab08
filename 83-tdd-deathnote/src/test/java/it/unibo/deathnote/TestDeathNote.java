package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Thread.sleep;

import java.util.List;
import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;
import static it.unibo.deathnote.api.DeathNote.RULES;

class TestDeathNote {
    private static final String TEST_NAME_1 = "Filippo Ferretti";
    private static final String TEST_NAME_2 = "Mario Rossi";
    private static final String TEST_CAUSE = "accident";
    private static final String TEST_DETAILS= "Riding too fast";
    private DeathNote deathNote;

    @BeforeEach
    void init () {
        deathNote = new DeathNoteImpl();
    }

    @Test
    void testExistingRule () {
        for (final var i : List.of (-1,0, RULES.size()+1) ) {
            Throwable exception = assertThrows(IllegalArgumentException.class, ()-> deathNote.getRule(i));
            assertEquals("Rule number " + i + "do not exist\n", exception.getMessage());
        }
    }

    @Test
    void testValidRule () {
        for (int i=1; i <= RULES.size(); i++) {
            assertNotNull(deathNote.getRule(i));
            assertFalse(deathNote.getRule(i).isBlank());
        }
    }

    @Test
    void testDeath () {
        assertFalse(deathNote.isNameWritten(TEST_NAME_1));
        deathNote.writeName(TEST_NAME_1);
        assertTrue(deathNote.isNameWritten(TEST_NAME_1));
        assertFalse(deathNote.isNameWritten(TEST_NAME_2));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testDeathCause () throws InterruptedException {
        Throwable exception = assertThrows(IllegalStateException.class, ()-> deathNote.writeDeathCause(TEST_CAUSE));
        assertEquals("cause is null or there is no name in the deathnote", exception.getMessage());
        deathNote.writeName(TEST_NAME_1);
        assertEquals("heart attack", deathNote.getDeathCause(TEST_NAME_1));
        deathNote.writeName(TEST_NAME_2);
        assertTrue(deathNote.writeDeathCause("karting accident"));
        assertEquals("karting accident", deathNote.getDeathCause(TEST_NAME_2));
        sleep(100);
        assertFalse(deathNote.writeDeathCause(TEST_CAUSE));
        assertEquals("karting accident", deathNote.getDeathCause(TEST_NAME_2));
    }

    @Test
    void testDeathDetails () throws InterruptedException {
        Throwable exception = assertThrows(IllegalStateException.class, ()-> deathNote.writeDetails(TEST_DETAILS));
        assertEquals("Name is not written in the death note yet", exception.getMessage());
        deathNote.writeName(TEST_NAME_1);
        assertEquals("", deathNote.getDeathDetails(TEST_NAME_1));
        assertTrue(deathNote.writeDetails("ran for too long"));
        assertEquals("ran for too long", deathNote.getDeathDetails(TEST_NAME_1));
        deathNote.writeName(TEST_NAME_2);
        sleep(6100);
        assertFalse(deathNote.writeDetails(TEST_DETAILS));
        assertEquals("", deathNote.getDeathDetails(TEST_NAME_2));
    }

}