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
        String s= new String();
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
        }
        return s+LightColour.WHITE;
    }

    private String formatLevel(){
        return this.colour.toString()+"LV: "+String.valueOf(this.level);
    }

    private String formatVictoryPoints(){
        String s =this.colour.toString()+"VP: ";
        if(this.victoryPoints<10)
            s+="0";
        s+=this.victoryPoints;
        return s;
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
        for(int i=this.prodInput.size(); i<2; i++)
            s+="   ";
        return s;
    }

    private String formatOutput(){
        String s=this.colour+"OP: ";
        for(LightResource r: this.prodOutput)
            s+= r.toColoredString()+" ";
        for(int i=this.prodOutput.size(); i<4; i++)
            s+="   ";
        return s;
    }
//CLI
   /* @Override
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
      public ArrayList<LightResource> getProdInput(){
        if(isUsable() && this.victoryPoints!=-1)
            return this.prodInput;
        return new ArrayList<>();
    }

    */
}
