package model.table;

import model.player.Player;
import model.cards.CardSlots;
import model.cards.LeaderCard;
import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;

import java.util.ArrayList;

public class PlayerBoard {
    private Player player;
    private CardSlots cardSlots = new CardSlots();
    private WarehouseDepot warehouseDepot = new WarehouseDepot();
    private Strongbox strongbox = new Strongbox();
    private FaithTrack faithTrack = new FaithTrack();
    private FaithBox faithBox = new FaithBox();
    private ArrayList<LeaderCard> leaderSlots;
    private boolean hasInkwell;
    private int faithPoints = 0;

    public PlayerBoard(Player player){
        this.player = player;
        this.hasInkwell = false;
    }

    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }

    public void addLeaderCards(ArrayList<LeaderCard> couple){
        leaderSlots = couple;
    }

    public Player getPlayer(){
        return player;
    }

    public Strongbox getStrongbox(){
        return strongbox;
    }

    public CardSlots getCardSlots(){ return cardSlots; }

    public WarehouseDepot getWarehouseDepot(){return warehouseDepot;}

    public FaithBox getFaithBox(){ return faithBox; }

    public FaithTrack getFaithTrack(){ return faithTrack;}

}
