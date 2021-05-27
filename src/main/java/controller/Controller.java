package controller;

import exceptions.*;
import model.Game;
import model.cards.Discount;
import model.cards.ExtraProd;
import model.cards.LeaderCard;
import model.cards.WhiteConverter;
import model.player.Player;
import model.resources.Resource;
import model.table.DevelopmentBoard;
import model.table.FaithTrack;
import model.table.MarketBoard;
import network.messages.gameMessages.GameMessage;
import network.messages.gameMessages.InternalErrorNotify;
import network.messages.gameMessages.SetupResponse;
import view.VirtualClient;

import java.util.ArrayList;

public class Controller {
    private int id;
    private Game game;
    private ArrayList<VirtualClient> views;
    private ArrayList<Player> disconnectedPlayer;
    private int readyPlayers = 0;

    public Controller(int id, ArrayList<VirtualClient> views){
        this.views = views;
        this.id = id;
        boolean isSinglePlayer = (views.size()==1);
        game = new Game(isSinglePlayer);
        System.out.println("CONTROLLER CREATO");
    }

    public void executeCommand(GameMessage message, VirtualClient client) {
        if(message.getUsername().equals(getCurrentPlayer().getNickname()))
            message.executeCommand(this, client);
        else {
            //client.getVirtualView().updateFailedAction(new FailedActionNotify(message.getUsername(), "Not your turn"));
            //todo mantenere failed action? questa non è la singola risposta
        }
    }
    public ArrayList<VirtualClient> getViews(){
        return views;
    }
    public int getId() {
        return id;
    }

    //ping
    public void disconnectPlayer(String username){
        //TODO:
        //  pensare come disconnettere il player tenendone traccia per la riconnessione
        System.out.println(username+" disconnesso! (in realta no uhuhuhuhu)");
        /*
        for(VirtualClient v: views){
            if(v.getVirtualView().getUsername().equals(username)){
                disconnectedPlayer.add(new Player(""));
                views.remove(v);
            }
        }
        */
    }

    //start
    public void setOrder(){
        game.setOrder();
    }
    public synchronized ArrayList<LeaderCard> getLeaderCards(){ return game.sendQuartet(); }
    public void setLeaderCards(String username, ArrayList<LeaderCard> couple) throws NoSuchUsernameException { getPlayer(username).getPlayerBoard().addLeaderCards(couple);}
    public void setChosenStartup(String username, ArrayList<Resource> resources, boolean faithPoint) throws NoSuchUsernameException, FullWarehouseException{
        Player player = getPlayer(username);
        for(Resource element : resources)
            player.getPlayerBoard().getWarehouseDepot().addResource(element);
    }
    public void notifyReadiness(){
        System.out.println("PARTIAMO SEEEEEEE");
        this.readyPlayers++;
        if(readyPlayers == this.views.size()){
            setOrder();
            for(VirtualClient v : views){
                System.out.println("sono: "+v.getVirtualView().getUsername());
                System.out.println("tocca a : "+ getCurrentPlayer());
                v.getVirtualView().updateSetup(new SetupResponse(v.getVirtualView().getUsername(),
                                                                getCurrentPlayer().getNickname(),
                                                                game.getMarketBoard(),
                                                                game.getDevBoard(),
                                                                game.getPlayers()));
            }
        }
    }
    //singleplayer
    public void addSinglePlayer(Player newPlayer){
        game.addPlayer(newPlayer);
    }
    //multiplayer
    public void addMultiPlayers(ArrayList<Player> newPlayers, ArrayList<VirtualClient> newViews){
        views = newViews;
        for(Player player : newPlayers)
            game.addPlayer(player);
    }

    //-----------tutto quello nel gioco
    public void buyDevCard(int deck, int slot, Discount card) throws FullCardSlotException,
            NonCorrectLevelCardException,
            InsufficientResourcesException,
            EmptyDeckException,
            UnusableCardException{

        try {
            game.buyDevCard(deck, slot, card);
        }catch(UnknownError e){
            handleError(e.getMessage());
        }
    }
    public void buyResources(boolean line, int num, WhiteConverter card) throws UnusableCardException {
        game.buyResources(line, num, card);

    }
    public ArrayList<Resource> getThrewResources(){
        return game.getThrewResources();
    }
    public void devCardProduction(int slot, Resource chosenOutput, ExtraProd card) throws InsufficientResourcesException,
            UnusableCardException {
        game.devCardProduction(slot, chosenOutput, card);
    }
    public void defaultProduction(ArrayList<Resource> input, Resource output, LeaderCard card, Resource chosenOutput) throws InsufficientResourcesException,
            UnusableCardException {
        game.defaultProduction(input, output, card, chosenOutput);
    }
    public void activateLeaderCard(LeaderCard card) throws InsufficientRequirementsException,
            InsufficientResourcesException {
        game.activateLeaderCard(card);
    }
    public void throwLeaderCard(LeaderCard card){
        game.throwLeaderCard(card);
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
    public MarketBoard getMarketBoard(){
        return game.getMarketBoard();
    }
    public DevelopmentBoard getDevBoard(){
        return game.getDevBoard();
    }
    public void endTurn(){ game.updateTurn(); }

    public void handleError(String message){
        views.forEach(element -> element.getVirtualView().updateInternalError(new InternalErrorNotify(element.getVirtualView().getUsername(), message)));

    }
    public void handleError(VirtualClient view, String message){
        view.getVirtualView().updateInternalError(new InternalErrorNotify(view.getVirtualView().getUsername(), message));
    }
    public Player getPlayer(String username) throws NoSuchUsernameException {
        return game.getPlayer(username);
    }
    public ArrayList<FaithTrack> getFaithTracks(){
        return game.getFaithTracks();
    }

}
