package model.cards;

import exceptions.UnusableCardException;
import org.junit.jupiter.api.Test;
import model.resources.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void whichDiscount() {
        //testing working
        Discount card = new Discount(0, true, new ArrayList<DevelopmentCard>(), Resource.COIN);
        try {
            assertEquals(card.whichDiscount(), Resource.COIN);
            assertNotEquals(card.whichDiscount(), Resource.SERVANT);
            card = new Discount(0, false, new ArrayList<DevelopmentCard>(), Resource.COIN);
            card.whichDiscount();
        }catch (UnusableCardException e) {
            assertTrue(true);
        }
    }

    @Test
    void isDiscount() {
        Discount card = new Discount(0, true, new ArrayList<DevelopmentCard>(), null);
        ExtraDepot card2 = new ExtraDepot(0, true, new ArrayList<Resource>(), new ArrayList<Resource>());
        ExtraProd card3 = new ExtraProd(0, true, null, null);
        WhiteConverter card4 = new WhiteConverter(0, true, new ArrayList<DevelopmentCard>(), null);
        assertTrue(card.isDiscount());
        assertFalse(card2.isDiscount());
        assertFalse(card3.isDiscount());
        assertFalse(card4.isDiscount());
    }

    @Test
    void testToString() {
        int id = 0;
        ArrayList<DevelopmentCard> requires = new ArrayList<>();
        Resource discount = Resource.COIN;
        int victoryPoints = 2;
        boolean isEnabled = false;
        Discount card = new Discount(id, isEnabled, requires, discount);
        String s = "\nDISCOUNT";
        s+= "\nID: "+id;
        s+= "\nRequires: ";
        for (DevelopmentCard d:requires) {
            s+="\n\t "+d;
        }
        s+= "\nDiscount: " +discount;
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
        assertEquals(card.toString(), s);
    }
}