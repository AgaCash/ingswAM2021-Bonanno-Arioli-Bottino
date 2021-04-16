package developmentCard;

import cards.DevelopmentCard;
import colour.Colour;
import org.junit.jupiter.api.Test;
import resources.Resource;
import strongbox.Strongbox;
import warehouse.WarehouseDepot;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class developmentCardTest {

    @Test
    void createCardTest(){
        int id = 2;
        Colour colour = Colour.GREEN;
        int level = 1;
        int points = 2;
        ArrayList<Resource> cost = new ArrayList<>();
        cost.add(Resource.STONE);
        ArrayList<Resource> prodInput = new ArrayList<>();
        prodInput.add(Resource.COIN);
        ArrayList<Resource> prodOutput = new ArrayList<>();
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c1 = new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        assertNotNull(c1);

        id = -1;
        colour = null;
        level = -1;
        points = 256;
        cost.clear();
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);
        cost.add(Resource.STONE);


        prodInput.clear();
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);
        prodInput.add(Resource.COIN);

        prodOutput.clear();
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        prodOutput.add(Resource.FAITH);
        DevelopmentCard c2 =new DevelopmentCard(id, colour, level, points,cost, prodInput,prodOutput);
        assertNotNull(c2);

        assertNotEquals(c1, c2);


    }

    @Test
    void createProductionTest(){
        ArrayList<Resource> cost = new ArrayList<>();
        ArrayList<Resource> pi = new ArrayList<>();
        ArrayList<Resource> po = new ArrayList<>();
        cost.add(Resource.STONE);
        pi.add(Resource.COIN);
        po.add(Resource.SHIELD);
        DevelopmentCard d = new DevelopmentCard(2, Colour.BLUE, 1, 1, cost, pi, po);

        WarehouseDepot w = new WarehouseDepot();
        Strongbox s = new Strongbox();

        d.createProduction(w, s);
        assertFalse(s.isPresent(po));

        w.addResource(pi.get(0));
        d.createProduction(w, s);
        assertTrue(s.isPresent(po));
        assertFalse(w.isPresent(pi));
    }
}