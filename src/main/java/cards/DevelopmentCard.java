package cards;

import colour.Colour;
import resources.Resource;
import strongbox.Strongbox;
import warehouse.WarehouseDepot;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

/**
 * Class representing the real Development Card
 */
public class DevelopmentCard extends Card {

    private Colour colour;
    private int level;
    private int victoryPoints;
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
        this.id = id;
        this.colour = colour;
        this.level = level;
        this.victoryPoints = points;
        this.cost = cost;
        this.prodInput = prodInput;
        this.prodOutput = prodOutput;
    }

    public DevelopmentCard(Colour colour, int level) {
        this.colour = colour;
        this.level = level;
    }

    private boolean isUsable(){
        return this.cost != null || this.prodInput != null || this.prodOutput != null;
    }

    /**
     *  Method that allow to activate the production
     * @param warehouseDepot user warehouse
     * @param strongbox user strongbox
     */

    public void createProduction(WarehouseDepot warehouseDepot, Strongbox strongbox) throws OperationNotSupportedException {
        if(!isUsable())
            throw new OperationNotSupportedException();
        if(!warehouseDepot.isPresent(prodInput)) {
            System.out.println("RESOURCES NOT PRESENT");
            return;
        }

        prodInput.forEach(warehouseDepot::removeResource);

        prodOutput.forEach(strongbox::addResource);
    }

    @Override
    public String toString(){
        String s = "";
        s += "\nColour: "+colour;
        s += "\nLevel: "+level;
        if(isUsable()){
            s += "\nID: "+id;
            s += "\nVictory Points: "+victoryPoints;
            s += "\nCost: ";
            for (Resource resource : cost) {
                s += "\n\t"+ resource;
            }
            s += "\nProdInput: ";
            for (Resource resource : prodInput) {
                s += "\n\t"+ resource;
            }
            s += "\nProdOutput: ";
            for (Resource resource : prodOutput) {
                s += "\n\t"+ resource;
            }
        }
        return s;
    }


}


