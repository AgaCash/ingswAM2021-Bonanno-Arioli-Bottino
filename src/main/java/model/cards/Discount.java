package model.cards;

import model.resources.Resource;

import java.util.ArrayList;

public class Discount extends LeaderCard{
    public ArrayList<DevelopmentCard> requires;
    public Resource discount;
    public static int victoryPoints = 2;

    public Discount(int id, boolean en, ArrayList<DevelopmentCard> req, Resource dis){
        this.id = id;
        this.requires = req;
        this.discount = dis;
        this.isEnabled = en;
    }

    @Override
    public Resource whichDiscount(){
        if(isEnabled())
            return this.discount;
        return null;
    }

    @Override
    public boolean isDiscount(){
        return true;
    }

    @Override
    public ArrayList<DevelopmentCard> getRequiredCards(){
        return this.requires;
    }
    /**
     * for tests
     * @return
     */
    @Override
    public String toString(){
        String s = "\nDISCOUNT";
        s+= "\nID: "+id;
        s+= "\nRequires: ";
        for (DevelopmentCard d:requires) {
         s+="\n\t "+d;
        }
        s+= "\nDiscount: " +discount;
        s+= "\nVictory Points: "+victoryPoints;
        s+= "\nIs Enabled: "+ isEnabled;
        return s;
    }

}
