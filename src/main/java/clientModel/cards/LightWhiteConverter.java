package clientModel.cards;

import clientModel.resources.LightResource;

import java.util.ArrayList;

public class LightWhiteConverter extends LightLeaderCard {
    private LightResource resource;
    private ArrayList<LightDevelopmentCard> requires;
    private static int victoryPoints = 5;

    public LightWhiteConverter(int id, boolean en, ArrayList<LightDevelopmentCard> req, LightResource res){
        this.id = id;
        this.resource = res;
        this.isEnabled = en;
        this.requires = req;
        super.type = LightLeaderCardType.WHITECONVERTER;
    }
/*
    @Override
    public LightResource whichResource() throws UnusableCardException {
        if(isEnabled())
            throw new UnusableCardException();
        return this.resource;
    }

    @Override
    public boolean isWhiteConverter(){
        return true;
    }

    @Override
    public ArrayList<LightDevelopmentCard> getRequiredCards(){
        return this.requires;
    }

 */

    @Override
    public String toString(){
        String s =  "______________________";
        s+=      "\n|    WHITE CONVERTER  |";
        s+=      "\n|Requires:            |";
        for (LightDevelopmentCard d:requires) {
            s+="\n|\t "+d;
        }
        s+=      "\n|Convert: " +resource.toColoredString();
        s+=      "\n|Victory Points: "+victoryPoints;
        s+= "\n______________________\n";
        return s;
    }

}
