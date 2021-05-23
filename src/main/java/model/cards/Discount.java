package model.cards;

import exceptions.UnusableCardException;
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
        super.type = LeaderCardType.DISCOUNT;
    }

    @Override
    public Resource whichDiscount() throws UnusableCardException {
        if(!isEnabled())
            throw new UnusableCardException();
        return this.discount;
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
