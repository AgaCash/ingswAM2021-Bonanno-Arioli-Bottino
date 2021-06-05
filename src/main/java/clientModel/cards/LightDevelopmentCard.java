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

    public LightDevelopmentCard(){
        this.level = -1;
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

    private boolean isUsable(){
        return this.cost != null || this.prodInput != null || this.prodOutput != null;
    }


    /*public boolean checkCard (ArrayList<LightResource> required){
        boolean check;
        check = LightStrongbox.isPresent(required);
        if(!check || required!=null)
            check = LightWarehouse.isPresent(required);
        return check;
    }
    */

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        if(this.level == -1)
            return "\n____________________\n" +
                   "|     DECK VUOTO    |\n" +
                    "____________________\n";
        if(isUsable()){
            s.append("\n____________________\n");
            s.append("\n|Colour: ").append(colour);
            s.append("\n|Level: ").append(level);
            s.append("\n|Victory Points: ").append(victoryPoints);
            s.append("\n|Cost: ");
            for (LightResource resource : cost) {
                s.append("\n|\t").append(resource);
            }
            s.append("\n|ProdInput: ");
            for (LightResource resource : prodInput) {
                s.append("\n|\t").append(resource);
            }
            s.append("\n|ProdOutput: ");
            for (LightResource resource : prodOutput) {
                s.append("\n|\t").append(resource);
            }
            s.append("\n____________________\n");
        }
        else{
            s.append("\n|Colour: ").append(colour);
            s.append("\n|Level: ").append(level);
        }
        return s.toString();
    }
}
