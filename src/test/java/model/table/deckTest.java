package model.table;

import model.cards.DevelopmentCard;
import model.colour.Colour;
import model.utilities.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;


public class deckTest {
    @Test
    void createDeckTest(){
        JsonParser jsonParser = new JsonParser("src/main/resources/developmentCards.json");
        ArrayList<DevelopmentCard> cards = jsonParser.getDevelopmentCards();
        ArrayList<DevelopmentCard> fourCard = new ArrayList<>();
        int i = 0;
        for (DevelopmentCard d : cards) {
            fourCard.add(d);
            if(i == 3){
                break;
            }
            i++;
        }

        Deck deck = new Deck(fourCard);
        fourCard.add(cards.get(19));
        assertThrows(InputMismatchException.class, ()->new Deck(fourCard));
    }

    @Test
    void getterTests(){
        JsonParser jsonParser = new JsonParser("src/main/resources/developmentCards.json");
        ArrayList<DevelopmentCard> cards = jsonParser.getDevelopmentCards();
        ArrayList<DevelopmentCard> fourCard = new ArrayList<>();
        int i = 0;
        for (DevelopmentCard d : cards) {
            fourCard.add(d);
            if(i == 3){
                break;
            }
            i++;
        }
        Deck deck = new Deck(fourCard);
        assertFalse( deck.isEmpty() );
        assertEquals( deck.getColourDeck(), Colour.GREEN );
        deck.shuffleDeck();
        assertEquals(deck.getLevelDeck(), 1);
        assertEquals( deck.getCost(), deck.popCard().getCost());
        assertNotNull(deck.popCard());
        assertNotNull(deck.popCard());
        assertNotNull(deck.popCard());
        assertNull(deck.popCard());
        assertTrue(deck.isEmpty());
    }
}
