package clientModel.cards;

import clientModel.resources.LightResource;
import exceptions.UnusableCardException;
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
    }

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
}
