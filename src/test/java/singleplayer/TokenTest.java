package singleplayer;

import colour.Colour;
import org.junit.jupiter.api.Test;
import table.Deck;
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
        assertNotNull(t1.toString());
    }

    @Test
    public void cardActionTest(){
        DevelopmentBoard devBoard = new DevelopmentBoard();
        Token token = new Token(1, Colour.BLUE, 4);
        Deck deck = devBoard.getDeck(Colour.BLUE);
        boolean isEmpty = deck.isEmpty();
        assertFalse(isEmpty);
        for(int i=0; i<token.getRemoveQuantity();i++)
            token.cardAction(devBoard);
        isEmpty = deck.isEmpty();
        assertTrue(isEmpty);
        //for (int i=0; i<9; i++)
        //    token.cardAction(devBoard);
        //commented section brings to a game over message successfully
    }
}
