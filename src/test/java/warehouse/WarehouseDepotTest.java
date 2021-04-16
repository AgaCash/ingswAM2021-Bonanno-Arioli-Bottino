package warehouse;

import cards.LeaderCard;
import org.junit.jupiter.api.Test;
import resources.Resource;
import strongbox.Strongbox;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDepotTest {

    @Test
    void addResourceTest() {
        Resource res1 = Resource.COIN;
        Resource res2 = Resource.SHIELD; // using coin and shield resources, but valid for every resource
        WarehouseDepot wd = new WarehouseDepot();
        wd.addResource(res1);
        wd.addResource(res2);
        assertEquals(Resource.COIN, wd.getResource(0));
        assertEquals(Resource.SHIELD, wd.getResource(1));
    }

    @Test
    void removeResourceTest() {
        Resource res1 = Resource.COIN;
        Resource res2 = Resource.SHIELD; // using coin and shield resources, but valid for every resource
        WarehouseDepot wd = new WarehouseDepot();
        wd.addResource(res1);
        wd.addResource(res2);
        wd.removeResource(res1);
        assertEquals(Resource.SHIELD, wd.getResource(0));
    }

    @Test
    void checkArrayTest() {
        WarehouseDepot wd = new WarehouseDepot();
        Resource res = Resource.SERVANT;
        wd.addResource(res);
        assertFalse(wd.checkArray(Resource.COIN));
    }

    @Test
    void isPresentTest() {
        //it presents the same code as Strongbox' isPresentTest, the test of this method refers to the Strongbox one
    }
}