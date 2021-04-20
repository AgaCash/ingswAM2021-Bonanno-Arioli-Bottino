package cards;

import resources.Resource;

import java.util.ArrayList;

public class Discount extends LeaderCard{
    public ArrayList<DevelopmentCard> requires;
    public Resource discount;
    public static int victoryPoints = 2;

    public Discount(int id, boolean en, ArrayList<DevelopmentCard> req, Resource dis){
        this.cardId = id;
        this.requires = req;
        this.discount = dis;
        this.isEnabled = en;
    }

    @Override
    public Resource whichResource() {
        return null;
    }
    @Override
    public boolean isEnabled(){
        return this.isEnabled;
    }
    @Override
    public Resource whichDiscount(){
        return this.discount;
    }


}
