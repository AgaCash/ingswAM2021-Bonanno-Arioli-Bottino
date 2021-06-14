package model.table;

import clientModel.cards.LightLeaderCard;
import clientModel.table.LightPlayerBoard;
import model.cards.CardSlots;
import model.cards.LeaderCard;
import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class PlayerBoard {
    private CardSlots cardSlots = new CardSlots();
    private WarehouseDepot warehouseDepot = new WarehouseDepot();
    private Strongbox strongbox = new Strongbox();
    private FaithTrack faithTrack = new FaithTrack();
    private ArrayList<LeaderCard> leaderSlots = new ArrayList<>();
    private boolean hasInkwell = false;
    private int faithPoints = 0;

    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }
    public boolean hasInkwell(){return this.hasInkwell;}

    public void addLeaderCards(ArrayList<LeaderCard> couple){
        leaderSlots = couple;
    }

    public void removeLeaderCard(int cardId){
        LeaderCard toRemove = null;
        for(LeaderCard card: leaderSlots)
            if(card.getId()==cardId)
                toRemove = card;
        if(toRemove == null)
            throw  new InputMismatchException("Can't find this leader card!");
        this.leaderSlots.remove(toRemove);
    }


    public Strongbox getStrongbox(){
        return strongbox;
    }

    public CardSlots getCardSlots(){ return cardSlots; }

    public WarehouseDepot getWarehouseDepot(){return warehouseDepot;}

    public FaithBox getFaithBox(){ return this.faithTrack.getFaithBox(); }

    public FaithTrack getFaithTrack(){ return faithTrack;}

    public ArrayList<LeaderCard> getLeaders (){
        return leaderSlots;
    }

    public void addPoint(int point){ this.faithPoints +=point;}
    public int getPoints(){ return this.faithPoints; }

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

}
