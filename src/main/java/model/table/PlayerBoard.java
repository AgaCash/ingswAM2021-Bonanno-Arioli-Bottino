package model.table;

import model.Player;
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
    private DevelopmentBoard developmentBoard;
    private MarketBoard marketBoard;
    private boolean hasInkwell;
    private int faithPoints = 0;

    public PlayerBoard(Player player, DevelopmentBoard developmentBoard, MarketBoard marketBoard){
        this.player = player;
        this.developmentBoard = developmentBoard;
        this.marketBoard = marketBoard;
        this.hasInkwell = false;
    }

    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }

    public void addLeaderCards(LeaderCard card1, LeaderCard card2){
        leaderSlots.add(card1);
        leaderSlots.add(card2);
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
