package cards;

import resources.*;

public class ExtraProd extends LeaderCard{
    private DevelopmentCard requires;
    private static int victoryPoints = 4;
    private Resource input;
    private Resource output = Resource.FAITH;
    private Resource choosenOutput;

    public ExtraProd(int id, boolean en, DevelopmentCard req, Resource input){
        this.cardId=id;
        this.isEnabled=en;
        this.requires=req;
        this.input=input;

    }

    @Override
    public Resource whichResource(){
        return null;
    }
    @Override
    public Resource whichDiscount(){
        return null;
    }
    @Override
    public boolean isEnabled(){
        return this.isEnabled;
    }
}
