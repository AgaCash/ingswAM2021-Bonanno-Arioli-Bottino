package model.strongbox;

import org.junit.jupiter.api.Test;
import model.resources.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    @Test
    void addResourceTest() {
        Resource res = Resource.SHIELD; // using shield resource, but valid for every resource
        Strongbox sb = new Strongbox();
        sb.addResource(res);
        assertEquals(Resource.SHIELD, sb.getResource(0));
    }

    @Test
    void removeResource() {
        Resource res1 = Resource.COIN;
        Resource res2 = Resource.SHIELD; // using coin and shield model.resources, but valid for every resource
        Strongbox sb = new Strongbox();
        sb.addResource(res1);
        sb.addResource(res2);
        sb.removeResource(res1);
        assertEquals(Resource.SHIELD, sb.getResource(0));
    }

    @Test
    void isPresentTest() {
        Resource res1 = Resource.COIN;
        Resource res2 = Resource.SHIELD; // using coin and shield model.resources, but valid for every resource
        Strongbox sb = new Strongbox();
        ArrayList<Resource> arrRes = new ArrayList<>();
        arrRes.add(res2);
        arrRes.add(res1);
        sb.addResource(res1);
        sb.addResource(res2);
        sb.addResource(res2);
        assertTrue(sb.isPresent(arrRes));
        arrRes.add(res1);
        assertFalse(sb.isPresent(arrRes));
    }
}