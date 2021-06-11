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
        if(isUsable() && this.victoryPoints!=-1)
            return this.prodInput;
        return new ArrayList<>();
    }

    public LightColour getColour(){
        return colour;
    }

    public ArrayList<LightResource> getCost(){
        if(isUsable() && this.victoryPoints!=-1)
            return this.cost;
        return new ArrayList<>();
    }

    public ArrayList<LightResource> getProdOutput(){
        if(isUsable() && this.victoryPoints!=-1)
            return this.prodOutput;
        return new ArrayList<>();
    }

    public int getPoints(){
            return this.victoryPoints;
    }

    public boolean isUsable(){
        return this.cost != null && this.prodInput != null && this.level !=1 && this.prodOutput != null;
    }
//CLI
    @Override
    public String toString(){
        String s = new String();
        if(isUsable()){
            s +="\n__________________________________\n";
            s +=toStringLevel()+"\n";
            s +=toStringPoints()+"\n";
            s +=toStringCost()+"\n";
            s += toStringProdInput()+"\n";
            s += toStringProdOutput()+"\n";
            s += "__________________________________\n";
        }
        else if(this.level!= -1){
            s += colour.toString()+"|DevCard Lvl: "+level+LightColour.WHITE;
        }
        return s;
    }

    public String toStringLevel(){
        return colour.toString()+"Level: "+level;
    }

    public String toStringPoints(){
        return colour.toString()+"Victory Points: "+victoryPoints;
    }

    public String toStringCost(){
        String s =colour.toString()+"Cost: ";
        for(LightResource resource : cost)
            s += resource.toColoredString()+" ";
        return s;
    }

    public String toStringProdInput(){
        String s = colour.toString()+"ProdInput: ";
        for(LightResource resource : prodInput)
            s += resource.toColoredString()+ " ";
        return s;
    }

    public String toStringProdOutput(){
        String s = colour.toString()+"ProdOutput: ";
        for (LightResource resource : prodOutput)
            s += resource.toColoredString()+" ";
        return s;
    }
}
