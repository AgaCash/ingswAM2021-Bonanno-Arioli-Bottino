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
    public void addPlayer(Player newPlayer){
        if(players.size()<4){
            players.add(newPlayer);
        }
    }
    public void setOrder(){
        Collections.shuffle(players);
        for(Player player : players){
            if(players.indexOf(player)== 0) {
                player.getPlayerBoard().setInkwell(true);
                currentPlayer = player;
            }
            player.setStartingTurn(players.indexOf(player));
        }
    }
    public ArrayList<LeaderCard> sendQuartet(){
        return table.sendQuartet();
    }

    //===========UTILITIES
    private boolean checkDevCards(ArrayList<DevelopmentCard> requirements){
        for(DevelopmentCard card : requirements){
            if(!currentPlayer.getPlayerBoard().getCardSlots().isPresent(card))
                return false;
        }
        return true;
    }
    /*checkResources: checks if @cost is present in this.warehouseDepot,
    this.strongbox, or both
    @remove flag sets if remove @cost after the check from player's resources or not
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
    private boolean cardIsUsable(LeaderCard card){
        return card != null && card.isEnabled();
    }

    private LeaderCard getLeaderCard(LeaderCard card){
        LeaderCard cardFromSlot = null;
        if(card!=null) {
            for (LeaderCard leaderCard : currentPlayer.getPlayerBoard().getLeaders())
                if (leaderCard.getId() == card.getId())
                    cardFromSlot = leaderCard;
        }
        return cardFromSlot;
    }


    //--------------------BUY DEV CARDS--------------------
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

    //--------------------MARKET--------------------
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

    //--------------------PRODUCTION--------------------
    /*devCadProduction: activates production of the last card popped from @slot
    @chosenOutput is set by @Controller and it's the optional resource produced by @card
     */
    public void devCardProduction(int slot, Resource chosenOutput, LeaderCard cardFromClient) throws
            InsufficientResourcesException, UnusableCardException, InvalidActionException, EmptyDeckException {

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
            throw new EmptyDeckException("slot is empty!");
        }
    }

    /*defaultProduction: activates production from the developmentBoard
    @input and @output are set by @Controller
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

    private boolean checkExtraProd(LeaderCard card){
        if(cardIsUsable(card) && card.isExtraProd())
           return true;
        return false;
    }

    //--------------------FAITH TRACK--------------------
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
    //--------------------LEADER CARDS--------------------

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

    public void throwLeaderCard(LeaderCard card) throws InvalidActionException {
        if(!currentPlayer.canDoLeader())
            throw new InvalidActionException("you already did a leader action!");
        currentPlayer.getPlayerBoard().removeLeaderCard(card.getId());
        currentPlayer.didLeader();
        faithAdvance(1);
    }

    //END TURN
    public void updateTurn(){
        updateStrongbox();
        updateCardSlots();
        currentPlayer.updateActions();
        changeTurn();
    }
    private void updateCardSlots(){
        for(int i = 0; i<3; i++)
            if(currentPlayer.getPlayerBoard().getCardSlots().getCard(i)!=null)
                currentPlayer.getPlayerBoard().getCardSlots().getCard(i).backUsable();
    }
    public void updateStrongbox(){
        currentPlayer.getPlayerBoard().getStrongbox().updateStrongbox();
    }
    //change turn
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


    //============ENDGAME=======
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

    public boolean isOver(){
        return this.isOver;
    }

    private void startLastTurn(){
        this.isOver = true;
        if(!isSinglePlayer()){
            System.out.println("riga 436 "+players);
            ArrayList<Player> newTurns = new ArrayList<>();
            int start = players.indexOf(currentPlayer);
            for(int i = start; i<players.size(); i++)
                newTurns.add(players.get(i));
            for(int i = 0; i<start; i++)
                newTurns.add(players.get(i));
            players = newTurns;
            System.out.println("riga 444 "+players);
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
   /* public ArrayList<String> getPlayerTurns(){
        ArrayList<String> turns = new ArrayList<>();
        for(Player p : players)
            turns.add(p.getNickname());
        return turns;
    }*/
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
