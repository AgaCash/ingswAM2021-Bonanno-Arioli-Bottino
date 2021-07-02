package model.table;

import clientModel.cards.LightLeaderCard;
import clientModel.table.LightPlayerBoard;
import model.cards.CardSlots;
import model.cards.LeaderCard;
import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**Class representing the Player Board in the Game. It contains all the Player tools during the Game (WarehouseDepot, Strongbox, FaithTrack, CardSlots and LeaderSlot)
 * and all the info regarding them
 *
 */
public class PlayerBoard {
    private CardSlots cardSlots = new CardSlots();
    private WarehouseDepot warehouseDepot = new WarehouseDepot();
    private Strongbox strongbox = new Strongbox();
    private FaithTrack faithTrack = new FaithTrack();
    private ArrayList<LeaderCard> leaderSlots = new ArrayList<>();
    private boolean hasInkwell = false;
    private int faithPoints = 0;
    private boolean usedInThisTurn = false;

    /**Sets the Player's inkwell value (if true it's player turn)
     * @param value a boolean
     */
    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }

    /**Adds the LeaderCard couple chosen by the Client (LightPlayer) to the PlayerBoard
     * @param couple a 2-length LeaderCard ArrayList
     */
    public void addLeaderCards(ArrayList<LeaderCard> couple){
        leaderSlots = couple;
    }

    /**Removes a LeaderCard from the Player LeaderSlots
     * @param cardId an int representing the LeaderCard ID
     */
    public void removeLeaderCard(int cardId){
        LeaderCard toRemove = null;
        for(LeaderCard card: leaderSlots)
            if(card.getId()==cardId)
                toRemove = card;
        if(toRemove == null)
            throw  new InputMismatchException("Can't find this leader card!");
        this.leaderSlots.remove(toRemove);
    }


    /**Returns Player's Strongbox
     * @return a Strongbox instance
     */
    public Strongbox getStrongbox(){
        return strongbox;
    }

    /**Returns Player's CardSlots
     * @return a CardSlots instance
     */
    public CardSlots getCardSlots(){ return cardSlots; }

    /**Returns Player's Warehouse
     * @return a WarehouseDepot instance
     */
    public WarehouseDepot getWarehouseDepot(){return warehouseDepot;}

    /**Returns the current FaithBox where Player's is positioned on the FaithTrack
     * @return a FaithBox instance
     */
    public FaithBox getFaithBox(){ return this.faithTrack.getFaithBox(); }

    /**Returns Player's FaithTrack
     * @return a FaithTrack instance
     */
    public FaithTrack getFaithTrack(){ return faithTrack;}

    /**Returns LeaderCard slot
     * @return a LeaderCard ArrayList
     */
    public ArrayList<LeaderCard> getLeaders (){
        return leaderSlots;
    }

    /**Returns Player's current FaithPoints
     * @return an int
     */
    public int getPoints(){ return this.faithPoints; }

    /**Convert current PlayerBoard state in a new LightPlayerBoard instance for LightModel
     * @return a LightPlayerBoard instance
     */
    public LightPlayerBoard convert(){
        LightPlayerBoard newPlayerBoard = new LightPlayerBoard();

        ArrayList<LightLeaderCard> lightLeaderCards = new ArrayList<>();
        leaderSlots.forEach(element -> lightLeaderCards.add(element.convert()));

        newPlayerBoard.setInkwell(this.hasInkwell);
        newPlayerBoard.setStrongbox(this.strongbox.convert());
        newPlayerBoard.setCardSlots(this.cardSlots.convert());
        newPlayerBoard.setWarehouse(this.warehouseDepot.convert());
        newPlayerBoard.setFaithTrack(this.faithTrack.convert());
        newPlayerBoard.setLeaderSlot(lightLeaderCards);

        return newPlayerBoard;
    }

    /**Sets the Default Production flag to true (Player did Default Production: so can't do another one)
     *
     */
    public void didProduction(){
        this.usedInThisTurn = true;
    }

    /**Method called by Game, turn back usable after turn is ended
     *
     */
    public void backUsable(){
        this.usedInThisTurn = false;
    }

    /**A boolean flag that permit (or not) the Player to do a Default Production in the current turn
     * @return true if Player can do a Default Production
     */
    public boolean canDoDefProduction(){
        return !usedInThisTurn;
    }

}
