package model.cards;

import org.junit.jupiter.api.Test;
import model.resources.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExtraDepotTest {

    @Test
    void addResource() {
        ArrayList<Resource> extra = new ArrayList<>();
        extra.add(Resource.COIN);
        extra.add(Resource.COIN);
        ExtraDepot card = new ExtraDepot(0, true, new ArrayList<Resource>(), extra);
        assertTrue(card.addResource(Resource.COIN));
        assertTrue(card.addResource(Resource.COIN));
        assertFalse(card.addResource(Resource.COIN));

        card = new ExtraDepot(0, true, new ArrayList<Resource>(), extra);
        assertFalse(card.addResource(Resource.SHIELD));

        card = new ExtraDepot(0, false, new ArrayList<Resource>(), extra);
        assertFalse(card.addResource(Resource.COIN));
    }

    @Test
    void removeResource() {
        ArrayList<Resource> extra = new ArrayList<>();
        extra.add(Resource.COIN);
        extra.add(Resource.COIN);
        ExtraDepot card = new ExtraDepot(0, true, new ArrayList<Resource>(), extra);
        card.addResource(Resource.COIN);
        card.addResource(Resource.COIN);
        assertTrue(card.removeResource(Resource.COIN));
        assertTrue(card.removeResource(Resource.COIN));
        assertFalse(card.removeResource(Resource.COIN));

        card = new ExtraDepot(0, true, new ArrayList<Resource>(), extra);
        assertFalse(card.removeResource(Resource.COIN));

        card.addResource(Resource.COIN);
        assertFalse(card.removeResource(Resource.SERVANT));

    }

    @Test
    void isExtraDepot() {
        ExtraDepot card = new ExtraDepot(0, true, new ArrayList<Resource>(), new ArrayList<Resource>());
        Discount card2 = new Discount(0, true, new ArrayList<DevelopmentCard>(), null);
        ExtraProd card3 = new ExtraProd(0, true, null, null);
        WhiteConverter card4 = new WhiteConverter(0, true, new ArrayList<DevelopmentCard>(), null);
        assertTrue(card.isExtraDepot());
        assertFalse(card2.isExtraDepot());
        assertFalse(card3.isExtraDepot());
        assertFalse(card4.isExtraDepot());
    }

    @Test
    void testToString() {
        int id = 0;
        ArrayList<Resource> requiredResource = new ArrayList<>();
        ArrayList<Resource> extraDepotResource = new ArrayList<>();
        int victoryPoints = 3;
        boolean isEnabled = false;
        ExtraDepot card = new ExtraDepot(id, isEnabled, requiredResource, extraDepotResource);
        String s = "\nEXTRA DEPOT";
        s+= "\nID: "+id;
        s+= "\nRequires: ";
        for (Resource r:requiredResource) {
            s+="\n\t "+r;
        }
        s+="\nExtra model.resources: ";
        for(Resource r: extraDepotResource ){
            s+="\n\t "+r;
        }
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
        assertEquals(card.toString(), s);
    }
}