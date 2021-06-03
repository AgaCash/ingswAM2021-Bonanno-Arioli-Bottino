package model;

import exceptions.*;
import model.cards.*;
import model.player.Player;
import model.resources.Resource;
import model.singleplayer.Lorenzo;
import model.singleplayer.Token;
import model.table.*;


import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Table table;
    private Player currentPlayer;
    private Lorenzo cpu;
    public ArrayList<Token> tokens;
    private boolean singlePlayer;
    private boolean didAction = false;

    private boolean isOver;
    private boolean victory;
    private String finalMessage;

    //COSTRUTTORE
    public Game(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
        initializeGame();
    }
    public void initializeGame(){
        table = new Table();
        Collections.shuffle(players);
        if(singlePlayer){
            cpu = new Lorenzo(table.getDevBoard());
            tokens = cpu.getTokens();
        }
    }

    //START
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
                System.out.println(currentPlayer.getNickname());
            }
            player.setStartingTurn(players.indexOf(player));
            System.out.println(player);
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

        ArrayList<Resource> clonedWarehouse = currentPlayer.getPlayerBoard().getWarehouseDepot().status();
        ArrayList<Resource> clonedStrongbox = currentPlayer.getPlayerBoard().getStrongbox().status();
        for(Resource r :cost){
            if(clonedWarehouse.remove(r)){
                if(remove)
                    try {
                        currentPlayer.getPlayerBoard().getWarehouseDepot().removeResource(r);
                    } catch(ResourceNotFoundException e){
                        return false;
                    }
            }else if(clonedStrongbox.remove(r)){
                if(remove)
                    try {
                        currentPlayer.getPlayerBoard().getStrongbox().removeResource(r);
                    }catch(ResourceNotFoundException e){
                        return false;
                    }
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

    //============UTILITIES FOR PING
    public void addPlayerAtIndex(Player player, int index){
        players.add(index, player);
    }

    public void removePlayer(String username){

    }
    //============GIOCO

    //--------------------BUY DEV CARDS--------------------
    public void buyDevCard(int numDeck, int slotPosition, Discount card) throws FullCardSlotException,
            NonCorrectLevelCardException,
            InsufficientResourcesException,
            UnusableCardException,
            EmptyDeckException,
            InvalidActionException {
        if(didAction)
            throw new InvalidActionException("you already did an action in this turn!\n");
        Resource discount;
        Deck deck = table.getDevBoard().getDeck(numDeck);
        ArrayList<Resource> cost = deck.getCost();
        try {
            discount = card.whichDiscount();
            cost.remove(discount);
        }catch(NullPointerException e){
            ;//todo rivedere
        }
        if(checkResources(cost, true)){
            currentPlayer.getPlayerBoard().getCardSlots().addCard(slotPosition, deck.popCard());
            if(currentPlayer.getPlayerBoard().getCardSlots().isOver()){
                this.isOver = true;
                this.victory = true;
                this.finalMessage = "\nVITTORIA! HAI COMPRATO LA TUA SETTIMA CARTA SVILUPPO\n";
            }
            this.didAction=true;
        }
        else{
            throw new InsufficientResourcesException("Can't buy this card: insufficient resources!");
        }
    }

    //--------------------MARKET--------------------
    public void buyResources(boolean line, int num, WhiteConverter card) throws UnusableCardException,
            InvalidActionException {
        if(didAction)
            throw new InvalidActionException("you already did an action in this turn!\n");
        ArrayList<Resource> bought = new ArrayList<>();
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
                                player.getPlayerBoard().getFaithTrack().faithAdvance(player.getFaithBox());
                    }

                }
            }
        this.didAction=true;

    }

    //--------------------PRODUCTION--------------------
    /*devCadProduction: activates production of the last card popped from @slot
    @chosenOutput is set by @Controller and it's the optional resource produced by @card
     */
    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws
            InsufficientResourcesException, UnusableCardException {
        ArrayList<Resource> prodResources;
        if(checkResources(currentPlayer.getPlayerBoard().getCardSlots().getCard(slot).getProdInput(), true)){
            prodResources = currentPlayer.getPlayerBoard().getCardSlots().getCard(slot).createProduction(card);
            try {
                if (checkExtraProd(card)) {
                    card.setChosenOutput(chosenOutput);
                    prodResources.addAll(card.production());
                    for (Resource res : prodResources)
                        if (res == Resource.FAITH) {
                            faithAdvance(1);
                            prodResources.remove(Resource.FAITH);
                        }
                }
            }catch(NullPointerException e){
                //todo va bene?
            }
            prodResources.forEach(element -> currentPlayer.getPlayerBoard().getStrongbox().addResource(element));
        }
        else {
            throw new InsufficientResourcesException("Can`t do this production: insufficient resources!");
        }
    }
    /*defaultProduction: activates production from the developmentBoard
    @input and @output are set by @Controller
     */
    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput) throws InsufficientResourcesException, UnusableCardException, InvalidActionException {
        if(didAction)
            throw new InvalidActionException("you already did an action in this turn!\n");
        ArrayList<Resource> prodResources = new ArrayList<>();

        if(checkResources(input, true )){
            prodResources.add(output);
            try {
                if (checkExtraProd(card)) {
                    card.setChosenOutput(chosenOutput);
                    prodResources.addAll(card.production());
                    for (Resource res : card.production())
                        if (res == Resource.FAITH) {
                            faithAdvance(1);
                            prodResources.remove(Resource.FAITH);
                        }
                }
            }catch(NullPointerException e){;}
            prodResources.forEach(element -> currentPlayer.getPlayerBoard().getStrongbox().addResource(element));
            this.didAction =true;
        }
        else{
            throw new InsufficientResourcesException("Can't do this production: insufficient resources!");

        }
    }

    private boolean checkExtraProd(LeaderCard card) throws UnusableCardException {
        if(cardIsUsable(card) && card.isExtraProd()) {
            ArrayList<Resource> extraProdInput = new ArrayList<>();
            extraProdInput.add(card.getExtraProdInput());
            if (checkResources(extraProdInput, true))
                return true;
        }
        return false;
    }

    //--------------------FAITH TRACK--------------------
    /** advances the player's pawn position in the Faith Track, calling faithAdvance in FaithTrack
     * @param advance how many position the player gets
     */
    public void faithAdvance (int advance){
        boolean[] check;
        for(int i=0; i<advance ;i++) {
            FaithBox faithBox  = currentPlayer.getFaithTrack().faithAdvance(currentPlayer.getFaithBox());
            if (faithBox.getPosition() == 24) {
                this.isOver = true;
                this.victory = true;
                this.finalMessage = "\nVITTORIA! HAI RAGGIUNTO LA FINE DEL FAITHTRACK\n";
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

    public void throwLeaderCard(LeaderCard card){
        currentPlayer.getPlayerBoard().removeLeaderCard(card);
        faithAdvance(1);
    }

    //END TURN
    public void updateTurn(){
        updateStrongbox();
        updateCardSlots();
        this.didAction = false;
        changeTurn();
    }
    private void updateCardSlots(){
        for(int i = 0; i<3; i++)
            try {
                currentPlayer.getPlayerBoard().getCardSlots().getCard(i).backUsable();
            }catch(NullPointerException e){}
    }
    private void updateStrongbox(){
        currentPlayer.getPlayerBoard().getStrongbox().updateStrongbox();
    }
    //change turn
    private void changeTurn(){
        if(singlePlayer) {
            cpu.pick();
            if(cpu.gameIsOver()){
                this.isOver = true;
                this.victory = false;
                this.finalMessage = cpu.getLorenzoLastAction();
            }
        }
        else {
            int turn = players.indexOf(currentPlayer);
            if (turn == players.size()-1)
                turn = 0;
            else
                turn += 1;
            currentPlayer = players.get(turn);
        }
    }


    //============ENDGAME=======
    //contare i punti vittoria dei giocatori su faithTrack, leaderCards, devCards ecc...
    //fare la classifica dei punti vittoria

    public boolean isOver(){
        return this.isOver;
    }

    public boolean victory(){
        return this.victory;
    }

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
    public ArrayList<String> getPlayerTurns(){
        ArrayList<String> turns = new ArrayList<>();
        for(Player p : players)
            turns.add(p.getNickname());
        return turns;
    }
    public Lorenzo getLorenzo(){
        return this.cpu;
    }
    //------------------------------------------------------------------------------------------------------------------

}
