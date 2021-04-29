package cards;

import org.junit.jupiter.api.Test;
import resources.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ExtraProdTest {

    @Test
    void getRequirement() {
        ExtraProd card = new ExtraProd(0, true,null, Resource.COIN);
        assertEquals(card.getRequirement(), Resource.COIN);
        assertNotEquals(card.getRequirement(), Resource.FAITH);

        card = new ExtraProd(0, false, null, Resource.COIN);
        assertNotEquals(card.getRequirement(), Resource.COIN);
    }

    @Test
    void production() {
        ExtraProd card = new ExtraProd(0, true, null, Resource.COIN);
        card.setChosenOutput(Resource.SHIELD);
        ArrayList<Resource> prod = card.production();
        assertEquals(prod.get(0), Resource.FAITH);
        assertEquals(prod.get(1), Resource.SHIELD);
    }

    @Test
    void isExtraProd() {

        ExtraProd card = new ExtraProd(0, true, null, null);
        Discount card2 = new Discount(0, true, new ArrayList<DevelopmentCard>(), null);
        ExtraDepot card3 = new ExtraDepot(0, true, new ArrayList<Resource>(), new ArrayList<Resource>());
        WhiteConverter card4 = new WhiteConverter(0, true, new ArrayList<DevelopmentCard>(), null);
        assertTrue(card.isExtraProd());
        assertFalse(card2.isExtraProd());
        assertFalse(card3.isExtraProd());
        assertFalse(card4.isExtraProd());
    }

    @Test
    void testToString() {
        int id = 0;
        DevelopmentCard req = null;
        Resource input = Resource.COIN;
        int victoryPoints = 4;
        boolean isEnabled = false;
        Resource output = Resource.FAITH;
        ExtraProd card = new ExtraProd(id, isEnabled, req, input);
        String s = "\nEXTRA PROD";
        s+= "\nID: "+id;
        s+= "\nRequires: ";
        s+="\n\t Input: "+input;
        s+="\nProduce: \n\t"+output+" and a chosen resource";
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
    }
}