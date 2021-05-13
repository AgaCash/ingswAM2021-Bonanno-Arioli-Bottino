package model;

import exceptions.*;
import model.cards.*;
import model.resources.Resource;
import model.singleplayer.Lorenzo;
import model.table.*;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<PlayerBoard> players = new ArrayList<>();
    private Table table;
    private PlayerBoard currentPlayer;
    private Lorenzo cpu;
    private boolean singlePlayer;
    private ArrayList<Resource> bufferStrongbox = new ArrayList<>();
    private ArrayList<Resource> threwResources = new ArrayList<>();
    //Lorenzo cpu = new Lorenzo();  will be ok when DevBoard will adopt singleton pattern

    //COSTRUTTORE
    public Game(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
    }
    //========================START GAME

    //@CONTROLLER
    public void initializeGame(){
        table = Table.getTableInstance();
        Collections.shuffle(players);
        if(singlePlayer)
            cpu = new Lorenzo(table.getDevBoard());
    }

    //========DA RIVEDERE DA QUA:
    public void addPlayer(Player newPlayer){
        if(players.size()<4){
            players.add(new PlayerBoard(newPlayer, table.getDevBoard(), table.getMarketBoard()));
        }
    }
    //send leader cards
    public ArrayList<LeaderCard> sendQuartet(){
        //da rifare
        return null;
    }
    //receive leader cards
    public void assignLeaderCards(String username, LeaderCard first, LeaderCard second){
        PlayerBoard user = getPlayerBoard(username);
        if(user!=null)
            user.addLeaderCards(first, second);
    }
    //starting resources
    public void addStartingResources(){
        //bla bla bla
    }
    //A QUA.
    //=================

    public ArrayList<String> getPlayerTurns(){
        ArrayList<String> turns = new ArrayList<>();
        for(PlayerBoard p : players)
            turns.add(p.getPlayer().getNickname());
        return turns;
    }

    public void updateTurn(){
        if(!bufferStrongbox.isEmpty())
            updateStrongbox();
        changeTurn();
    }
    //===========UTILITIES
    private boolean checkDevCards(ArrayList<DevelopmentCard> requirements){
        for(DevelopmentCard card : requirements){
            if(!currentPlayer.getCardSlots().isPresent(card))
                return false;
        }
        return true;
    }
    /*checkResources: checks if @cost is present in this.warehouseDepot,
    this.strongbox, or both
    @remove flag sets if remove @cost after the check from player's resources or not
     */
    private boolean checkResources(ArrayList<Resource> cost, boolean remove){

        ArrayList<Resource> clonedWarehouse = currentPlayer.getWarehouseDepot().status();
        ArrayList<Resource> clonedStrongbox = currentPlayer.getStrongbox().status();
        for(Resource r :cost){
            ArrayList<Resource> tmp = new ArrayList<>();
            tmp.add(r);
            if(clonedWarehouse.remove(r)){
                if(remove)
                    currentPlayer.getWarehouseDepot().removeResource(r);
            }else if(clonedStrongbox.remove(r)){
                if(remove)
                    currentPlayer.getStrongbox().removeResource(r);
            }else{
                return false;
            }
        }
        return true;
    }
    private boolean cardIsUsable(LeaderCard card){
        if(card!=null && card.isEnabled())
            return true;
        return false;
    }
    //============GIOCO

    //--------------------BUY DEV CARDS--------------------
    public void buyDevCard(Deck deck, int slotPosition, Discount card) throws FullCardSlotException, NonCorrectLevelCardException, InsufficientResourcesException {
        Resource discount = null;
        if(cardIsUsable(card))
            discount = card.whichDiscount();
        ArrayList<Resource> cost = deck.getCost();

        if(discount!=null)
            cost.remove(discount);

        if(checkResources(cost, true)){
            currentPlayer.getCardSlots().addCard(slotPosition, deck.popCard());
        }
        else{
            throw new InsufficientResourcesException("Can't buy this card: insufficient resources!");
        }
    }

    //--------------------MARKET--------------------
    public void buyResources(boolean line, int num, WhiteConverter card){
        ArrayList<Resource> bought = new ArrayList<>();
        if(line &&  num>=0 && num<=2) {
            bought = table.getMarketBoard().addMarketLine(num, card);
        }
        else if(!line && num>=0 && num<=3) {
            bought = table.getMarketBoard().addMarketColumn(num, card);
        }

        for (Resource res : bought) {
            if (res == Resource.FAITH) {
                currentPlayer.getFaithTrack().faithAdvance(currentPlayer.getFaithBox(), currentPlayer.getFaithTrack());
            }
            else {
                try{
                    currentPlayer.getWarehouseDepot().addResource(res);
                    } catch(FullWarehouseException e){
                        threwResources.add(res);
                    }

                }
            }

    }

    public ArrayList<Resource> getThrewResources(){
        ArrayList<Resource> cloned = (ArrayList<Resource>) threwResources.clone();
        for(Resource res: cloned)
            for(PlayerBoard player: players)
                if(player!=currentPlayer)
                    player.getFaithTrack().faithAdvance(player.getFaithBox(), player.getFaithTrack());
        threwResources.clear();
        return cloned;
    }


    //--------------------PRODUCTION--------------------
    /*devCadProduction: activates production of the last card popped from @slot
    @chosenOutput is set by @Controller and it's the optional resource produced by @card
     */
    public ArrayList<Resource> devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws
            InsufficientResourcesException
    {
        ArrayList<Resource> prodResources;
        if(checkResources(currentPlayer.getCardSlots().getCard(slot).getProdInput(), true)){
            if(checkExtraProd(card)==null){}
            else{
                card.setChosenOutput(chosenOutput);
            }
            prodResources = currentPlayer.getCardSlots().getCard(slot).createProduction(card);
            for (Resource res : prodResources)
                if (res == Resource.FAITH) {
                    currentPlayer.getFaithTrack().faithAdvance(currentPlayer.getFaithBox(), currentPlayer.getFaithTrack());
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
                        currentPlayer.getFaithTrack().faithAdvance(currentPlayer.getFaithBox(), currentPlayer.getFaithTrack());
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
            FaithBox faithBox = currentPlayer.getFaithBox();
            faithBox  = currentPlayer.getFaithTrack().faithAdvance(currentPlayer.getFaithBox(), currentPlayer.getFaithTrack());
            if (faithBox.getPosition() == 24)
                System.out.println("faith track completed");
            //endgame
            ;
            check = faithBox.getPopeFlag();
            checkPopeFlags(check);
        }
    }

    public void checkPopeFlags(boolean[] flags){
        if (flags[0]){
            for(PlayerBoard p : players){
                if(p.getFaithBox().getPosition()>=5)
                    p.getPlayer().addPoints(2);
                p.getFaithBox().setPopeFlag(false,false,false);
            }
        }
        if (flags[1]){
            for(PlayerBoard p : players){
                if(p.getFaithBox().getPosition()>=12)
                    p.getPlayer().addPoints(3);
                p.getFaithBox().setPopeFlag(false,false,false);
            }
        }
        if(flags[2]){
            for(PlayerBoard p : players){
                if(p.getFaithBox().getPosition()>=19)
                    p.getPlayer().addPoints(4);
                p.getFaithBox().setPopeFlag(false,false,false);
            }
        }
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
    //============ENDGAME=======
    //contare i punti vittoria dei giocatori su faithTrack, leaderCards, devCards ecc...
    //fare la classifica dei punti vittoria
    //============utilities

    private void updateStrongbox(){
        this.bufferStrongbox.forEach(element -> currentPlayer.getStrongbox().addResource(element));
    }

    private PlayerBoard getPlayerBoard(String username){
        for(PlayerBoard p : players){
            if(username.equals(p.getPlayer().getNickname()))
                return p;
        }
        return null;
    }

    //change turn
    private void changeTurn(){
        if(singlePlayer) {
            cpu.pick();
        }
        else {
            int turn = players.indexOf(currentPlayer);
            if (turn == 3)
                turn = 0;
            else
                turn += 1;
            currentPlayer = players.get(turn);
        }
    }

    public PlayerBoard getCurrentPlayer(){
        return currentPlayer;
    }

    public DevelopmentBoard getDevBoard(){
        return table.getDevBoard();
    }

    public MarketBoard getMarketBoard(){
        return table.getMarketBoard();
    }




}
