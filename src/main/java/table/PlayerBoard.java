package table;

import cards.*;
import resources.Resource;
import strongbox.Strongbox;
import warehouse.WarehouseDepot;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class PlayerBoard {
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

    //--------------------INITIALIZE--------------------

    // public PlayerBoard (){
    //     this.developmentBoard = getDevBoardInstance();
    //     this.marketBoard = getMarketInstance();
    //     this.hasInkwell = false;  initialization with singleton MarketBoard and DevBoard

    public PlayerBoard(DevelopmentBoard developmentBoard, MarketBoard marketBoard){
        this.developmentBoard = developmentBoard;
        this.marketBoard = marketBoard;
        this.hasInkwell = false;
    }

    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }

    //quando farete il controller stronzi?
    public void selectLeader(ArrayList<LeaderCard> quartet, int first, int second){
        //controller sceglie due delle quattre carte
        this.leaderSlots.add(quartet.get(first));
        this.leaderSlots.add(quartet.get(second));
    }

    //--------------------UTILITIES--------------------

    //@AgaCash
    private boolean checkResources(ArrayList<Resource> cost){
        ArrayList<Resource> warehouseResources = new ArrayList<>();
        ArrayList<Resource> strongboxResources = new ArrayList<>();
        ArrayList<Resource> tmp = new ArrayList<>();

        for(Resource r :cost){
            tmp.clear();
            tmp.add(r);
            if(warehouseDepot.isPresent(tmp)){
                warehouseResources.add(r);
            }else if(strongbox.isPresent(tmp)){
                strongboxResources.add(r);
            }else{
                return false;
            }
        }
        //TEORICAMENTE SE LE RISORSE NON CI SONO NON RAGGIUNGERA' MAI QUESTO PUNTO DELLA FUNZIONE

        for(Resource r: warehouseResources){
            warehouseDepot.removeResource(r);
        }
        for(Resource r: strongboxResources){
            strongbox.removeResource(r);
        }

        return true;
    }

    public void activateLeaderCard(){

    }

    //--------------------BUY DEV CARDS--------------------

    //@Controller
    public void buyDevCard(Deck deck, int slotPosition, Discount card) throws OperationNotSupportedException {
        Resource discount = card.whichDiscount();
        ArrayList<Resource> cost = deck.getCost();
        ArrayList<Resource> payment = new ArrayList<>();

        if(discount!=null)
            cost.remove(discount);

        if(checkResources(cost)){
            cardSlots.addCard(slotPosition, deck.popCard());
        }
        else {
            //notificare al controller
        }
    }

    //--------------------MARKET--------------------

    public void buyResources(boolean line, boolean column, int num, WhiteConverter card){
        ArrayList<Resource> bought;
        if(line && !column &&  num>=0 && num<=2) {
            bought = marketBoard.addMarketLine(num, card);
            for (Resource res : bought) {
                if (res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack, 1);
                else
                    warehouseDepot.addResource(res);
            }
        }
        else if(!line && column && num>=0 && num<=3) {
            bought = marketBoard.addMarketColumn(num, card);
            for (Resource res : bought) {
                if (res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack, 1);
                warehouseDepot.addResource(res);
            }
        }
    }


    //--------------------PRODUCTION--------------------

    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws OperationNotSupportedException {
        ArrayList<Resource> prodResources;
        if(checkResources(this.cardSlots.getCard(slot).getCost())){
            if(card.isEnabled() && card.isExtraProd()){
                card.setChosenOutput(chosenOutput);
                if(!checkResources(card.production()))
                    card = null;
            }
            else{
                //notify the controller
            }
            prodResources =  this.cardSlots.getCard(slot).createProduction(card);
            for(Resource res : prodResources)
                if(res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack, 1);
                else
                    strongbox.addResource(res);
        }
        else {
            //notify the controller
        }
    }

    public void defaultProduction(ArrayList<Resource> input, Resource output){//aggiungi carta leader
        for(Resource ptr : input)
            this.warehouseDepot.removeResource(ptr);
        this.strongbox.addResource(output);
    }

    //--------------------FAITH TRACK--------------------

    /** advances the player's pawn position in the Faith Track, calling faithAdvance in FaitTrack
     * @param advance how many position the player gets
     */
    public void faithAdvance (int advance){
        boolean[] check;
        faithBox = faithTrack.faithAdvance(faithBox, faithTrack, advance);
        check = faithBox.getPopeFlag();
        checkPopeFlags(check);
    }

    public FaithBox getFaithBox(){
        return faithBox;
    }

    public void checkPopeFlags(boolean[] flags){
        if (flags[0])
            //serve che controller faccia chiamare al game il metodo, dandogli indicazione
            //di quale player ha chiamato
            ;
        if (flags[1])
            //same
            ;
        if(flags[2])
            //same
            ;
    }
}
