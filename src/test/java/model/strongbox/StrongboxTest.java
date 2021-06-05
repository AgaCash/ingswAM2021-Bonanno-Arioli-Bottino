package model.strongbox;
import clientModel.resources.LightResource;
import exceptions.ResourceNotFoundException;
import model.resources.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class StrongboxTest {
/*
    @Test
    void addResourceTest() {
        Resource res = Resource.SHIELD; // using shield resource, but valid for every resource
        Strongbox sb = new Strongbox();
        sb.addResource(res);
        assertEquals(Resource.SHIELD, sb.getResource(0));
    }
    */

    @Test
    void removeResource() {
        Resource res1 = Resource.COIN;
        Resource res2 = Resource.SHIELD; // using coin and shield model.resources, but valid for every resource
        Strongbox sb = new Strongbox();
        ArrayList<Resource> image = new ArrayList<>();

        sb.addResource(res1);
        sb.addResource(res2); image.add(res2);
        sb.updateStrongbox();
        try {
            sb.removeResource(res1);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(sb.isPresent(image));

        image.add(Resource.SHIELD);
        sb.addResource(Resource.SHIELD);
        sb.updateStrongbox();
        System.out.println(sb.convert());
        try{
            sb.removeResource(Resource.SHIELD);
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(sb.convert());
        assertFalse(sb.isPresent(image));
        image.remove(Resource.SHIELD);
        assertTrue(sb.isPresent(image));


    }

/*
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
    }*/



    @Test
    void convert(){
        ArrayList<Resource> src = new ArrayList<>();
        src.add(Resource.SHIELD); src.add(Resource.COIN);
        ArrayList<LightResource> convert = (ArrayList<LightResource>) src.clone();
        System.out.println(convert);
    }

}



