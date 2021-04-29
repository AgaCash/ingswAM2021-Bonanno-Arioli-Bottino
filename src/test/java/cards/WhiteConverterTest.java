package cards;

import org.junit.jupiter.api.Test;
import resources.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WhiteConverterTest {

    @Test
    void whichResource(){
        WhiteConverter card = new WhiteConverter(0, true, new ArrayList<DevelopmentCard>(), Resource.COIN);
        assertEquals(card.whichResource(), Resource.COIN);
        assertNotEquals(card.whichResource(), Resource.STONE);

        card = new WhiteConverter(0, false, new ArrayList<DevelopmentCard>(), Resource.COIN);
        assertNotEquals(card.whichResource(), Resource.COIN);
    }

    @Test
    void isWhiteConverter(){
        WhiteConverter card = new WhiteConverter(0, true, new ArrayList<DevelopmentCard>(), null);
        ExtraProd card4 = new ExtraProd(0, true, null, null);
        Discount card2 = new Discount(0, true, new ArrayList<DevelopmentCard>(), null);
        ExtraDepot card3 = new ExtraDepot(0, true, new ArrayList<Resource>(), new ArrayList<Resource>());
        assertTrue(card.isWhiteConverter());
        assertFalse(card2.isWhiteConverter());
        assertFalse(card3.isWhiteConverter());
        assertFalse(card4.isWhiteConverter());
    }

    @Test
    void testToString() {
        int id = 0;
        boolean isEnabled = true;
        ArrayList<DevelopmentCard> requires = new ArrayList<>();
        Resource resource = Resource.COIN;
        int victoryPoints = 5;
        WhiteConverter card = new WhiteConverter(id, isEnabled, requires, resource);
        String s = "\nWHITE CONVERTER";
        s+= "\nID: "+id;
        s+= "\nRequires: ";
        for (DevelopmentCard d:requires) {
            s+="\n\t "+d;
        }
        s+= "\nConvert: " +resource;
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
        assertEquals(card.toString(), s);
    }
}