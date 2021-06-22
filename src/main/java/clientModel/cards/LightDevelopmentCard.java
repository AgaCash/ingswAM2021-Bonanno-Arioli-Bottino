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

    private boolean isUsable(){
        return this.cost != null && this.prodInput != null && this.prodOutput != null;
    }

    private boolean isNull(){
        if(this.level !=-1)
            return false;
        return true;
    }
//CLI
    @Override
    public String toString(){
        String s = new String();
        if(isUsable()){
            s +="\n__________________________________\n";
            s +=toStringLevel()+insertTabs(24)+"\n";
            s +=toStringPoints()+ insertTabs(15-getPoints()/10)+"\n";
            s +=toStringCost()+ insertTabs(32-6-3*getCost().size())+"\n";
            s += toStringProdInput()+ insertTabs(32 - 11 - 3*getProdInput().size())+"\n";
            s += toStringProdOutput()+ insertTabs(32 -12  -3*getProdOutput().size())+"\n";
            s += LightColour.WHITE+"__________________________________\n";
        }
        else if(!isNull()){
            s += colour.toString()+"|DevCard Lvl: "+level+LightColour.WHITE;
        }
        return s;
    }

    public String toStringLevel(){
        if(isNull())
            return "                                ";
        return colour.toString()+"Level: "+level;
    }

    public String toStringPoints(){
        if(isNull())
            return "                                ";
        return colour.toString()+"Victory Points: "+victoryPoints;
    }

    public String toStringCost(){
        if(isNull())
            return "                                ";
        String s =colour.toString()+"Cost: ";
        for(LightResource resource : cost)
            s += resource.toColoredString()+" ";
        return s;
    }

    public String toStringProdInput(){
        if(isNull())
            return "                                ";
        String s = colour.toString()+"ProdInput: ";
        for(LightResource resource : prodInput)
            s += resource.toColoredString()+ " ";
        return s;
    }

    public String toStringProdOutput(){
        if(isNull())
            return "                                ";
        String s = colour.toString()+"ProdOutput: ";
        for (LightResource resource : prodOutput)
            s += resource.toColoredString()+" ";
        return s;
    }


    private String insertTabs(int num){
        String s = new String();
        for(int i=0; i<num; i++)
            s+=" ";
        return s;
    }
}
