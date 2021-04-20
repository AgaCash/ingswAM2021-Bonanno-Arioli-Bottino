package cards;

import colour.Colour;
import resources.Resource;
import strongbox.Strongbox;
import warehouse.WarehouseDepot;

import java.util.ArrayList;

public class DevelopmentCard extends Card {
    /**
     * Class representing the real Development Card
     */


    private Colour colour;
    private int level;
    private int points;
    private ArrayList<Resource> cost;
    private ArrayList<Resource> prodInput;
    private ArrayList<Resource> prodOutput;

    /**
     *  Default constructor to create the card
     *
     * @param id id of the card
     * @param colour color of the card
     * @param level level (points on the flags)
     * @param points victory points
     * @param cost cost to purchase it
     * @param prodInput resources required in production
     * @param prodOutput resources given to user in production
     */


    public DevelopmentCard(int id, Colour colour, int level, int points, ArrayList<Resource> cost, ArrayList<Resource> prodInput, ArrayList<Resource> prodOutput) {
        this.cardId = id;
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

    /**
     *  Method that allow to activate the production
     * @param warehouseDepot user warehouse
     * @param strongbox user strongbox
     */

    public void createProduction(WarehouseDepot warehouseDepot, Strongbox strongbox){

        if(!warehouseDepot.isPresent(prodInput)) {
            System.out.println("RESOURCES NOT PRESENT");
            return;
        }

        prodInput.forEach(warehouseDepot::removeResource);

        prodOutput.forEach(strongbox::addResource);
    }

    public Colour getColour(){
        return colour;
    }


}
