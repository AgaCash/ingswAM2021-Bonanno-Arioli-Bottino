package model.cards;

import model.colour.Colour;
import model.resources.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    /*
    @Test
    void createProductionTest() throws OperationNotSupportedException {
        ArrayList<Resource> cost = new ArrayList<>();
        ArrayList<Resource> pi = new ArrayList<>();
        ArrayList<Resource> po = new ArrayList<>();
        ExtraProd extraProd = new ExtraProd(0, true, null, Resource.COIN);
        extraProd.setChosenOutput(Resource.SERVANT);
        //WhiteConverter whiteConverter = new WhiteConverter(0, true, null, Resource.COIN);

        cost.add(Resource.STONE);
        pi.add(Resource.COIN);
        po.add(Resource.SHIELD);
        DevelopmentCard d = new DevelopmentCard(2, Colour.BLUE, 1, 1, cost, pi, po);

        WarehouseDepot w = new WarehouseDepot();
        w.addResource(Resource.COIN);
        w.addResource(Resource.SHIELD);
        w.addResource(Resource.COIN);
        Strongbox s = new Strongbox();


        //d.createProduction(extraProd);
        //assertFalse(s.isPresent(po));

        //s.addResource(Resource.COIN);
        ArrayList<Resource> prod = d.createProduction(extraProd);
        po.add(Resource.COIN);
        assertEquals(po, prod);
        //assertFalse(w.isPresent(pi));
    }*/

    @Test
    void checkErrorsTest(){

    }
}
