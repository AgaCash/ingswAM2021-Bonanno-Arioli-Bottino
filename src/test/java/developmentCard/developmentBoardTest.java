package developmentCard;

import cards.DevelopmentCard;
import org.junit.jupiter.api.Test;
import table.Deck;
import table.DevelopmentBoard;

import java.util.Objects;

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
        assertNotNull(d.getDeck(5));
        assertNotNull(d.getDeck(11));
        assertNull(d.getDeck(12));
        assertNotNull(Objects.requireNonNull(d.getDeck(11)).popCard());
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

    @Test
    void countTotalCards(){
        DevelopmentBoard d = new DevelopmentBoard();
        int nDeck = 0;
        for(int i = 0; i < 12; i++, nDeck++){
            for(int j = 0; j < 4 ; j++)
                assertNotNull(d.popCardFromDeck(nDeck));

        }
        assertNull(d.popCardFromDeck(nDeck));
    }
}
