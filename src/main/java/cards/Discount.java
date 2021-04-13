package cards;

import resources.Resource;

import java.util.ArrayList;

public class Discount extends LeaderCard{
    private ArrayList<DevelopmentCard> requirements;
    private Resource discount;
    private static int victoryPoints = 2;

    @Override
    public Resource whichResource() {
        return null;
    }

    public Discount(boolean en, ArrayList<DevelopmentCard> req, Resource dis){
        this.requirements = req;
        this.discount = dis;
        this.isEnabled = en;
    }
}
