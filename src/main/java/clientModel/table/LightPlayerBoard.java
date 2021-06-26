package clientModel.table;


import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.strongbox.LightStrongbox;
import clientModel.warehouse.LightWarehouseDepot;

import java.util.ArrayList;

public class LightPlayerBoard {
    private LightCardSlots cardSlots = new LightCardSlots();
    private LightWarehouseDepot warehouseDepot = new LightWarehouseDepot();
    private LightStrongbox strongbox = new LightStrongbox();
    private LightFaithTrack faithTrack = new LightFaithTrack();
    private LightFaithBox faithBox = new LightFaithBox();
    private ArrayList<LightLeaderCard> leaderSlot = new ArrayList<>();
    private boolean hasInkwell;
    private int faithPoints = 0;

    public void setInkwell(boolean value){ this.hasInkwell=value; }
    public boolean getInkwell(){ return this.hasInkwell; }

    public void setPoints(int points){ this.faithPoints = points;}
    public int getPoints(){ return  this.faithPoints;}

    public LightStrongbox getStrongbox(){
        return strongbox;
    }
    public void setStrongbox(LightStrongbox strongbox){ this.strongbox = strongbox;}

    public LightCardSlots getCardSlots(){ return cardSlots; }
    public void setCardSlots(LightCardSlots cardSlots){ this.cardSlots = cardSlots;}

    public LightWarehouseDepot getWarehouseDepot(){return warehouseDepot;}
    public void setWarehouse(LightWarehouseDepot warehouseDepot){ this.warehouseDepot = warehouseDepot;}

    public LightFaithBox getFaithBox(){ return faithBox; }

    public LightFaithTrack getFaithTrack(){ return faithTrack;}
    public void setFaithTrack(LightFaithTrack faithTrack){ this.faithTrack = faithTrack;}

    public ArrayList<LightLeaderCard> getLeaderSlot(){
        return leaderSlot;
    }
    public void setLeaderSlot(ArrayList<LightLeaderCard> cards){ this.leaderSlot = cards;
    }
}
