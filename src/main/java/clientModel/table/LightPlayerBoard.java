package clientModel.table;


import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import clientModel.strongbox.LightStrongbox;
import clientModel.warehouse.LightWarehouseDepot;

import java.util.ArrayList;

public class LightPlayerBoard {
    private transient LightPlayer player;
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

    public LightPlayer getPlayer(){
        return player;
    }
    public void setPlayer(LightPlayer player){ this.player = player;}

    public LightStrongbox getStrongbox(){
        return strongbox;
    }
    public void setStrongbox(LightStrongbox strongbox){ this.strongbox = strongbox;}

    public LightCardSlots getCardSlots(){ return cardSlots; }
    public void setCardSlots(ArrayList<LightDevelopmentCard> cardSlots){ this.cardSlots.setCards(cardSlots);}

    public LightWarehouseDepot getWarehouseDepot(){return warehouseDepot;}
    public void setWarehouse(LightWarehouseDepot warehouseDepot){ this.warehouseDepot = warehouseDepot;}

    public LightFaithBox getFaithBox(){ return faithBox; }

    public LightFaithTrack getFaithTrack(){ return faithTrack;}
    public void setFaithTrack(LightFaithTrack faithTrack){ this.faithTrack = faithTrack;}

    public ArrayList<LightLeaderCard> getLeaderSlot(){
        return leaderSlot;
    }
    public void setLeaderSlot(ArrayList<LightLeaderCard> cards){ this.leaderSlot = cards;
        System.out.println(cards);
    }
}
