package model.table;

import clientModel.table.LightMarketBoard;

import exceptions.InvalidActionException;
import exceptions.UnusableCardException;
import model.resources.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MarketBoardTest {

    @Test
    void initializeMarbleGrid() {

    }

    @Test
    void addMarketLine() {
        MarketBoard market = new MarketBoard();
        LightMarketBoard image = market.convert();

        System.out.println(image.toString());
        ArrayList<Resource> bought = null;
        try {
            bought = market.addMarketLine(0, null);
            System.out.println(bought);

        }catch(UnusableCardException e){
            System.out.println(e.getMessage());
        }

        image = market.convert();
        System.out.println(image.toString());

        try {
            bought = market.addMarketLine(0, null);
            System.out.println(bought);
        }catch(UnusableCardException e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    void addMarketColumn() {
    }

    @Test
    void getMarble() {

    }
}