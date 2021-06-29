package clientModel.cards;

import clientModel.colour.LightColour;
import clientModel.resources.LightResource;

import java.util.ArrayList;

/**
 * LightModel copy of Model's WhiteConverter
 */
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

@Override
public boolean isWhiteConverter(){
    return true;
}

    @Override
    public String toString(){
        String s = new String();
        LightColour colour;
        if(!isEnabled)
            colour = LightColour.RED;
        else
            colour = LightColour.GREEN;
        s += colour+"\n______________________";
        s+=      "\n|    WHITE CONVERTER  |";
        s+=      "\n|Requires:            |";
        for (LightDevelopmentCard d:requires) {
            s+="\n|\t "+d+colour;
        }
        s+=      colour+"\n|Convert: " +resource.toColoredString();
        s+=    colour+  "\n|Victory Points: "+victoryPoints;
        s+= "\n______________________\n"+LightColour.WHITE;
        return s;
    }

}
