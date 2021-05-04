package model.table;

import model.Player;
import model.cards.*;
import model.resources.Resource;
import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;

import javax.naming.OperationNotSupportedException;
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

    //--------------------INITIALIZE--------------------
    //ONLY 4 TESTING
    public PlayerBoard(CardSlots cardSlots, WarehouseDepot warehouseDepot,
                       Strongbox strongbox, FaithTrack faithTrack,
                       DevelopmentBoard developmentBoard, MarketBoard marketBoard,
                       ArrayList<LeaderCard> couple){
        this.cardSlots = cardSlots;
        this.warehouseDepot = warehouseDepot;
        this.strongbox = strongbox;
        this.faithTrack = faithTrack;
        this.developmentBoard = developmentBoard;
        this.marketBoard = marketBoard;
        this.hasInkwell = false;
        this.leaderSlots = couple;
    }

    public PlayerBoard(Player player, DevelopmentBoard developmentBoard, MarketBoard marketBoard){
        this.player = player;
        this.developmentBoard = developmentBoard;
        this.marketBoard = marketBoard;
        this.hasInkwell = false;
    }

    public void setInkwell(boolean value){
        this.hasInkwell=value;
    }
    /*selectLeader: permits to choose the 2 leader cards at starting
    @quartet, @first and @second are set by @Controller
     */
    public void selectLeader(ArrayList<LeaderCard> quartet, int first, int second){
        //DA RIVEDERE STO METODO QUA EH
        this.leaderSlots.add(quartet.get(first));
        this.leaderSlots.add(quartet.get(second));
    }

    //--------------------UTILITIES--------------------
    /*checkDevCards: checks if @requirements is present in this.cardSlots
    */
    private boolean checkDevCards(ArrayList<DevelopmentCard> requirements){
        for(DevelopmentCard card : requirements){
            if(!cardSlots.isPresent(card))
                return false;
        }
        return true;
    }
    /*checkResources: checks if @cost is present in this.warehouseDepot,
    this.strongbox, or both
    @remove flag sets if remove @cost after the check from player's resources or not
     */
    private boolean checkResources(ArrayList<Resource> cost, boolean remove){
        /*ArrayList<Resource> warehouseResources = new ArrayList<>();
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
        if(remove){
            for(Resource r: warehouseResources){
                warehouseDepot.removeResource(r);
            }
            for(Resource r: strongboxResources){
                strongbox.removeResource(r);
            }
        }

        return true;*/
        ArrayList<Resource> clonedWarehouse = warehouseDepot.status();
        ArrayList<Resource> clonedStrongbox = strongbox.status();
        for(Resource r :cost){
            ArrayList<Resource> tmp = new ArrayList<>();
            tmp.add(r);
            if(clonedWarehouse.remove(r)){
                if(remove)
                    this.warehouseDepot.removeResource(r);
            }else if(clonedStrongbox.remove(r)){
                if(remove)
                    this.strongbox.removeResource(r);
            }else{
                return false;
            }
        }
        return true;
    }

    /* check if @card is usable
     */
    private boolean cardIsUsable(LeaderCard card){
        if(card!=null && card.isEnabled())
            return true;
        return false;
    }

    //--------------------BUY DEV CARDS--------------------

    //@Controller
    public void buyDevCard(Deck deck, int slotPosition, Discount card) throws OperationNotSupportedException {
        Resource discount = null;
        if(cardIsUsable(card))
            discount = card.whichDiscount();
        ArrayList<Resource> cost = deck.getCost();
        ArrayList<Resource> payment = new ArrayList<>();

        if(discount!=null)
            cost.remove(discount);

        if(checkResources(cost, true)){
            cardSlots.addCard(slotPosition, deck.popCard());
        }
        else{
            //notificare al controller
            //"ACQUISTO FALLITO"
        }
    }

    //--------------------MARKET--------------------

    public void buyResources(boolean line, boolean column, int num, WhiteConverter card){
        ArrayList<Resource> bought;
        if(line && !column &&  num>=0 && num<=2) {
            bought = marketBoard.addMarketLine(num, card);
            for (Resource res : bought) {
                if (res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack);
                else
                    warehouseDepot.addResource(res);
            }
        }
        else if(!line && column && num>=0 && num<=3) {
            bought = marketBoard.addMarketColumn(num, card);
            for (Resource res : bought) {
                if (res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack);
                else
                    warehouseDepot.addResource(res);
            }
        }
    }

    //--------------------PRODUCTION--------------------
    /*devCadProduction: activates production of the last card popped from @slot
    @chosenOutput is set by @Controller and it's the optional resource produced by @card
     */
    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws OperationNotSupportedException {
        ArrayList<Resource> prodResources;
        if(checkResources(this.cardSlots.getCard(slot).getProdInput(), true)){
            if(checkExtraProd(card)==null){
                // notifica il controller
                // "CARTA NON USABILE"
            }
            else{
                card.setChosenOutput(chosenOutput);
            }
            prodResources =  this.cardSlots.getCard(slot).createProduction(card);
            for(Resource res : prodResources)
                if(res == Resource.FAITH)
                    faithTrack.faithAdvance(faithBox, faithTrack);
                else
                    strongbox.addResource(res);
        }
        else {
            //notify the controller
            //"RISORSE NON SUFFICIENTI"
        }
    }
    /*defaultProduction: activates production from the developmentBoard
    @input and @output are set by @Controller
     */
    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput){
        if(checkExtraProd(card)!=null)
            card.setChosenOutput(chosenOutput);
        else{
            // notifica il controller
            //"CARTA NON USABILE
        }
        if(checkResources(input, true )){
            this.strongbox.addResource(output);
            if(card!=null){
                for (Resource res : card.production())
                    if (res == Resource.FAITH)
                        faithTrack.faithAdvance(faithBox, faithTrack);
                    else
                        strongbox.addResource(res);
            }
        }
        else{
            //notifica il controller
            //"RISORSE NON SUFFICIENTI"
        }
    }

    private ExtraProd checkExtraProd(LeaderCard card){
        if(cardIsUsable(card) && card.isExtraProd()) {
            ArrayList<Resource> extraProdInput = new ArrayList<>();
            extraProdInput.add(card.getExtraProdInput());
            if (checkResources(extraProdInput, true))
                return (ExtraProd) card;
        }
        else{
            /*notificare il controller */
        }
        return null;
    }

    //--------------------FAITH TRACK--------------------

    /** advances the player's pawn position in the Faith Track, calling faithAdvance in FaitTrack
     * @param advance how many position the player gets
     */
    public void faithAdvance (int advance){
        boolean[] check;
        for(int i=0; i<advance ;i++) {
            faithBox = faithTrack.faithAdvance(faithBox, faithTrack);
            if (faithBox.getPosition() == 24)
                System.out.println("faith track completed");
                //endgame
                ;
            check = faithBox.getPopeFlag();
            checkPopeFlags(check);
        }
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
    //--------------------LEADER CARDS--------------------

    public void activateLeaderCard(LeaderCard card){
        if(card.isExtraDepot()){
            ArrayList<Resource> requirements = card.getRequiredResources();
            if(checkResources(requirements, false))
                card.activate();
            else{ /* notifica il controller */ }
        }
        else{
            ArrayList<DevelopmentCard> requirements = card.getRequiredCards();
            if(checkDevCards(requirements))
                card.activate();
            else{ /* notifica il controller */ }

        }
    }

}
