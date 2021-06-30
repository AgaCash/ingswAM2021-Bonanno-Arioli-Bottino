package clientModel.cards;

import clientModel.colour.LightColour;
import clientModel.resources.LightResource;

import java.util.ArrayList;

/**
 * The LightModel copy of Model's DevelopmentCard
 */
public class LightDevelopmentCard {
    private int id;
    private LightColour colour;
    private int level;
    private int victoryPoints;
    private ArrayList<LightResource> cost;
    private ArrayList<LightResource> prodInput;
    private ArrayList<LightResource> prodOutput;
    private transient String fileName;

    /**Constructor. The instance represents a copy of DevelopmentCard in Model
     * @param id the Card id
     * @param colour the Card Colour
     * @param level the level
     * @param points the amount of Victory Points
     * @param cost the Resources required to purchase the Card in the DevelopmentBoard
     * @param prodInput the Resource ArrayList to activate production
     * @param prodOutput the Resource ArrayList produced
     */
    public LightDevelopmentCard(int id, LightColour colour, int level, int points, ArrayList<LightResource> cost, ArrayList<LightResource> prodInput, ArrayList<LightResource> prodOutput) {
        this.id = id;
        this.colour = colour;
        this.level = level;
        this.victoryPoints = points;
        this.cost = cost;
        this.prodInput = prodInput;
        this.prodOutput = prodOutput;
        fileName = "devCard-"+id+".png";
    }

    /**Returns Card's  ID
     * @return an int
     */
    public int getId() {
        return id;
    }


    /**Constructor representing a LeaderCard attribute for checks
     * @param colour
     * @param level
     */
    public LightDevelopmentCard(LightColour colour, int level) {
        this.colour = colour;
        this.level = level;
    }

    /**Constructor of an instance representing an empty CardSlot or Deck in LightDevelopmentBoard
     */
    public LightDevelopmentCard(){
        this.level = -1;
    }

    /**Returns Card's level
     * @return an int
     */
    public int getLevel() {
        return level;
    }

    /**Returns Card's Colour
     * @return a LightColour instance
     */
    public LightColour getColour(){
        return colour;
    }

    /**Returns the Victory Points
     * @return an int
     */
    public int getPoints(){
            return this.victoryPoints;
    }

    /**Returns true if instance represents a Model's DevelopmentCard copy, false if represents an attribute or a "null" card
     * @return a boolean
     */
    private boolean isUsable(){
        return this.level!=-1 && this.cost != null && this.prodInput != null && this.prodOutput != null;
    }

    /**Prints the DevelopmentCard in CLI
     * @return
     */
    @Override
    public String toString(){
        String s= "";
        if(isUsable()){
            s+=formatLevel()+"\n";
            s+=formatVictoryPoints()+"\n";
            s+=formatCost()+"\n";
            s+=formatInput()+"\n";
            s+=formatOutput()+"\n";
        }
        else{
            if(this.level!=-1)
                s+=this.colour.toString()+"LV: "+this.level;
            else
                s="                            \n" +
                        "                            \n" +
                        "                            \n" +
                        "                            \n" +
                        "                            \n";
        }
        return s+LightColour.WHITE;
    }

    /**Format the Card's level print for CLI if Card is usable
     * @return a String
     */
    private String formatLevel(){
        return this.colour.toString()+"LV: "+this.level+"                       ";
    }

    /**Format the Card's victory points print for CLI if Card is usable
     * @return a String
     */
    private String formatVictoryPoints(){
        String s =this.colour.toString()+"VP: ";
        if(this.victoryPoints<10)
            s+="0";
        s+=this.victoryPoints;
        return s+"                      ";
    }

    /**Format the Card's cost print for CLI if Card is usable
     * @return a String
     */
    private String formatCost(){
        String s=this.colour+"$$: ";
        for(LightResource r: this.cost)
            s+= r.toColoredString()+" ";
        for(int i=this.cost.size(); i<8; i++)
            s+="   ";
        return s;
    }

    /**Format the Card production input Resources print for CLI if Card is usable
     * @return a String
     */
    private String formatInput(){
        String s=this.colour+"IP: ";
        for(LightResource r: this.prodInput)
            s+= r.toColoredString()+" ";
        for(int i=this.prodInput.size(); i<8; i++)
            s+="   ";
        return s;
    }

    /**Format the Card production output Resources print for CLI if Card is usable
     * @return a String
     */
    private String formatOutput(){
        String s=this.colour+"OP: ";
        for(LightResource r: this.prodOutput)
            s+= r.toColoredString()+" ";
        for(int i=this.prodOutput.size(); i<8; i++)
            s+="   ";
        return s;
    }
}
