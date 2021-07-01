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
import model.table.MarketBoard;
import network.messages.gameMessages.*;
import network.server.Lobby;
import network.server.LobbyHandler;
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
    private final int lobbyId;

    public Controller(int id, ArrayList<VirtualClient> views, int lobbyId){
        this.views = views;
        this.id = id;
        boolean isSinglePlayer = (views.size()==1);
        game = new Game(isSinglePlayer);
        disconnectedPlayer = new ArrayList<>();
        //System.out.println("CONTROLLER CREATED");
        this.lobbyId = lobbyId;
    }

    /**Runs the executeCommand() method in GameMessage
     * @param message the GameMessage instance
     * @param client the VirtualClient corresponding the Client interface
     */
    public void executeCommand(GameMessage message, VirtualClient client) {
        if(message.getUsername().equals(getCurrentPlayer().getNickname()))
            message.executeCommand(this, client);
    }

    //PING
    public synchronized void disconnectPlayer(String username){
        System.out.println(username+" disconnected!");
        for(VirtualClient v: views){
            if(v.getVirtualView().getUsername().equals(username)){
                disconnectedPlayer.add(username);
                views.remove(v);
                break;
            }
        }
        if(game.getCurrentPlayer().getNickname().equals(username) && !isSinglePlayer()){
            endTurn(username);
        }
        PlayerDisconnectedMessage pdm = new PlayerDisconnectedMessage(username);
        views.forEach((v)->v.getVirtualView().sendPlayerResilienceMessage(pdm));
    }

    //RESILIENCE
    public synchronized boolean isUsernameDisconnected(String username){
        if(disconnectedPlayer.contains(username))
            return true;
        return false;
    }

    public boolean isSinglePlayer(){
        return game.isSinglePlayer();
    }

    public synchronized void reconnectUsername(String username, VirtualClient virtualClient){
        disconnectedPlayer.remove(username);
        PlayerReconnectedMessage prm = new PlayerReconnectedMessage(username);
        views.forEach((v)-> v.getVirtualView().sendPlayerResilienceMessage(prm));
        views.add(virtualClient);
    }

    /**
     *todo da fare
     */
    public void setOrder(){
        game.setOrder();
    }

    /**Pop a random quartet of LeaderCard instances
     * @return a LeaderCard ArrayList of length four
     */
    public synchronized ArrayList<LeaderCard> getLeaderCards(){ return game.sendQuartet(); }

    /**Add a LeaderCard ArrayList of length two to the PlayerBoard
     * @param username the Player's username owning the LeaderCard couple
     * @param couple the ArrayList of LeaderCard instances
     * @throws NoSuchUsernameException if there's not a Player with such username
     */
    public void setLeaderCards(String username, ArrayList<LeaderCard> couple) throws NoSuchUsernameException { getPlayer(username).getPlayerBoard().addLeaderCards(couple);}

    /**Add the optional Resource instances chosen by a non-first Player in the multi player game's setup
     * @param username the Player's username owning the Resource instances
     * @param resources the Resource ArrayList chosen by the Player
     * @throws NoSuchUsernameException if there's not a Player with such username
     * @throws FullWarehouseException if Player's WarehouseDepot can't contain another different type of Resource
     */
    public void setChosenStartup(String username, ArrayList<Resource> resources) throws NoSuchUsernameException, FullWarehouseException{
        Player player = getPlayer(username);
        for(Resource element : resources)
            player.getPlayerBoard().getWarehouseDepot().addResource(element);
    }

    /**Add the Player instance in the single player game setup
     * @param newPlayer the new Player instance
     */
    public void addSinglePlayer(Player newPlayer){
        game.addPlayer(newPlayer);
    }

    /**Add the new Player instances in the multi player game setup;
     * @param newPlayers the Player ArrayList of players in the game
     * @param newViews the VirtualClient ArrayList
     */
    public void addMultiPlayers(ArrayList<Player> newPlayers, ArrayList<VirtualClient> newViews){
        views = newViews;
        for(Player player : newPlayers)
            game.addPlayer(player);
    }

    /**
     * Notifies the controller that the Player is ready to start the game and sends a SetupResponse to all VirtualViews with the first model Update
     */
    public void notifyReadiness(){
        this.readyPlayers++;
        if(game.isSinglePlayer())
            views.get(0).getVirtualView().updateSetup(new SetupResponse(views.get(0).getVirtualView().getUsername(),
                    game.getCurrentPlayer().convert(),
                    game.getMarketBoard().convert(),
                    game.getDevBoard().convert(),
                    game.getLorenzo().convert()));

        else if(readyPlayers == this.views.size()){
            for(VirtualClient v : views){
                ArrayList<LightPlayer> players = new ArrayList<>();
                game.getPlayers().forEach(element -> players.add(element.convert()));
                v.getVirtualView().updateSetup(new SetupResponse(v.getVirtualView().getUsername(),
                                                                getCurrentPlayer().getNickname(),
                                                                game.getMarketBoard().convert(),
                                                                game.getDevBoard().convert(),
                                                                players));
            }
        }
    }

    /**Purchase a DevelopmentCard from the DevelopmentBoard to Player's CardSlots
     * @param deck the number of deck in the DevelopmentBoard where pop the DevelopmentCard from
     * @param slot the number of CardSlots slot in the PlayerBoard where add the DevelopmentCard
     * @param lightCard the LightLeaderCard instances that can be optionally added to benefit of its leader ability. Method convert it in a LeaderCard instance
     * @throws FullCardSlotException if the CardSlots slots can't contain new DevelopmentCard instances
     * @throws NonCorrectLevelCardException if the DevelopmentCard don't respect the Level requirements to be added
     * @throws InsufficientResourcesException if Player's Strongbox nor WarehouseDepot contain the entire Resource list to purchase the card
     * @throws EmptyDeckException if the deck number is referred to an empty in the DevelopmentBoard
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
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

    /**Get a Resource ArrayList from the MarketBoard into Player's WarehouseDepot
     * @param line true if Player has chosen Resources from a MarketBoard line, false if has chosen it from a MarketBoard column
     * @param num the number of MarketBoard line/column where get the Resources from
     * @param lightCard the LightLeaderCard instances that can be optionally added to benefit of its leader ability. Method convert it in a LeaderCard instance. null if Player won't use a LeaderCard in the action
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void buyResources(boolean line, int num, LightLeaderCard lightCard) throws UnusableCardException, InvalidActionException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        String s = gson.toJson(lightCard);
        LeaderCard card = gson.fromJson(s, LeaderCard.class);
        game.buyResources(line, num, card);

    }

    /**Get the rejected Resources from MarketBoard purchase because of insufficient space in the Player's WarehouseDepot
     * @return a Resource ArrayList
     */
    public ArrayList<LightResource> getThrewResources(){
        return game.getCurrentPlayer().getPlayerBoard().getWarehouseDepot().getThrewResources();
    }

    /**Activates from a Player's CardSlots last added DevelopmentCard the production of Resources. If succeed, Resources are added to Player's Strongbox
     * @param slot the number of slot in CardSlots where pop the DevelopmentCard from
     * @param lightChosenOutput the LightResource that can be produced if the @lightCard is an ExtraDepot instance. Method convert it in a Resource instance. null if Player won't use a LeaderCard in the action
     * @param lightCard the LightLeaderCard instances that can be optionally added to benefit of its leader ability. Method convert it in a LeaderCard instance. null if Player won't use a LeaderCard in the action
     * @throws InsufficientResourcesException if Player's Strongbox nor WarehouseDepot contain the entire Resource list to activate the production
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws InvalidActionException if the Player has already did an action in the current turn
     * @throws EmptySlotException if the slot number is referred to an empty in Player's CardSlots
     */
    public void devCardProduction(int slot, LightResource lightChosenOutput, LightLeaderCard lightCard) throws InsufficientResourcesException,
            UnusableCardException, InvalidActionException, EmptySlotException {
        LeaderCard card =  null;
        Resource chosenOutput = null;
        if(lightCard!=null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
            String s = gson.toJson(lightCard);
            card = gson.fromJson(s, LeaderCard.class);
            chosenOutput = Resource.valueOf(lightChosenOutput.toString());
        }

        game.devCardProduction(slot, chosenOutput, card);
    }

    /**Activate from the Player's PlayerBoard the production of Resources. If succeed, Resources are added to Player's Strongbox
     * @param input the Resource ArrayList used to make the production
     * @param output the Resource produced
     * @param lightCard the LightLeaderCard instances that can be optionally added to benefit of its leader ability. Method convert it in a LeaderCard instance. null if Player won't use a LeaderCard in the action
     * @param lightChosenOutput the LightResource that can be produced if the @lightCard is an ExtraDepot instance. Method convert it in a Resource instance. null if Player won't use a LeaderCard in the action
     * @throws InsufficientResourcesException if Player's Strongbox nor WarehouseDepot contain the entire Resource list to activate the production
     * @throws UnusableCardException if the LeaderCard can't be used (non-active Card or insufficient requirements to benefit of its leader ability)
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void defaultProduction(ArrayList<LightResource> input, LightResource output, LightLeaderCard lightCard, LightResource lightChosenOutput) throws InsufficientResourcesException,
            UnusableCardException, InvalidActionException {
        ArrayList<Resource> newInput = new ArrayList<>();
        input.forEach(e -> newInput.add(Resource.valueOf(e.toString())));
        Resource newOutput = Resource.valueOf(output.toString());

        LeaderCard card =  null;
        Resource chosenOutput = null;
        if(lightCard!=null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
            String s = gson.toJson(lightCard);
            card = gson.fromJson(s, LeaderCard.class);
            chosenOutput = Resource.valueOf(lightChosenOutput.toString());
        }

        game.defaultProduction(newInput, newOutput, card, chosenOutput);
    }

    /**Activate the LeaderCard ability until the end of the game
     * @param lightLeaderCard the LightLeaderCard instances that will be activated. Method convert it in a LeaderCard instance
     * @throws InsufficientRequirementsException if Player's CardSlots doesn't contains the DevelopmentCards required
     * @throws InsufficientResourcesException if Player's Strongbox nor WarehouseDepot contain the entire Resource list to activate the LeaderCard
     * @throws InputMismatchException if Game can't find the LeaderCard instance copy in the Player's LeaderSlot
     //* @throws UnusableCardException this will never been throw
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void activateLeaderCard(LightLeaderCard lightLeaderCard) throws InsufficientRequirementsException,
            InsufficientResourcesException, InputMismatchException, UnusableCardException, InvalidActionException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        LeaderCard card = gson.fromJson(gson.toJson(lightLeaderCard), LeaderCard.class);
        game.activateLeaderCard(card);
    }

    /**Throws a LeaderCard from the Player's CardSlots, who earns a FaithPoint
     * @param lightLeaderCard the LightLeaderCard instances that will be thrown. Method convert it in a LeaderCard instance
     * @throws InvalidActionException if the Player has already did an action in the current turn
     */
    public void throwLeaderCard(LightLeaderCard lightLeaderCard) throws InvalidActionException {
        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        LeaderCard card = gson.fromJson(gson.toJson(lightLeaderCard), LeaderCard.class);
        game.throwLeaderCard(card);
    }


    /**End the current Player's turn. Update the Player's Strongbox and rehabilitates the production from CardSlots' cards.
     * If the game is over, send the final rank to all the players if game was in multi player mode.
     * If a Player disconnect from the Server, then the turn is skipped to the next connected Player
     * If all Players are disconnected or the game is ended, the Lobby will be erased from the Server
     * @param username the Player's username that ends the turn
     */
    public synchronized void endTurn(String username) {
        EndTurnResponse response;
        Lobby currL = LobbyHandler.getInstance().getLobby(lobbyId);
        game.updateTurn();
        ArrayList<LightPlayer> players = new ArrayList<>();
        for(Player p: game.getPlayers())
            players.add(p.convert());

        if (game.isSinglePlayer()) {
            if(!game.isOver()) {
                response = new EndTurnResponse(username,
                        game.getLorenzo().convert(),
                        players,
                        game.getDevBoard().convert());
                //getViews().get(0).getVirtualView().sendEndTurnNotify(response);
            }else {
                response = new EndTurnResponse(username, game.endingSinglePlayerGame());
                if(currL != null){
                    LobbyHandler.getInstance().destroyLobby(currL);
                }
            }
            getViews().get(0).getVirtualView().sendEndTurnNotify(response);

        }
        else{
            //multiplayer
            if(game.lastTurnIsOver()){
                response = new EndTurnResponse(username, game.getRanking(), game.getWinner());
                EndTurnResponse finalResponse = response;
                getViews().forEach((element) -> element.getVirtualView().sendEndTurnNotify(finalResponse));
                if(currL != null){
                    LobbyHandler.getInstance().destroyLobby(currL);
                }
            }
            else {
                response = new EndTurnResponse(username,
                        game.getCurrentPlayer().getNickname(),
                        players);
                EndTurnResponse finalResponse1 = response;
                getViews().forEach((element) -> element.getVirtualView().sendEndTurnNotify(finalResponse1));
                while (disconnectedPlayer.contains(game.getCurrentPlayer().getNickname()) && !views.isEmpty()){
                    game.updateTurn();
                    //todo guardami se crasha
                    response = new EndTurnResponse(username,
                            game.getCurrentPlayer().getNickname(),
                            players);
                    EndTurnResponse finalResponse2 = response;
                    getViews().forEach((element) -> element.getVirtualView().sendEndTurnNotify(finalResponse2));
                }
            }
            if(views.isEmpty() && currL != null){
                LobbyHandler.getInstance().destroyLobby(currL);
            }
        }
    }

    public void handleError(String message){
        System.out.println(message);
        //todo metodo fuffa
    }

    /**Returns the Player by username
     * @param username the Player username
     * @return the Player instance
     * @throws NoSuchUsernameException if there's not a Player with such username
     */
    public Player getPlayer(String username) throws NoSuchUsernameException {
        return game.getPlayer(username);
    }

    /**Returns all the Players instances
     * @return a Player ArrayList
     */
    public ArrayList<Player> getPlayers(){
        return game.getPlayers();
    }

    /**Returns all the Players instances converted in LightPlayer version
     * @return a LightPlayer ArrayList
     */
    public ArrayList<LightPlayer> getLightPlayers(){
        ArrayList<LightPlayer> players = new ArrayList<>();
        game.getPlayers().forEach(e-> players.add(e.convert()));
        if(isSinglePlayer()){
            players.get(0).getPlayerBoard().getFaithTrack().setLorenzoPos(game.getLorenzo().getFaithBox().getPosition());
        }
        return players;
    }

    /**Returns all the VirtualClient instances
     * @return a VirtualClient ArrayList
     */
    public ArrayList<VirtualClient> getViews(){
        return views;
    }

    /**Returns the Lobby id
     * @return an int
     */
    public int getId() {
        return id;
    }

    /**Returns the Player whose own the turn at the moment
     * @return a Player instance
     */
    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    /**Returns the MarketBoard
     * @return a MarketBoard instance
     */
    public MarketBoard getMarketBoard(){
        return game.getMarketBoard();
    }

    /**Return the DevelopmentBoard
     * @return a DevelopmentBoard instance
     */
    public DevelopmentBoard getDevBoard(){
        return game.getDevBoard();
    }

    public Lorenzo getLorenzo(){
        return game.getLorenzo();
    }
//todo da rimuovere
    public void cheat(){
        game.cheat();
    }
    public void cheat2(){
        game.cheat2();
    }

}
