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
    private transient String fileName;

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

    public LightColour getColour(){
        return colour;
    }

    public int getPoints(){
            return this.victoryPoints;
    }

    private boolean isUsable(){
        return this.level!=-1 && this.cost != null && this.prodInput != null && this.prodOutput != null;
    }

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

    private String formatLevel(){
        return this.colour.toString()+"LV: "+this.level+"                       ";
    }

    private String formatVictoryPoints(){
        String s =this.colour.toString()+"VP: ";
        if(this.victoryPoints<10)
            s+="0";
        s+=this.victoryPoints;
        return s+"                      ";
    }

    private String formatCost(){
        String s=this.colour+"$$: ";
        for(LightResource r: this.cost)
            s+= r.toColoredString()+" ";
        for(int i=this.cost.size(); i<8; i++)
            s+="   ";
        return s;
    }

    private String formatInput(){
        String s=this.colour+"IP: ";
        for(LightResource r: this.prodInput)
            s+= r.toColoredString()+" ";
        for(int i=this.prodInput.size(); i<8; i++)
            s+="   ";
        return s;
    }

    private String formatOutput(){
        String s=this.colour+"OP: ";
        for(LightResource r: this.prodOutput)
            s+= r.toColoredString()+" ";
        for(int i=this.prodOutput.size(); i<8; i++)
            s+="   ";
        return s;
    }
}
