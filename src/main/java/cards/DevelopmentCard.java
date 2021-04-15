package cards;

import colour.Colour;
import resources.Resource;
import strongbox.Strongbox;
import warehouse.WarehouseDepot;

import java.util.ArrayList;

public class DevelopmentCard extends Card {
    private Colour colour;
    private int level;
    private int points;
    private ArrayList<Resource> cost;
    private ArrayList<Resource> prodInput;
    private ArrayList<Resource> prodOutput;

    public DevelopmentCard(Colour colour, int level, int points, ArrayList<Resource> cost, ArrayList<Resource> prodInput, ArrayList<Resource> prodOutput) {
        this.colour = colour;
        this.level = level;
        this.points = points;
        this.cost = cost;
        this.prodInput = prodInput;
        this.prodOutput = prodOutput;
    }

    public DevelopmentCard(Colour colour, int level) {
        this.colour = colour;
        this.level = level;
    }

    public Colour getColour() {
        return colour;
    }

    public int getLevel() {
        return level;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<Resource> getCost() {
        return cost;
    }

    public ArrayList<Resource> getProdInput() {
        return prodInput;
    }

    public ArrayList<Resource> getProdOutput() {
        return prodOutput;
    }

    public void createProduction(WarehouseDepot warehouseDepot, Strongbox strongbox){
        /*
         *  Check if prodInput is in warehouseDepot:
         *      OK -> remove prodInput in warehouseDepot && add in strongbox.temporary prodOutput
         *      KO -> throws an exception
         */
    }


}
