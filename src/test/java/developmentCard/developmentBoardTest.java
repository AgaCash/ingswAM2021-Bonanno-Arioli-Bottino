package developmentCard;

import cards.DevelopmentCard;
import org.junit.jupiter.api.Test;
import table.Deck;
import table.DevelopmentBoard;

import static org.junit.jupiter.api.Assertions.*;

public class developmentBoardTest {
    @Test
    void createDevBoardTest(){
        DevelopmentBoard d = new DevelopmentBoard();
        assertNotNull(d);
    }

    @Test
    void getDeckTest(){
        DevelopmentBoard d = new DevelopmentBoard();
        assertNull(d.getDeck(-1));
        assertNotNull(d.getDeck(1));
        assertNotNull(d.getDeck(2));
    }

    @Test
    void popCardFromDeckTest(){
        DevelopmentBoard d = new DevelopmentBoard();
        assertNotNull( d.popCardFromDeck(1) );
        assertNotNull( d.popCardFromDeck(1) );
        assertNotNull( d.popCardFromDeck(1) );
        assertNotNull( d.popCardFromDeck(1) );
        assertNull( d.popCardFromDeck(1) );
    }
}
