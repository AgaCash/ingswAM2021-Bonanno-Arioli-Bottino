package cards;

import resources.Resource;

import java.util.ArrayList;

public class Discount extends LeaderCard{
    public ArrayList<DevelopmentCard> requirements;
    public Resource discount;
    public static int victoryPoints = 2;

    @Override
    public Resource whichResource() {
        return null;
    }

    public Discount(int id, boolean en, ArrayList<DevelopmentCard> req, Resource dis){
        this.cardId = id;
        this.requirements = req;
        this.discount = dis;
        this.isEnabled = en;
    }


}
