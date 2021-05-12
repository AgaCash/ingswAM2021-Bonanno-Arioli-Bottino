package model.cards;

import model.colour.Colour;
import model.resources.Resource;

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
     * @param prodInput model.resources required in production
     * @param prodOutput model.resources given to user in production
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
     *
     */

    public ArrayList<Resource> createProduction(ExtraProd extraProd){
        ArrayList<Resource> outputResources = (ArrayList<Resource>) prodOutput.clone();
        //paranoico
        if(!isUsable())
            return null;

        if(extraProd!=null && extraProd.isEnabled())
                outputResources.addAll(extraProd.production());
        return outputResources;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Resource> getProdInput(){
        return this.prodInput;
    }

    public Colour getColour(){
        return colour;
    }

    public ArrayList<Resource> getCost(){
        return this.cost;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("\nColour: ").append(colour);
        s.append("\nLevel: ").append(level);
        if(isUsable()){
            s.append("\nID: ").append(id);
            s.append("\nVictory Points: ").append(victoryPoints);
            s.append("\nCost: ");
            for (Resource resource : cost) {
                s.append("\n\t").append(resource);
            }
            s.append("\nProdInput: ");
            for (Resource resource : prodInput) {
                s.append("\n\t").append(resource);
            }
            s.append("\nProdOutput: ");
            for (Resource resource : prodOutput) {
                s.append("\n\t").append(resource);
            }
        }
        return s.toString();
    }




}


