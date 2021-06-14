package clientModel.cards;

import clientModel.colour.LightColour;
import clientModel.resources.LightResource;

import java.util.ArrayList;

public class LightDiscount extends LightLeaderCard {
    public ArrayList<LightDevelopmentCard> requires;
    public LightResource discount;
    public static int victoryPoints = 2;

    public LightDiscount(int id, boolean en, ArrayList<LightDevelopmentCard> req, LightResource dis){
        this.id = id;
        this.requires = req;
        this.discount = dis;
        this.isEnabled = en;
        super.type = LightLeaderCardType.DISCOUNT;
    }
/*
    @Override
    public LightResource whichDiscount() throws UnusableCardException {
        if(!isEnabled())
            throw new UnusableCardException();
        return this.discount;
    }*/

    @Override
    public boolean isDiscount(){
        return true;
    }
/*
    @Override
    public ArrayList<LightDevelopmentCard> getRequiredCards(){
        return this.requires;
    }
 */
    @Override
    public String toString(){
        String s = new String();
        LightColour colour;
        if(!isEnabled)
            colour = LightColour.RED;
        else
            colour = LightColour.GREEN;
        s+=colour.toString()+"\n______________________";
        s+=     "\n|       DISCOUNT      |";
        s+=     "\n|Requires:            |";
        for (LightDevelopmentCard d:requires) {
            s+="\n|\t"+d.toString()+colour;
        }
        s+= colour.toString()+   "\n|Discount: \n\t" +discount.toColoredString();
        s+=  colour.toString()+   "\n|Victory Points: "+victoryPoints;
        s+="\n______________________\n"+LightColour.WHITE;
        return s;
    }

}
