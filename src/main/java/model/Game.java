package model;

import exceptions.*;
import model.cards.DevelopmentCard;
import model.cards.ExtraDepot;
import model.cards.LeaderCard;
import model.player.Player;
import model.resources.Resource;
import model.singleplayer.Lorenzo;
import model.singleplayer.Token;
import model.table.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Table table;
    private Player currentPlayer;
    private Lorenzo cpu;
    public ArrayList<Token> tokens;
    private final boolean singlePlayer;
    private boolean isOver;
    private boolean lastTurnIsEnded;
    private String finalMessage;

    public Game(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
        initializeGame();
    }
    public void initializeGame(){
        this.isOver = false;
        this.lastTurnIsEnded = false;
        table = new Table();
        if(singlePlayer){
            cpu = new Lorenzo(table.getDevBoard());
            tokens = cpu.getTokens();
        }
    }

    /**Adds a new Player's instance during the setup
     * @param newPlayer a Player instance
     */
    public void addPlayer(Player newPlayer){
        if(players.size()<4){
            players.add(newPlayer);
        }
    }

    /**Adds the Inkwell to the first Player, setup it as the currentPlayer and set the Players turn order
     */
    public void setOrder(){
        for(Player player : players){
            if(players.indexOf(player)== 0) {
                player.getPlayerBoard().setInkwell(true);
                currentPlayer = player;
            }
            player.setStartingTurn(players.indexOf(player));
        }
    }

    /**Return a LeaderCard quartet
     * @return
     */
    public ArrayList<LeaderCard> sendQuartet(){
        return table.sendQuartet();
    }

    /**Checks if a list of DevelopmentCards is present in Player's CardSlots
     * @param requirements a DevelopmentCard ArrayList
     * @return true if @requirements is present, false if at least one is not present
     */
    private boolean checkDevCards(ArrayList<DevelopmentCard> requirements){
        for(DevelopmentCard card : requirements){
            if(!currentPlayer.getPlayerBoard().getCardSlots().isPresent(card))
                return false;
        }
        return true;
    }

    /**Checks if a list of Resources is present in Player's WarehouseDepot or Strongbox
     * @param cost a Resource ArrayList
     * @param remove true if remove @cost after a successful check, false if not
     * @return true if @cost is present, false if not
     */
    private boolean checkResources(ArrayList<Resource> cost, boolean remove){

        ArrayList<Resource> clonedWarehouse = (ArrayList<Resource>) currentPlayer.getPlayerBoard().getWarehouseDepot().status().clone();
        ArrayList<Resource> clonedStrongbox = (ArrayList<Resource>) currentPlayer.getPlayerBoard().getStrongbox().status().clone();
        for(Resource r :cost)
            if(clonedWarehouse.remove(r));
            else if(clonedStrongbox.remove(r));
            else return false;

        if(remove){
            removeResource(cost);
        }
        return true;
    }

    /**Removes Resources from Player's WarehouseDepot or Strongbox or both
     * @param cost a Resource ArrayList
     */
    private void removeResource(ArrayList<Resource> cost){
        for(Resource r: cost){
            try{
                currentPlayer.getPlayerBoard().getWarehouseDepot().removeResource(r);
            } catch (ResourceNotFoundException e) {
                try {
                    currentPlayer.getPlayerBoard().getStrongbox().removeResource(r);
                }catch(ResourceNotFoundException f){
                    throw new UnknownError("SERVER ERROR!");
                }
            }
        }

    }

    /**Check if LeaderCard's ability can be used (it's enabled and not null)
     * @param card a LeaderCard instance
     * @return true if it's usable, false if not
     */
    private boolean cardIsUsable(LeaderCard card){
        return card != null && card.isEnabled();
    }

    /**Return the LeaderCard instance in Player's LeaderSlot
     * @param card a LeaderCard instance
     * @return the exactly instance of the LeaderCard copy of @card in Player's LeaderSlot, or null
     */
    private LeaderCard getLeaderCard(LeaderCard card){
        LeaderCard cardFromSlot = null;
        if(card!=null) {
            for (LeaderCard leaderCard : currentPlayer.getPlayerBoard().getLeaders())
                if (leaderCard.getId() == card.getId())
                    cardFromSlot = leaderCard;
        }
        return cardFromSlot;
    }

    /**Purchase a DevelopmentCard from the DevelopmentBoard to Player's CardSlots
     * @param numDeck the number of deck in the DevelopmentBoard where pop the DevelopmentCard from
     * @param slotPosition the number of CardSlots slot in the PlayerBoard where add the DevelopmentCard
     * @param cardFromClient the LeaderCard instances that can be optionally added to benefit of its leader ability. null if Player won't use a LeaderCard in the action
     * @throws FullCardSlotException if the CardSlots slots can't contain new DevelopmentCard instances
     * @throws NonCorrectLevelCardException if the DevelopmentCard don't respect the Level requirements to be added
     * @throws InsufficientResourcesException if Player's Strongbox nor WarehouseDepot contain the entire Resource list to purchase the card
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws EmptyDeckException if the deck number is referred to an empty in the DevelopmentBoard
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void buyDevCard(int numDeck, int slotPosition, LeaderCard cardFromClient) throws FullCardSlotException,
            NonCorrectLevelCardException,
            InsufficientResourcesException,
            UnusableCardException,
            EmptyDeckException,
            InvalidActionException {

        if(!currentPlayer.canBuyDevCards())
            throw new InvalidActionException("you already did an action in this turn!\n");

        Resource discount;
        Deck deck = table.getDevBoard().getDeck(numDeck);
        ArrayList<Resource> cost = deck.getCost();

        LeaderCard card = getLeaderCard(cardFromClient);
        if(card!=null) {
            if (cardIsUsable(card) && card.isDiscount()) {
                discount = card.whichDiscount();
                cost.remove(discount);
            }
            else
                throw new UnusableCardException("can't use this leader card!");
        }


        if(checkResources(cost, true)){
            currentPlayer.getPlayerBoard().getCardSlots().addCard(slotPosition, deck.popCard());
            if(currentPlayer.getPlayerBoard().getCardSlots().isOver()){
                startLastTurn();
                this.finalMessage = "\nVICTORY! YOU BOUGHT YOUR 7th DEVELOPMENT CARD!\n";
            }
            currentPlayer.didAction();
        }
        else{
            throw new InsufficientResourcesException("Can't buy this card: insufficient resources!");
        }
    }

    /**Get a Resource ArrayList from the MarketBoard into Player's WarehouseDepot
     * @param line true if Player has chosen Resources from a MarketBoard line, false if has chosen it from a MarketBoard column
     * @param num the number of MarketBoard line/column where get the Resources from
     * @param cardFromClient the LeaderCard instances that can be optionally added to benefit of its leader ability. null if Player won't use a LeaderCard in the action
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void buyResources(boolean line, int num, LeaderCard cardFromClient) throws UnusableCardException,
            InvalidActionException {
        if(!currentPlayer.canBuyResources())
            throw new InvalidActionException("you already did an action in this turn!\n");
        ArrayList<Resource> bought = new ArrayList<>();
        LeaderCard card = getLeaderCard(cardFromClient);
        if(line &&  num>=0 && num<=2) {
            bought = table.getMarketBoard().addMarketLine(num, card);
        }
        else if(!line && num>=0 && num<=3) {
            bought = table.getMarketBoard().addMarketColumn(num, card);
        }

        for (Resource res : bought) {
            if (res == Resource.FAITH) {
                faithAdvance(1);
            }
            else {
                try{
                    currentPlayer.getPlayerBoard().getWarehouseDepot().addResource(res);
                    } catch(FullWarehouseException e){
                        for(Player player: this.players)
                            if(!player.getNickname().equals(currentPlayer.getNickname()))
                                player.getPlayerBoard().getFaithTrack().faithAdvance();
                    }

                }
            }
        currentPlayer.didAction();
    }

    /**Activates from a Player's CardSlots last added DevelopmentCard the production of Resources. If succeed, Resources are added to Player's Strongbox
     * @param slot the number of slot in CardSlots where pop the DevelopmentCard from
     * @param chosenOutput the Resource that can be produced if the @cardFromClient is an ExtraDepot instance. null if Player won't use a LeaderCard in the action
     * @param cardFromClient the LeaderCard instances that can be optionally added to benefit of its leader ability. null if Player won't use a LeaderCard in the action
     * @throws InsufficientResourcesException if Player's Strongbox nor WarehouseDepot contain the entire Resource list to activate the production
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws InvalidActionException if the Player has already did an action in the current turn
     * @throws EmptySlotException if the slot number is referred to an empty in Player's CardSlots
     */
    public void devCardProduction(int slot, Resource chosenOutput, LeaderCard cardFromClient) throws
            InsufficientResourcesException, UnusableCardException, InvalidActionException, EmptySlotException {

        if(!currentPlayer.canDoProduction())
            throw new InvalidActionException("you already did an action in this turn!\n");
        LeaderCard card = getLeaderCard(cardFromClient);
        ArrayList<Resource> prodResources = new ArrayList<>();
        ArrayList<Resource> cost = new ArrayList<>();
        try {
            if (card != null) {
                if (checkExtraProd(card)) {
                    cost.add(card.getExtraProdInput());
                    card.setChosenOutput(chosenOutput);
                    prodResources.addAll(card.production());
                } else throw new UnusableCardException("can't use this leader card!");
            }
            cost.addAll(currentPlayer.getPlayerBoard().getCardSlots().getCard(slot).getProdInput());
            if (checkResources(cost, true)) {
                prodResources.addAll(currentPlayer.getPlayerBoard().getCardSlots().getCard(slot).createProduction());
                for (Resource res : prodResources) {
                    if (res == Resource.FAITH)
                        faithAdvance(1);
                    else
                        currentPlayer.getPlayerBoard().getStrongbox().addResource(res);
                }
                currentPlayer.didProduction();
            } else {
                throw new InsufficientResourcesException("Can`t do this production: insufficient resources!");
            }
        }catch (NullPointerException e){
            throw new EmptySlotException("slot is empty!");
        }
    }


    /**Activate from the Player's PlayerBoard the production of Resources. If succeed, Resources are added to Player's Strongbox
     * @param input the Resource ArrayList used to make the production
     * @param output the Resource produced
     * @param cardFromClient the Resource that can be produced if the @cardFromClient is an ExtraDepot instance. null if Player won't use a LeaderCard in the action
     * @param chosenOutput the LeaderCard instances that can be optionally added to benefit of its leader ability. null if Player won't use a LeaderCard in the action
     * @throws InsufficientResourcesException if Player's Strongbox nor WarehouseDepot contain the entire Resource list to activate the production
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard cardFromClient, Resource chosenOutput) throws InsufficientResourcesException, UnusableCardException, InvalidActionException {
        if(!currentPlayer.canDoProduction())
            throw new InvalidActionException("you already did an action in this turn!");
        ArrayList<Resource> prodResources = new ArrayList<>();
        ArrayList<Resource> cost = new ArrayList<>();
        LeaderCard card = getLeaderCard(cardFromClient);

        if(card!=null) {
            if (checkExtraProd(card)) {
                cost.add(card.getExtraProdInput());
                card.setChosenOutput(chosenOutput);
                prodResources.addAll(card.production());
            }else throw new UnusableCardException("can't use this leader card!");
        }
        cost.addAll(input);
        if(checkResources(cost, true )){
            prodResources.add(output);
            for (Resource res : prodResources) {
                if (res == Resource.FAITH)
                    faithAdvance(1);
                else currentPlayer.getPlayerBoard().getStrongbox().addResource(res);
            }
            currentPlayer.didProduction();
        }
        else{
            throw new InsufficientResourcesException("Can't do this production: insufficient resources!");

        }
    }

    /**Check is an ExtraDepot card is usable
     * @param card a LeaderCard instance
     * @return true if @card is ExtraDepot and it's enabled, false if not
     */
    private boolean checkExtraProd(LeaderCard card){
        if(cardIsUsable(card) && card.isExtraProd())
           return true;
        return false;
    }

    /**Advances the player's pawn position in the Faith Track, calling faithAdvance in FaithTrack
     * @param advance how many position the player gets
     */
    public void faithAdvance (int advance){
        boolean[] check;
        for(int i=0; i<advance ;i++) {
            FaithBox faithBox  = currentPlayer.getFaithTrack().faithAdvance();
            if (faithBox.getPosition() >= 24) {
                startLastTurn();
                this.finalMessage = "\nVICTORY! YOU REACHED THE END OF THE FAITH TRACK!\n";
                return;
            }
            check = faithBox.getPopeFlag();
            checkPopeFlags(check);
        }
    }

    /**Check if the currentPlayer's FaithBox is in the "report in Vatican" section and if it is, adds the Victory Points to currentPlayer and, eventually, to the other Players
     * @param flags the boolean flag from the currentPlayer's current FaithBox
     */
    public void checkPopeFlags(boolean[] flags){
        if (flags[0]){
            for(Player p : players){
                if(p.getPlayerBoard().getFaithBox().getPosition()>=5)
                    p.addPoints(2);
                p.getPlayerBoard().getFaithBox().setPopeFlag(false,false,false);
            }
        }
        if (flags[1]){
            for(Player p : players){
                if(p.getPlayerBoard().getFaithBox().getPosition()>=12)
                    p.addPoints(3);
                p.getPlayerBoard().getFaithBox().setPopeFlag(false,false,false);
            }
        }
        if(flags[2]){
            for(Player p : players){
                if(p.getPlayerBoard().getFaithBox().getPosition()>=19)
                    p.addPoints(4);
                p.getPlayerBoard().getFaithBox().setPopeFlag(false,false,false);
            }
        }
    }

    /**Activate the LeaderCard ability until the end of the game
     * @param cardFromClient the LeaderCard copy of instance that will be activated in the Player's PlayerBoard
     * @throws InsufficientResourcesException  if Player's Strongbox nor WarehouseDepot contain the entire Resource list to activate the LeaderCard
     * @throws InsufficientRequirementsException if Player's CardSlots doesn't contains the DevelopmentCards required
     * @throws InputMismatchException if Game can't find the LeaderCard instance copy in the Player's LeaderSlot
     * //@throws UnusableCardException this will never been throw
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void activateLeaderCard(LeaderCard cardFromClient) throws InsufficientResourcesException,
            InsufficientRequirementsException, InputMismatchException, UnusableCardException, InvalidActionException {
        if(!currentPlayer.canDoLeader())
            throw new InvalidActionException("you already did a leader action!");
        System.out.println("before activation:"+currentPlayer.getPlayerBoard().getWarehouseDepot().status()+currentPlayer.getPlayerBoard().getStrongbox().status());
        LeaderCard card = getLeaderCard(cardFromClient);
        if(card == null)
            throw new InputMismatchException("Can't find this leader card!");
        if(card.isExtraDepot()){
            ArrayList<Resource> requirements = card.getRequiredResources();
            if(checkResources(requirements, false)) {
                card.activate();
                currentPlayer.didLeader();
                currentPlayer.getPlayerBoard().getWarehouseDepot().addNewExtraDepot((ExtraDepot) card);
            }
            else{
                throw new InsufficientResourcesException("Can't activate this leader card: insufficient resources!");
            }
        }
        else{
            ArrayList<DevelopmentCard> requirements = card.getRequiredCards();
            if(checkDevCards(requirements)) {
                card.activate();
                System.out.println("after activation:" + currentPlayer.getPlayerBoard().getWarehouseDepot().status() + currentPlayer.getPlayerBoard().getStrongbox().status());
            }
            else{
                throw new InsufficientRequirementsException("Can't activate this leader card: insufficient resources!");
            }
        }
    }

    /**Throws a LeaderCard from the Player's CardSlots, who earns a FaithPoint
     * @param card the LeaderCard copy of instance that will be activated in the Player's PlayerBoard
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void throwLeaderCard(LeaderCard card) throws InvalidActionException {
        if(!currentPlayer.canDoLeader())
            throw new InvalidActionException("you already did a leader action!");
        currentPlayer.getPlayerBoard().removeLeaderCard(card.getId());
        currentPlayer.didLeader();
        faithAdvance(1);
    }

    /**Updates the Player's Strongbox and rehabilitates the production from CardSlots' cards.
     * Updates Player's actions for the next time he will be the currentPlayer again.
     * Ends the turn updating the new currentPlayer with the next Player in players ArrayList
     */
    public void updateTurn(){
        updateStrongbox();
        updateCardSlots();
        currentPlayer.updateActions();
        changeTurn();
    }

    /**Updates the DevelopmentCard used in the production to be usable again the next time Player will be currentPlayer
     *
     */
    private void updateCardSlots(){
        for(int i = 0; i<3; i++)
            if(currentPlayer.getPlayerBoard().getCardSlots().getCard(i)!=null)
                currentPlayer.getPlayerBoard().getCardSlots().getCard(i).backUsable();
    }

    /**Adds the Resources produced in this turn to the Player's Strongbox
     *
     */
    public void updateStrongbox(){
        currentPlayer.getPlayerBoard().getStrongbox().updateStrongbox();
    }

    /**If Game is singlePlayer: runs the Lorenzo's pick. If Game is over, set the victory and get the Lorenzo's last action
     * If Game is multi player: update the currentPlayer with the next Player in players ArrayList.
     */
    private void changeTurn(){
        if(singlePlayer) {
            cpu.pick();
            if(cpu.gameIsOver()){
                this.isOver = true;
                this.currentPlayer.setVictory(false);
                this.finalMessage = cpu.getLorenzoLastAction();
            }
        }
        else {
            int turn = players.indexOf(currentPlayer);
            if (turn == players.size()-1)
                if(isOver)
                    lastTurnIsEnded = true;
                else
                    turn = 0;
            else
                turn += 1;
            currentPlayer = players.get(turn);
        }
    }


    /**Returns a String with the final ranking in multi player Game
     * @return a String
     */
    public String getRanking()  {
        ArrayList<Integer> scores = new ArrayList<>();
        String finalScore = "";

        for(Player p: players)
            scores.add(p.getScore());

        Collections.sort(scores);
        Collections.reverse(scores);

        for(Integer i: scores)
            for(Player p: players)
                if(i == p.getPoints()) {
                    if(i.equals(scores.get(0)))
                        p.setVictory(true);
                    finalScore += p.getNickname() + " " + p.getPoints() + "\n";
                }

        return finalScore;
    }

    /**Returns a flag if Game is over
     * @return a boolean
     */
    public boolean isOver(){
        return this.isOver;
    }

    /**
     *
     */
    private void startLastTurn(){
        this.isOver = true;
        if(!isSinglePlayer()){
            ArrayList<Player> newTurns = new ArrayList<>();
            int start = players.indexOf(currentPlayer);
            for(int i = start; i<players.size(); i++)
                newTurns.add(players.get(i));
            for(int i = 0; i<start; i++)
                newTurns.add(players.get(i));
            players = newTurns;
        }
    }

    public boolean lastTurnIsOver(){ return this.lastTurnIsEnded; }

    public String endingSinglePlayerGame(){
        return this.finalMessage;
    }


    //GETTERS
    public Player getPlayer(String username) throws NoSuchUsernameException {
        for(Player player: players)
            if(player.getNickname().equals(username))
                return player;
        throw new NoSuchUsernameException("username not found");
    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public DevelopmentBoard getDevBoard(){
        return table.getDevBoard();
    }
    public MarketBoard getMarketBoard(){
        return table.getMarketBoard();
    }
    public ArrayList<FaithTrack> getFaithTracks(){
        ArrayList<FaithTrack> tracks = new ArrayList<>();
        for(Player player : players)
            tracks.add(player.getPlayerBoard().getFaithTrack());
        return tracks;
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    public boolean isSinglePlayer() {
        return singlePlayer;
    }
    public Lorenzo getLorenzo(){
        return this.cpu;
    }

    public String getWinner(){
        for(Player p: players)
            if(p.isTheWinner())
                return p.getNickname();
        return null;
    }
    //------------------------------------------------------------------------------------------------------------------

    public void cheat(){
        ArrayList<Resource> res = new ArrayList<>();
        res.add(Resource.COIN);
        res.add(Resource.STONE);
        res.add(Resource.SHIELD);
        res.add(Resource.SERVANT);
        for(Resource r: res)
            for(int i = 0; i<10; i++)
                currentPlayer.getPlayerBoard().getStrongbox().addResource(r);
    }

    public void cheat2(){
        faithAdvance(20);
    }

}
