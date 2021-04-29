package singleplayer;

import colour.Colour;
import org.junit.jupiter.api.Test;
import table.DevelopmentBoard;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {
    @Test
    public void tokenTest(){
        Token t1 = new Token(0, Colour.BLUE, 3);
        Token t2 = new Token(1, 2);
        assertNotNull(t1);
        assertNotNull(t2);
        assertEquals(t1.getTokenID(), 0);
        assertEquals(t2.getTokenID(), 1);
        assertEquals(t1.getColour(), Colour.BLUE);
        assertNull(t2.getColour());
        //t1.execute(new FaithTrack(), new DevelopmentBoard());
        //t2.execute(new FaithTrack(), new DevelopmentBoard());
        assertNotNull(t1.toString());
        DevelopmentBoard d = new DevelopmentBoard();
        for (int i = 0; i < 13; i++) {
          //  t1.execute(new FaithTrack(), d);
        }

    }
}
