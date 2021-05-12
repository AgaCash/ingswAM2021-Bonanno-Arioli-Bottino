package model.table;

import exceptions.*;
import model.Player;
import model.cards.*;
import model.resources.Resource;
import model.strongbox.Strongbox;
import model.warehouse.WarehouseDepot;

import java.util.ArrayList;

public class PlayerBoard {
    private int playerPos;
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
    public void buyDevCard(Deck deck, int slotPosition, Discount card) throws FullCardSlotException, NonCorrectLevelCardException, InsufficientResourcesException {
        Resource discount = null;
        if(cardIsUsable(card))
            discount = card.whichDiscount();
        ArrayList<Resource> cost = deck.getCost();

        if(discount!=null)
            cost.remove(discount);

        if(checkResources(cost, true)){
            cardSlots.addCard(slotPosition, deck.popCard());
        }
        else{
            throw new InsufficientResourcesException("Can't buy this card: insufficient resources!");
        }
    }

    //--------------------MARKET--------------------

    public void buyResources(boolean line, boolean column, int num, WhiteConverter card){
        ArrayList<Resource> bought;
        if(line && !column &&  num>=0 && num<=2) {
            bought = marketBoard.addMarketLine(num, card);
            for (Resource res : bought) {
                if (res == Resource.FAITH) {
                    faithTrack.faithAdvance(faithBox, faithTrack);
                }
                else {
                    try {
                        warehouseDepot.addResource(res);
                    } catch(FullWarehouseException e){
                        //
                    }
                }

            }
        }
        else if(!line && column && num>=0 && num<=3) {
            bought = marketBoard.addMarketColumn(num, card);
            for (Resource res : bought) {
                if (res == Resource.FAITH) {
                    faithTrack.faithAdvance(faithBox, faithTrack);
                }
                else {
                    try{
                        warehouseDepot.addResource(res);
                    } catch(FullWarehouseException e){
                        //
                    }

                }
            }
        }
    }

    //--------------------PRODUCTION--------------------
    /*devCadProduction: activates production of the last card popped from @slot
    @chosenOutput is set by @Controller and it's the optional resource produced by @card
     */
    public ArrayList<Resource> devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws
                                                                                            InsufficientResourcesException
                                                                                            {
        ArrayList<Resource> prodResources;
        if(checkResources(this.cardSlots.getCard(slot).getProdInput(), true)){
            if(checkExtraProd(card)==null){}
            else{
                card.setChosenOutput(chosenOutput);
            }
            prodResources =  this.cardSlots.getCard(slot).createProduction(card);
                for (Resource res : prodResources)
                    if (res == Resource.FAITH) {
                        faithTrack.faithAdvance(faithBox, faithTrack);
                        prodResources.remove(Resource.FAITH);
                return prodResources;
            }
        }
        else {
            throw new InsufficientResourcesException("Can`t do this production: insufficient resources!");
        }
        return null;
    }
    /*defaultProduction: activates production from the developmentBoard
    @input and @output are set by @Controller
     */
    public ArrayList<Resource> defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput) throws
                                                                                                                                        InsufficientResourcesException {
        ArrayList<Resource> prodResources = new ArrayList<>();
        if(checkExtraProd(card)!=null)
            card.setChosenOutput(chosenOutput);
        if(checkResources(input, true )){
            prodResources.add(output);
            //leaderCard
            if(card!=null){
                for (Resource res : card.production())
                    if (res == Resource.FAITH) {
                        faithTrack.faithAdvance(faithBox, faithTrack);
                        prodResources.remove(Resource.FAITH);
                    }
            }
            return prodResources;
        }
        else{
            throw new InsufficientResourcesException("Can't do this production: insufficient resources!");

        }
    }

    private ExtraProd checkExtraProd(LeaderCard card) {
        if(cardIsUsable(card) && card.isExtraProd()) {
            ArrayList<Resource> extraProdInput = new ArrayList<>();
            extraProdInput.add(card.getExtraProdInput());
            if (checkResources(extraProdInput, true))
                return (ExtraProd) card;
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
        //dai figa cisco competitivo
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

    public void activateLeaderCard(LeaderCard card) throws InsufficientResourcesException,
                                                            InsufficientRequirementsException {
        if(card.isExtraDepot()){
            ArrayList<Resource> requirements = card.getRequiredResources();
            if(checkResources(requirements, false))
                card.activate();
            else{
                throw new InsufficientResourcesException("Can't activate this leader card: insufficient resources!");
            }
        }
        else{
            ArrayList<DevelopmentCard> requirements = card.getRequiredCards();
            if(checkDevCards(requirements))
                card.activate();
            else{
                throw new InsufficientRequirementsException("Can't activate this leader card: insufficient resources!");
            }

        }
    }

    public Player getPlayer(){
        return player;
    }

    public Strongbox getStrongbox(){
        return strongbox;
    }





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

}
