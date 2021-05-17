/*package model.table;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class developmentBoardTest {
    DevelopmentBoard d;
    @Test
    void createDevBoardTest(){
        d = DevelopmentBoard.getDevBoardInstance();
        assertNotNull(d);
        d.deleteInstance();
    }

    @Test
    void getDeckTest(){
        d = DevelopmentBoard.getDevBoardInstance();
        assertNull(d.getDeck(-1));
        assertNotNull(d.getDeck(1));
        assertNotNull(d.getDeck(2));
        assertNotNull(d.getDeck(5));
        assertNotNull(d.getDeck(11));
        assertNull(d.getDeck(12));
        assertNotNull(Objects.requireNonNull(d.getDeck(11)).popCard());
        d.deleteInstance();
    }

    @Test
    void popCardFromDeckTest(){
        d = DevelopmentBoard.getDevBoardInstance();
        assertNotNull( d.popCardFromDeck(1) );
        assertNotNull( d.popCardFromDeck(1) );
        assertNotNull( d.popCardFromDeck(1) );
        assertNotNull( d.popCardFromDeck(1) );
        assertNull( d.popCardFromDeck(1) );
        d.deleteInstance();

    }

    @Test
    void countTotalCards(){
        d = DevelopmentBoard.getDevBoardInstance();
        int nDeck = 0;
        for(int i = 0; i < 12; i++, nDeck++){
            for(int j = 0; j < 4 ; j++)
                assertNotNull(d.popCardFromDeck(nDeck));

        }
        assertNull(d.popCardFromDeck(nDeck));
        d.deleteInstance();
    }

}*/
