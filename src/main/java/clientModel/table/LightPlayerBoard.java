package clientModel.table;


import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.strongbox.LightStrongbox;
import clientModel.warehouse.LightWarehouseDepot;

import java.util.ArrayList;

/**
 * LightModel's copy of Player's PlayerBoard in Model.
 */
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

    /**Returns current LightStrongbox
     * @return a LightStrongbox instance
     */
    public LightStrongbox getStrongbox(){
        return strongbox;
    }

    /**Updates the LightStrongbox instance with the current Model's Strongbox status
     * @param strongbox a LightStrongbox status
     */
    public void setStrongbox(LightStrongbox strongbox){ this.strongbox = strongbox;}

    /**Returns current LightCardSlots
     * @return a LightCardSlots instance
     */
    public LightCardSlots getCardSlots(){ return cardSlots; }

    /**Updates the LightCardSlots instance with the current Model's CardSlots status
     * @param cardSlots a LightCardSlots instance
     */
    public void setCardSlots(LightCardSlots cardSlots){ this.cardSlots = cardSlots;}

    /**Returns current LightWarehouseDepot
     * @return a LightWarehouseDepot instance
     */
    public LightWarehouseDepot getWarehouseDepot(){return warehouseDepot;}

    /**Updates the LightWarehouseDepot instance with the current Model's WarehouseDepot status
     * @param warehouseDepot a LightWarehouseDepot instance
     */
    public void setWarehouse(LightWarehouseDepot warehouseDepot){ this.warehouseDepot = warehouseDepot;}

    /**Returns current LightFaithTrack
     * @return a LightFaithTrack instance
     */
    public LightFaithTrack getFaithTrack(){ return faithTrack;}

    /**Updates the LightWarehouseDepot instance with the current Model's FaithTrack status
     * @param faithTrack a LightFaithTrack instance
     */
    public void setFaithTrack(LightFaithTrack faithTrack){ this.faithTrack = faithTrack;}

    /**Returns the current LightLeaderCardSlot
     * @return a LightLeaderCard instance
     */
    public ArrayList<LightLeaderCard> getLeaderSlot(){
        return leaderSlot;
    }

    /**Updates the LightLeaderCardSlots with the current Model's LeaderCardSlot status
     * @param cards a LightLeaderCard ArrayList
     */
    public void setLeaderSlot(ArrayList<LightLeaderCard> cards){
        this.leaderSlot = cards;
    }
}
