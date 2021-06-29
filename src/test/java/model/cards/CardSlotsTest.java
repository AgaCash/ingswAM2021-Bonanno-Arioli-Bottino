package model.cards;

import exceptions.FullCardSlotException;
import exceptions.NonCorrectLevelCardException;
import model.colour.Colour;
import org.junit.jupiter.api.Test;
import model.resources.Resource;

import javax.naming.OperationNotSupportedException;

import java.util.ArrayList;

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
        } catch (FullCardSlotException e) {
            e.printStackTrace();
        } catch (NonCorrectLevelCardException e) {
            e.printStackTrace();
        }
        assertEquals(slots.getCard(0), card);
        assertEquals(slots.getCard(1), card);
        assertEquals(slots.getCard(2), card);

        //add level 2 card
        card = new DevelopmentCard(Colour.PURPLE, 2);
        try {
            slots.addCard(0, card);
            slots.addCard(2, card);
        }catch(FullCardSlotException | NonCorrectLevelCardException e){}
        assertEquals(slots.getCard(0), card);
        assertEquals(slots.getCard(2), card);
        //doesn't add level 3 card on level 2 card
        card = new DevelopmentCard(Colour.BLUE, 3);
        try {
            slots.addCard(1, card);
        }catch(FullCardSlotException | NonCorrectLevelCardException e){ }
        assertFalse(card.equals(slots.getCard(1)));
        //doesn't add level 2 model.cards as first level
        slots = new CardSlots();
        card = new DevelopmentCard(Colour.GREEN, 3);
        try{
            slots.addCard(1, card);
        }catch(Exception e){}
        assertNull(slots.getCard(1));
        //
    }
    @Test
    void isPresent() throws OperationNotSupportedException {
        CardSlots slots = new CardSlots();
        DevelopmentCard card1 = new DevelopmentCard(1, Colour.BLUE, 1, 1,
                                                    new ArrayList<Resource>(),
                                                    new ArrayList<Resource>(),
                                                    new ArrayList<Resource>());
        DevelopmentCard card2 = new DevelopmentCard(2, Colour.BLUE, 1, 1,
                new ArrayList<Resource>(),
                new ArrayList<Resource>(),
                new ArrayList<Resource>());
        DevelopmentCard card3 = new DevelopmentCard(3, Colour.BLUE, 1, 1,
                new ArrayList<Resource>(),
                new ArrayList<Resource>(),
                new ArrayList<Resource>());
        DevelopmentCard card4 = new DevelopmentCard(4, Colour.BLUE, 2, 1,
                new ArrayList<Resource>(),
                new ArrayList<Resource>(),
                new ArrayList<Resource>());
        DevelopmentCard card5 = new DevelopmentCard(5, Colour.BLUE, 3, 1,
                new ArrayList<Resource>(),
                new ArrayList<Resource>(),
                new ArrayList<Resource>());
        try {
            slots.addCard(0, card1);
            slots.addCard(1, card2);
            slots.addCard(2, card3);
            slots.addCard(1, card4);
            slots.addCard(1, card5);
        } catch (FullCardSlotException e) {
            e.printStackTrace();
        } catch (NonCorrectLevelCardException e) {
            e.printStackTrace();
        }

        assertTrue(slots.isPresent(card1));
        assertTrue(slots.isPresent(card2));
        assertTrue(slots.isPresent(card3));
        assertTrue(slots.isPresent(card4));
        assertTrue(slots.isPresent(card5));



    }

}