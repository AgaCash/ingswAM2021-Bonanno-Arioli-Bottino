package model.cards;

import clientModel.cards.LightDevelopmentCard;
import clientModel.colour.LightColour;
import clientModel.resources.LightResource;
import exceptions.UnusableCardException;
import model.colour.Colour;
import model.resources.Resource;

import java.util.ArrayList;

/**DevelopmentCard is a Card class representing the Development Cards. They can be bought in the DevelopmentBoard for a different coast for Card expressed by the attribute cost
 * If successfully purchased, the owner gain the so called "Production power": each card, for some Resources in input, can produce specific Resources that will be added to the Player's Strongbox
 * Each card has an amount of Victory Points, that will be summed at the end of Game
 * Each card has an own ID, and they're divided by Level (1, 2, 3) and Colour (Green, Blue, Yellow and Purple)
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
     *  Default constructor to create the card: instantiates a real DevelopmentCard in the DevelopmentBoard, that can be purchased by player
     *
     * @param id ID of the card
     * @param colour Colour of the card
     * @param level level (points on the flags)
     * @param points victory points
     * @param cost cost to purchase it
     * @param prodInput Resources required in production
     * @param prodOutput Resources given to user in production
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

    /**Attribute constructor: this represents a LeaderCard classes Attribute, and it's used for check the LeaderCard requirements (if can be activated).
     * It doesn't have an ID neither other information except for Colour and Level
     * @param colour
     * @param level
     */
    public DevelopmentCard(Colour colour, int level) {
        this.colour = colour;
        this.level = level;
    }

    /**Checks if DevelopmentCard instance is a real DevelopmentCard usable or an attribute
     * @return true if is a real DevelopmentCard, false if not
     */
    private boolean isUsable(){
        return this.cost != null || this.prodInput != null || this.prodOutput != null;
    }


    /**Runs the production. Can't be called more than one time in a turn per Card
     * It only generate the Resource, doesn't remove the @prodInput (see DevCardProduction() method in Game class)
     * @return the Resource ArrayList produced
     * @throws UnusableCardException if DevelopmentCard has already been used for production in this turn
     */
    public ArrayList<Resource> createProduction() throws UnusableCardException {
        if(usedInThisTurn)
            throw new UnusableCardException("DevCard already used in this turn!");

        ArrayList<Resource> outputResources = (ArrayList<Resource>) prodOutput.clone();
        if(!isUsable())
            throw new UnusableCardException();//non dovrebbe succedere mai

        this.usedInThisTurn = true;
        return outputResources;
    }

    /**Return the level
     * @return an int
     */
    public int getLevel() {
        return level;
    }

    /**Return the Resource ArrayList that must be used for activate the production
     * @return a copy of @prodInput ArrayLIst
     */
    public ArrayList<Resource> getProdInput(){
        return (ArrayList<Resource>) this.prodInput.clone();
    }

    public Colour getColour(){
        return colour;
    }

    public ArrayList<Resource> getCost(){
        return (ArrayList<Resource>) this.cost.clone();
    }

    public int getVictoryPoints(){
        return this.victoryPoints;
    }


    public void backUsable(){
        this.usedInThisTurn = false;
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

    //just 4 tests
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


