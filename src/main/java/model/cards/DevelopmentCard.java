package model.cards;

import clientModel.cards.LightDevelopmentCard;
import clientModel.colour.LightColour;
import clientModel.resources.LightResource;
import exceptions.UnusableCardException;
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
    private boolean usedInThisTurn;

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
    public ArrayList<Resource> createProduction(ExtraProd extraProd) throws UnusableCardException {
        if(usedInThisTurn)
            throw new UnusableCardException("DevCard already used in this turn!");

        ArrayList<Resource> outputResources = (ArrayList<Resource>) prodOutput.clone();
        if(!isUsable())
            throw new UnusableCardException();//non dovrebbe succedere mai

        if(extraProd!=null && extraProd.isEnabled())
                outputResources.addAll(extraProd.production());
        this.usedInThisTurn = true;
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

    public void backUsable(){
        this.usedInThisTurn = false;
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

    public LightDevelopmentCard convert(){
        ArrayList<LightResource> cost = new ArrayList<>();
        ArrayList<LightResource> prodInput = new ArrayList<>();
        ArrayList<LightResource> prodOutput = new ArrayList<>();
        LightColour colour = LightColour.valueOf(this.colour.toString());
        if(isUsable()) {
            this.cost.forEach(element -> cost.add(LightResource.valueOf(element.toString())));
            this.prodInput.forEach(element -> prodInput.add(LightResource.valueOf(element.toString())));
            this.prodOutput.forEach(element -> prodOutput.add(LightResource.valueOf(element.toString())));

            return new LightDevelopmentCard(this.id,
                    colour,
                    this.level,
                    this.victoryPoints,
                    cost,
                    prodInput,
                    prodOutput);
        }
        else
            return new LightDevelopmentCard(colour,
                    this.level);
    }




}


