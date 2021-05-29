package clientModel.cards;

import clientModel.colour.LightColour;
import clientModel.resources.LightResource;

import java.util.ArrayList;

public class LightDevelopmentCard {
    private int id;
    private LightColour colour;
    private int level;
    private int victoryPoints;
    private ArrayList<LightResource> cost;
    private ArrayList<LightResource> prodInput;
    private ArrayList<LightResource> prodOutput;

    public LightDevelopmentCard(int id, LightColour colour, int level, int points, ArrayList<LightResource> cost, ArrayList<LightResource> prodInput, ArrayList<LightResource> prodOutput) {
        this.id = id;
        this.colour = colour;
        this.level = level;
        this.victoryPoints = points;
        this.cost = cost;
        this.prodInput = prodInput;
        this.prodOutput = prodOutput;
    }

    public LightDevelopmentCard(LightColour colour, int level) {
        this.colour = colour;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<LightResource> getProdInput(){
        return this.prodInput;
    }

    public LightColour getColour(){
        return colour;
    }

    public ArrayList<LightResource> getCost(){
        return this.cost;
    }


    /*public boolean checkCard (ArrayList<LightResource> required){
        boolean check;
        check = LightStrongbox.isPresent(required);
        if(!check || required!=null)
            check = LightWarehouse.isPresent(required);
        return check;
    }
    */
}
