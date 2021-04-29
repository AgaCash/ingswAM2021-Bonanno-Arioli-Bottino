package warehouse;

import cards.ExtraDepot;
import org.junit.jupiter.api.Test;
import resources.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WarehouseDepotTest {
    WarehouseDepot w = new WarehouseDepot();

    @Test
    void addNewExtraDepot() {
        //some initializing
        ArrayList<Resource> addedResources = new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(Resource.SERVANT); resources.add(Resource.SERVANT);
        ExtraDepot card1 = new ExtraDepot(0, true, null, resources);
        resources.remove(Resource.SERVANT); resources.remove(Resource.SERVANT);
        resources.add(Resource.COIN); resources.add(Resource.COIN);
        ExtraDepot card2 = new ExtraDepot(0, false, null, resources);

        w.addNewExtraDepot(card1);
        //test if card1 is working
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        assertTrue(w.isPresent(addedResources));
        //test if not adds more than 6 items
        addedResources.add(Resource.SERVANT);
        assertFalse(w.isPresent(addedResources));

        w = new WarehouseDepot();
        w.addNewExtraDepot(card2);
        addedResources = new ArrayList<>();
        //test if cards are not enabled
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        assertFalse(w.isPresent(addedResources));
        //test with other resources
        card2 = new ExtraDepot(0, true, null, resources);
        WarehouseDepot w3 = new WarehouseDepot();
        addedResources = new ArrayList<>();
        w3.addNewExtraDepot(card1);
        w3.addNewExtraDepot(card2);
        w3.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w3.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w3.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w3.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w3.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w3.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w3.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w3.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w3.addResource(Resource.SHIELD); addedResources.add(Resource.SHIELD);
        assertTrue(w3.isPresent(addedResources));


    }

    @Test
    void addResource() {
        ArrayList<Resource> addedResources = new ArrayList<>();
        //test adding 1 resource of the same type
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        assertTrue(w.isPresent(addedResources));
        //test adding 3 resources of the same type
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        assertTrue(w.isPresent(addedResources));
        //test adding 4 resources of the same type
        w.addResource(Resource.COIN); addedResources.add(Resource.COIN);
        assertFalse(w.isPresent(addedResources));
        //test adding 2 another resources
        addedResources.remove(Resource.COIN);
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        assertTrue(w.isPresent(addedResources));
        //test adding 1 another resource
        w.addResource(Resource.SHIELD); addedResources.add(Resource.SHIELD);
        assertTrue(w.isPresent(addedResources));
        //now warehouse is full... test adding something
        w.addResource(Resource.SHIELD); addedResources.add(Resource.SHIELD);
        assertFalse(w.isPresent(addedResources));
        w.addResource(Resource.SERVANT); addedResources.add(Resource.SERVANT);
        assertFalse(w.isPresent(addedResources));
    }

    @Test
    void removeResource() {
        ArrayList<Resource> removedResources = new ArrayList<>();
        //there's only one instance of this
        w.addResource(Resource.SHIELD);
        w.removeResource(Resource.SHIELD); removedResources.add(Resource.SHIELD);
        assertFalse(w.isPresent(removedResources));
        //there are two servants
        //test that delete one don't delete both
        removedResources.remove(Resource.SHIELD);
        w.addResource(Resource.SERVANT); w.addResource(Resource.SERVANT);
        w.removeResource(Resource.SERVANT); removedResources.add(Resource.SERVANT);
        assertTrue(w.isPresent(removedResources));
    }

}