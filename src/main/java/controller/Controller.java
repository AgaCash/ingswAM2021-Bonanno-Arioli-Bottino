package controller;

import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.*;
import model.Game;
import model.cards.LeaderCard;
import model.player.Player;
import model.resources.Resource;
import model.singleplayer.Lorenzo;
import model.table.DevelopmentBoard;
import model.table.FaithTrack;
import model.table.MarketBoard;
import network.messages.gameMessages.*;
import utilities.LeaderCardDeserializer;
import view.VirtualClient;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Controller {
    private final int id;
    private final Game game;
    private ArrayList<VirtualClient> views;
    private ArrayList<String> disconnectedPlayer;
    private int readyPlayers = 0;

    public Controller(int id, ArrayList<VirtualClient> views){
        this.views = views;
        this.id = id;
        boolean isSinglePlayer = (views.size()==1);
        game = new Game(isSinglePlayer);
        disconnectedPlayer = new ArrayList<>();
        System.out.println("CONTROLLER CREATED");
    }
    //EVERY MESSAGE
    public void executeCommand(GameMessage message, VirtualClient client) {
        if(message.getUsername().equals(getCurrentPlayer().getNickname()))
            message.executeCommand(this, client);
    }

    //PING
    //todo: 2 exception quando ci si disconnette da game
    //          -> è perchè game in multiplayer non viene ancora inizializzato
    public void disconnectPlayer(String username){
        System.out.println(username+" disconnected!");
        if(game.getCurrentPlayer().getNickname().equals(username) && !isSinglePlayer()){
            endTurn(username);
        }
        for(VirtualClient v: views){
            if(v.getVirtualView().getUsername().equals(username)){
                disconnectedPlayer.add(username);
                views.remove(v);
                break;
            }
        }
        PlayerDisconnectedMessage pdm = new PlayerDisconnectedMessage(username);
        views.forEach((v)->v.getVirtualView().sendPlayerResilienceMessage(pdm));
    }

    //RESILIENCE
    public boolean isUsernameDisconnected(String username){
        if(disconnectedPlayer.contains(username))
            return true;
        return false;
    }

    public boolean isSinglePlayer(){
        return game.isSinglePlayer();
    }

    public void reconnectUsername(String username, VirtualClient virtualClient){
        disconnectedPlayer.remove(username);
        PlayerReconnectedMessage prm = new PlayerReconnectedMessage(username);
        views.forEach((v)-> v.getVirtualView().sendPlayerResilienceMessage(prm));
        views.add(virtualClient);
    }

    //STARTUP
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
    //single player
    public void addSinglePlayer(Player newPlayer){
        game.addPlayer(newPlayer);
    }
    //multiplayer
    public void addMultiPlayers(ArrayList<Player> newPlayers, ArrayList<VirtualClient> newViews){
        views = newViews;
        for(Player player : newPlayers)
            game.addPlayer(player);
    }
    public void notifyReadiness(){
        this.readyPlayers++;
        if(readyPlayers == this.views.size()){
            for(VirtualClient v : views){
                ArrayList<LightPlayer> players = new ArrayList<>();
                game.getPlayers().forEach(element -> players.add(element.convert()));
                System.out.println(game.getPlayers());
                v.getVirtualView().updateSetup(new SetupResponse(v.getVirtualView().getUsername(),
                                                                getCurrentPlayer().getNickname(),
                                                                game.getMarketBoard().convert(),
                                                                game.getDevBoard().convert(),
                                                                players));
            }
        }
    }

    //ACTIONS
    public void buyDevCard(int deck, int slot, LightLeaderCard lightCard) throws FullCardSlotException,
            NonCorrectLevelCardException,
            InsufficientResourcesException,
            EmptyDeckException,
            UnusableCardException, InvalidActionException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        String s = gson.toJson(lightCard);
        LeaderCard card = gson.fromJson(s, LeaderCard.class);
        game.buyDevCard(deck, slot, card);
    }
    public void buyResources(boolean line, int num, LightLeaderCard lightCard) throws UnusableCardException, InvalidActionException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        String s = gson.toJson(lightCard);
        LeaderCard card = gson.fromJson(s, LeaderCard.class);
        game.buyResources(line, num, card);

    }
    public ArrayList<LightResource> getThrewResources(){
        return game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().getThrewResources();
    }
    public void devCardProduction(int slot, LightResource lightChosenOutput, LightLeaderCard lightCard) throws InsufficientResourcesException,
            UnusableCardException, InvalidActionException, EmptyDeckException {
        LeaderCard card =  null; Resource chosenOutput = null;
        if(lightCard!=null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
            String s = gson.toJson(lightCard);
            card = gson.fromJson(s, LeaderCard.class);
            chosenOutput = Resource.valueOf(lightChosenOutput.toString());
        }

        game.devCardProduction(slot, chosenOutput, card);
    }
    public void defaultProduction(ArrayList<LightResource> input, LightResource output, LightLeaderCard lightCard, LightResource lightChosenOutput) throws InsufficientResourcesException,
            UnusableCardException, InvalidActionException {
        ArrayList<Resource> newInput = new ArrayList<>();
        input.forEach(e -> newInput.add(Resource.valueOf(e.toString())));
        Resource newOutput = Resource.valueOf(output.toString());

        LeaderCard card =  null; Resource chosenOutput = null;
        if(lightCard!=null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
            String s = gson.toJson(lightCard);
            card = gson.fromJson(s, LeaderCard.class);
            chosenOutput = Resource.valueOf(lightChosenOutput.toString());
        }

        game.defaultProduction(newInput, newOutput, card, chosenOutput);
    }
    public void activateLeaderCard(LightLeaderCard lightLeaderCard) throws InsufficientRequirementsException,
            InsufficientResourcesException, InputMismatchException, UnusableCardException, InvalidActionException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        LeaderCard card = gson.fromJson(gson.toJson(lightLeaderCard), LeaderCard.class);
        game.activateLeaderCard(card);
    }

    public void throwLeaderCard(LightLeaderCard lightLeaderCard) throws InvalidActionException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        LeaderCard card = gson.fromJson(gson.toJson(lightLeaderCard), LeaderCard.class);
        game.throwLeaderCard(card);
    }


    public void endTurn(String username) {
        EndTurnResponse response;
        if (game.isSinglePlayer()) {
            game.updateTurn();
            if(!game.isOver())
                response = new EndTurnResponse(username,
                        game.getDevBoard().convert(),
                        game.getCurrentPlayer().getFaithTrack().convert(),
                        game.getLorenzo().getLorenzoLastAction(),
                        game.getCurrentPlayer().getPlayerBoard().getCardSlots().convert(),
                        game.getCurrentPlayer().getPlayerBoard().getStrongbox().convert(),
                        game.getLorenzo().getFaithBox().getPosition());
            else
                response = new EndTurnResponse(username, game.endingSinglePlayerGame());
            getViews().get(0).getVirtualView().sendEndTurnNotify(response);
        }
        else{
            Player lastPlayer = game.getCurrentPlayer();
            game.updateTurn();
            if(game.lastTurnIsOver()){
                response = new EndTurnResponse(username, game.getRanking(), game.getWinner());
                EndTurnResponse finalResponse = response;
                getViews().forEach((element) -> element.getVirtualView().sendEndTurnNotify(finalResponse));
                /*try {
                    Lobby lobby = LobbyHandler.getInstance().getLobbyFromUsername(username);
                    Player creator = lobby.getCreator();
                    lobby.leaveLobby(creator.getNickname());
                } catch (NoSuchUsernameException e) {
                    e.printStackTrace();
                }

                 */
            }
            else do {
                response = new EndTurnResponse(username,
                        game.getCurrentPlayer().getNickname(),
                        lastPlayer.getPlayerBoard().getStrongbox().convert());
                EndTurnResponse finalResponse1 = response;
                getViews().forEach((element) -> element.getVirtualView().sendEndTurnNotify(finalResponse1));
                if(disconnectedPlayer.size() == game.getPlayers().size()){
                    //fanculo tutto todo: aggiungere qualcosa di piu elegante?
                }
            } while (disconnectedPlayer.contains(game.getCurrentPlayer().getNickname()));
            //OCCHIO AGLI UPDATE E AL MECCANISMO DI UNDERSTANDING DEL TURNO DA PARTE DEL CLIENT
            //
            //SE TUTTI E 4 SI SONO DISCONNESSI FANCULO TUTTO SI BUTTA VIA LA PARTITA
        }
    }


    //ERRORS
    public void handleError(String message){
        views.forEach(element -> element.getVirtualView().updateInternalError(new InternalErrorNotify(element.getVirtualView().getUsername(), message)));

    }
    public void handleError(VirtualClient view, String message){
        view.getVirtualView().updateInternalError(new InternalErrorNotify(view.getVirtualView().getUsername(), message));
    }

    //GETTERS
    public Player getPlayer(String username) throws NoSuchUsernameException { return game.getPlayer(username); }
    public ArrayList<FaithTrack> getFaithTracks(){
        return game.getFaithTracks();
    }
    public ArrayList<Player> getPlayers(){
        return game.getPlayers();
    }
    public ArrayList<VirtualClient> getViews(){
        return views;
    }
    public int getId() {
        return id;
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
    public Lorenzo getLorenzo(){return game.getLorenzo(); }

    public void cheat(){
        game.cheat();
    }
    public void cheat2(){
        game.cheat2();
    }
}
