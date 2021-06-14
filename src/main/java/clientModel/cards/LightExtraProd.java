package clientModel.cards;

import clientModel.colour.LightColour;
import clientModel.resources.LightResource;

import java.util.ArrayList;

public class LightExtraProd extends LightLeaderCard {
    private ArrayList<LightDevelopmentCard> requires;
    private static int victoryPoints = 4;
    private LightResource input;
    private LightResource output = LightResource.FAITH;
    private LightResource chosenOutput;

    public LightExtraProd(int id, boolean en, ArrayList<LightDevelopmentCard> req, LightResource input){
        this.id=id;
        this.isEnabled=en;
        this.requires=req;
        this.input=input;
        super.type = LightLeaderCardType.EXTRAPROD;

    }
/*
    @Override
    public LightResource getExtraProdInput() throws UnusableCardException {
        if(!isEnabled())
            throw new UnusableCardException();
        return this.input;
    }

    public void setChosenOutput(LightResource chosenOutput){
        this.chosenOutput = chosenOutput;
    }


    @Override
    public ArrayList<LightResource> production(){
        if(isEnabled()) {
            ArrayList products = new ArrayList();
            products.add(this.output);
            products.add(chosenOutput);
            return products;
        }
        return null;
    }





    @Override
    public ArrayList<LightDevelopmentCard> getRequiredCards(){
        return this.requires;
    }

 */
@   Override
    public boolean isExtraProd(){
        return true;
    }

    @Override
    public String toString(){
        String s = new String();
        LightColour colour;
        if(!isEnabled)
            colour =  LightColour.RED;
        else
            colour = LightColour.GREEN;
        s += colour + "\n______________________";
        s+=      "\n|      EXTRA PROD     |";
        s+=      "\n|Requires:            |";
        for(LightDevelopmentCard card: requires)
            s+=   "\n|\t"+card.toString()+colour;
        s+=  colour+  "\n|Input: \n\t"+input.toColoredString();
        s+= colour.toString()+     "\n|Produce: \n\t"+output.toColoredString()+colour+" + free choice";
        s+=      "\n|Victory Points: "+victoryPoints;
        s+="\n______________________\n"+LightColour.WHITE;
        return s;
    }
}
