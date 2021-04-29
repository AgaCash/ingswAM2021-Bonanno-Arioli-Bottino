package cards;

import colour.Colour;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;

class CardSlotsTest {

    @Test
    void addCard() throws OperationNotSupportedException {
        CardSlots slots = new CardSlots();
        DevelopmentCard card = new DevelopmentCard(Colour.BLUE, 1);
        //adds level 1 card
        try {
            slots.addCard(0, card);
            slots.addCard(1, card);
            slots.addCard(2, card);
        }catch(OperationNotSupportedException e){
            //notify to controller
        }
        assertEquals(slots.getCard(0), card);
        assertEquals(slots.getCard(1), card);
        assertEquals(slots.getCard(2), card);

        //add level 2 card
        card = new DevelopmentCard(Colour.PURPLE, 2);
        try {
            slots.addCard(0, card);
            slots.addCard(2, card);
        }catch(OperationNotSupportedException e){}
        assertEquals(slots.getCard(0), card);
        assertEquals(slots.getCard(2), card);
        //doesn't add level 3 card on level 2 card
        card = new DevelopmentCard(Colour.BLUE, 3);
        try {
            slots.addCard(1, card);
        }catch(OperationNotSupportedException e){ }
        assertFalse(card.equals(slots.getCard(1)));
        //doesn't add level 2 cards as first level
        slots = new CardSlots();
        card = new DevelopmentCard(Colour.GREEN, 3);
        try{
            slots.addCard(1, card);
        }catch(Exception e){}
        assertNull(slots.getCard(1));
        //



    }
}