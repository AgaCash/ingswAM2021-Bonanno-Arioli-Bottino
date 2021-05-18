package clientModel.cards;

import clientModel.resources.LightResource;
import exceptions.UnusableCardException;

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
    }

    @Override
    public LightResource whichDiscount() throws UnusableCardException {
        if(!isEnabled())
            throw new UnusableCardException();
        return this.discount;
    }

    @Override
    public boolean isDiscount(){
        return true;
    }

    @Override
    public ArrayList<LightDevelopmentCard> getRequiredCards(){
        return this.requires;
    }
}
