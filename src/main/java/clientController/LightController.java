package clientController;

import clientModel.LightGame;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.singleplayer.LightLorenzo;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import clientModel.table.LightPlayerBoard;
import clientView.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.NoSuchUsernameException;
import network.client.Client;
import network.messages.MessageType;
import network.messages.gameMessages.*;
import network.messages.lobbyMessages.*;
import network.messages.pingMessages.PongGameMessage;
import network.messages.pingMessages.PongLobbyMessage;
import network.server.Lobby;
import utilities.LightLeaderCardDeserializer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * LightController is the part of Controller in the Client that handle the LightModel updates, the
 * Messages sending and receiving and the call to View's method
 */
public class LightController {
    private View view;
    private Client client;
    private LightGame game;
    private String username;
    private Gson gson;
    private int numOfPlayerInLobby;
    private ArrayList<String> usernamesList;

    /**Constructor
     * @param view a Class implementing View interface
     */
    public LightController(View view){
        this.view = view;
        this.game = new LightGame();
        gson = new GsonBuilder().registerTypeAdapter(LightLeaderCard.class, new LightLeaderCardDeserializer()).create();
        numOfPlayerInLobby = -1;
        usernamesList = new ArrayList<>();
    }

    /**helper for gui: Returns true if Game is in single player mode
     * @return a boolean
     */
    public boolean isSinglePlayer(){
        return usernamesList.size()==1;
    }

    /**helper for gui: Sets the number of Player's in Lobby
     * @param numOfPlayerInLobby an int
     */
    public synchronized void setNumOfPlayerInLobby(int numOfPlayerInLobby) {
        this.numOfPlayerInLobby = numOfPlayerInLobby;
    }

    /**helper for gui: Gets the number of Player's in Lobby
     */
    public synchronized int getNumOfPlayerInLobby() {
        return numOfPlayerInLobby;
    }

    /**helper for gui: Sets a Player's username List
     * @param usernamesList a String ArrayList
     */
    public synchronized void setUsernamesList(ArrayList<String> usernamesList) {
        this.usernamesList = usernamesList;
    }

    /**helper for gui: Gets a Player's username List
     * @return a String ArrayList
     */
    public synchronized ArrayList<String> getUsernamesList() {
        return usernamesList;
    }

    /**Method called to quit the application at the end of the Game to quit all the process
     *It's last 5 seconds before the System exit
     */
    public void quittingApplication(){
        view.showSuccess("Quitting the game in 5 seconds");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            view.showError(e.getMessage());
        }
        System.exit(0);
    }

    /**Method called to quit the application at the end of the Game to quit all the process
     *It instantly exit the System
     */
    public void instantQuittingApplication(){
        System.exit(0);
    }

    //RESILIENCE
    //todo aga divertiti da qua
    public void reconnectToGame(){
        view.showReconnectionToGame();
        UpdateReconnectionRequest request = new UpdateReconnectionRequest(username);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            UpdateReconnectionResponse response = gson.fromJson(responseS, UpdateReconnectionResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    public void notifyPlayerDisconnected(String username){
        view.notifyPlayerDisconnected(username);
    }

    public void notifyPlayerReconnected(String username){
        view.notifyPlayerReconnected(username);
    }

    public void notifyCreatorDisconnected(){
        view.notifyCreatorDisconnected();
        quittingApplication();
    }

    //PING

    public void serverDisconnected(){
        view.serverLostConnection();
    }

    public void sendGamePong(){
        PongGameMessage pongMessage = new PongGameMessage(username);
        client.send(gson.toJson(pongMessage));
    }

    public void sendLobbyPong(){
        PongLobbyMessage pongLobbyMessage = new PongLobbyMessage(username);
        client.send(gson.toJson(pongLobbyMessage));
    }


    public void connectToServer(String address, int port){
        new Thread(()->{
            try{
                client = new Client(address, port, this);
                client.connect();
                view.askUsername();
            }catch (IOException e){
                view.showError(e.getMessage());
                view.askServerInfo();
            }
        }).start();
    }
    //todo a qua

    /**Sets the username received by User (this method it's called by View) and stores it until a Player registration in the Lobby
     * Sends a RegisterUsernameRequest to Lobby in the Server and execute the RegisterUsernameResponse
     * @param username the username chosen by User in the View
     */
    public void setUsername(String username) {
        boolean success = false;
        RegisterUsernameRequest registerUsernameRequest = new RegisterUsernameRequest(username);
        String a = gson.toJson(registerUsernameRequest);
        client.send(a);
        String responseS = null;
        try {
            responseS = client.recv();
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
        RegisterUsernameResponse response = gson.fromJson(responseS, RegisterUsernameResponse.class);
        success = response.isSuccess();
        if(success){
            this.username = username;
            view.showSuccess(username+" registered");
            response.executeCommand(this);
        }
        else{
            view.showError(response.getMessage());
            view.askUsername();
        }
    }

    /**Returns the Client Player username
     * @return a String
     */
    public String getUsername() {
        return username;
    }

    /**Calls the askMenu method in View
     * that permit User to choose the Game modality
     */
    public void askLobbyMenu(){
        view.askMenu();
    }

    /**Creates a SinglePlayer Lobby to start a aSingle player game
     * Sends a StartSinglePlayerRequest and runs the StartSinglePlayerResponse
     */
    public void createSinglePlayerLobby(){
        this.game = new LightGame();
        StartSinglePlayerRequest request = new StartSinglePlayerRequest(username);
        String requestS = gson.toJson(request);
        client.send(requestS);
        String responseS = null;
        try {
            responseS = client.recv();
            StartSinglePlayerResponse response = gson.fromJson(responseS, StartSinglePlayerResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    /**Creates a MultiPlayer Lobby to start a Multi Player Lobby
     * Sends a CreateLobbyRequest and runs the CreateLobbyResponse
     */
    public void createMultiLobby(){
        CreateLobbyRequest request = new CreateLobbyRequest(username);
        String requestS = gson.toJson(request);
        client.send(requestS);
        String responseS;
        try {
            responseS = client.recv();
            CreateLobbyResponse response = gson.fromJson(responseS, CreateLobbyResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    //RICORDA CHE 2 RECV IN CONTEMPORANEA FREGANO IL TUTTO

    //V1 (t'appooo!!)todo tutto tuo agaaaaaa
    public void createLobbyWaiting(){
        view.showCreatorWaitingRoom();
        new Thread(()->{
            boolean gameStarted = false;
            try {
                do{
                    String lobbyWaiting = client.recv();
                    JsonObject jsonObject = gson.fromJson(lobbyWaiting, JsonObject.class);
                    MessageType msgType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
                    ((LobbyMessage) gson.fromJson(lobbyWaiting, msgType.getClassType())).executeCommand(this);
                    if(msgType == MessageType.LOBBYSTARTGAME_RESPONSE){
                        StartMultiPlayerResponse m = gson.fromJson(lobbyWaiting, StartMultiPlayerResponse.class);
                        if(m.isSuccess()){
                            gameStarted = true;
                        }
                    }
                }while (!gameStarted);
            } catch (IOException e) {
                view.showError(e.getMessage());
            }
        }).start();
        view.askStartGame();
    }
    //todo aga
    public void waitStartGameString(){
        view.waitStartGameString();
    }

    /**Prints in the Lobby Menu that a Player joined the Lobby in the server
     * @param username the Player's username who just joined
     */
    public void notifyPlayerJoined(String username){
        view.notifyPlayerJoined(username);
    }
//TODO AGA
    public void joinLobbyWaiting(ArrayList<String> usernameList){
        //mostra che è entrato nella lobby
        //notifica attesa che il gioco inizi
        view.showWaitingRoom(usernameList);
        new Thread(()-> {
            boolean gameStarted = false;
            try {
                do {
                    String lobbyWaiting = client.recv();
                    JsonObject jsonObject = gson.fromJson(lobbyWaiting, JsonObject.class);
                    MessageType msgType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
                    ((LobbyMessage) gson.fromJson(lobbyWaiting, msgType.getClassType())).executeCommand(this);
                    if (gson.fromJson(lobbyWaiting, msgType.getClassType()).getMessageType() == MessageType.LOBBYSTARTGAME_RESPONSE) {
                        gameStarted = true;
                    }
                } while (!gameStarted);
            } catch (IOException e) {
                view.showError(e.getMessage());
            }
        }).start();

    }


    /**
     * Calls startGameProcedure method
     */
    public void startSinglePlayerGame() {
        startGameProcedure();
    }

    /**
     * Calls startGameProcedure method
     */
    public void startMultiPlayerGame(){
        startGameProcedure();
    }


    /**Sends a StartGameRequest and runs the StartGameResponse that contains the initial 4 LightLeaderCard
     * and eventually the free choice LightResource
     */
    private void startGameProcedure() {
        StartGameRequest startGameRequest = new StartGameRequest(getUsername());
        String request = gson.toJson(startGameRequest);
        client.send(request);
        try{
            String response = client.recv();
            StartGameResponse startGameResponse = gson.fromJson(response, StartGameResponse.class);
            startGameResponse.executeCommand(this);
        } catch (IOException e){
            view.showError("INTERNAL ERROR");
        }
    }
//TODO AGA DIVERTITI CIAO BELLO
    public void sendSignalMultiPlayerGame(){
        StartMultiPlayerRequest request = new StartMultiPlayerRequest(username);
        client.send(gson.toJson(request));
        //MA DAI ERA QUA IL PROBLEMA VERAMENTE???
        //2 client.recv in contemporanea bastano a spaccare tutto??????
        //    :( :( :(
    }

    /**Sends a GetLobbyRequest to Server and runs the GetLobbyResponse with the List of available Lobbies
     * in the Server with relatives owner
     *
     */
    public void getLobbyList(){
        GetLobbyRequest request = new GetLobbyRequest(username);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            GetLobbyResponse response = gson.fromJson(responseS, GetLobbyResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }

    }

    /**Calls the View method to receive the Lobby id chosen by User
     * @param lobbies the Lobby ArrayList (copied by server)
     */
    public void askLobbyId(ArrayList<Lobby> lobbies){
        view.askLobbyID(lobbies);
    }

    /**Sends a LoginMultiPlayerRequest to make join the Player to a Lobby and runs the LoginMultiPlayerResponse
     * that notifies all the other Players its join to the Lobby
     * @param id the Lobby ID
     */
    public void joinLobbyById(int id){
        LoginMultiPlayerRequest request = new LoginMultiPlayerRequest(username, id);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            LoginMultiPlayerResponse response = gson.fromJson(responseS, LoginMultiPlayerResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    /**Sends a DefaultProductionRequest and runs the DefaultProductionResponse
     * @param input the LightResource ArrayList that User has chosen as production input
     * @param output the LightResource that User has chosen as production output
     * @param card the LightLeaderCard User has chosen to add to the production. null if User didn't want to benefit of a LeaderAction
     * @param chosenOutput the free choice LightResource that user has chosen as extra production output. null if User didn't add a LightLeaderCard
     */
    public void sendDefaultProductionRequest (ArrayList<LightResource> input, LightResource output, LightLeaderCard card, LightResource chosenOutput){
        DefaultProductionRequest request = new DefaultProductionRequest(game.getUsername(), input, output, card, chosenOutput);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            DefaultProductionResponse response = gson.fromJson(responseS, DefaultProductionResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }

    }

    /**Sends a DevCardProductionRequest and runs the DevCardProductionResponse
     * @param slot the CardSlot number where to take from the DevelopmentCard
     * @param chosenResource the free choice LightResource that user chosen as extra production output. null if User didn't add a LightLeaderCard
     * @param card the LightLeaderCard User chosen to add to the production. null if User didn't want to benefit of a LeaderAction
     */
    public void sendDevCardProductionRequest (int slot, LightResource chosenResource, LightLeaderCard card){
        DevCardProductionRequest request = new DevCardProductionRequest(game.getUsername(), slot, chosenResource, card);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            DevCardProductionResponse response = gson.fromJson(responseS, DevCardProductionResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    /**Sends a BuyResourceRequest and runs the BuyResourceRequest
     * @param line true if User has chosen a Market line, false if User has chosen a Market column
     * @param num the line/column number
     * @param lightCard the LightLeaderCard User chosen to add to the production. null if User didn't want to benefit of a LeaderAction
     */
    public void sendBuyResourceRequest (boolean line, int num, LightLeaderCard lightCard){
        BuyResourcesRequest request = new BuyResourcesRequest(game.getUsername(), line, num, lightCard);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            BuyResourcesResponse response = gson.fromJson(responseS, BuyResourcesResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    /**Sends a BuyDevCardRequest and runs the BuyDevCardResponse
     * @param deck the deck's number in DevelopmentBoard
     * @param slot the Slot number in CardSlots where put the DevelopmentCard purchased
     * @param card the LightLeaderCard User chosen to add to the production. null if User didn't want to benefit of a LeaderAction
     */
    public void sendBuyDevCardRequest (int deck, int slot, LightLeaderCard card){
        BuyDevCardRequest request = new BuyDevCardRequest(game.getUsername(), deck, slot, card);
        client.send(gson.toJson(request));
        try{
            String responseS = client.recv();
            BuyDevCardResponse response = gson.fromJson(responseS, BuyDevCardResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());

        }
    }

    /**Sends a LeaderCardActivationRequest and runs a LeaderCardActivationResponse
     * @param card a LightLeaderCard copy of the LeaderCard in server will be activated
     */
    public void sendLeaderCardActivationRequest (LightLeaderCard card){
        LeaderCardActivationRequest request = new LeaderCardActivationRequest(getUsername(), card);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            LeaderCardActivationResponse response = gson.fromJson(responseS, LeaderCardActivationResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    /**Sends a LeaderCardThrownResponse and runs a LeaderCardThrownResponse
     * @param card a LightLeaderCard copy of the LeaderCard in server will be thrown
     */
    public void sendLeaderCardThrowRequest (LightLeaderCard card){
        LeaderCardThrowRequest request = new LeaderCardThrowRequest(getUsername(), card);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            LeaderCardThrowResponse response = gson.fromJson(responseS, LeaderCardThrowResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    /**Sends a EndTurnRequest and runs a EndTurnResponse. If it's in MultiPlayer Game modality,
     * puts the LightController in wait (see waitForMyTurn). If it's in SinglePlayer Game modality
     * runs the executeCommand that updates LightModel with the Lorenzo's last actions.
     */
    public void sendEndTurnRequest(){
        EndTurnRequest request = new EndTurnRequest(getUsername());
        client.send((gson.toJson(request)));
        try{
            String responseS = client.recv();
            EndTurnResponse response = gson.fromJson(responseS, EndTurnResponse.class);
            response.executeCommand(this);
            if(!this.isSinglePlayer()){
                waitForMyTurn();
            }
        }catch (IOException e){
            view.showError(e.getMessage());
        }
    }

    /**Updates the LightModel's LightDevelopmentBoard with the current Model's DevelopmentBoard status
     * @param board a LightDevelopmentBoard instance
     */
    public void updateDevBoard(LightDevelopmentBoard board) {
        game.updateDevBoard(board);
        view.updateDevBoard(board);
    }

    /**Updates the LightModel's LightMarketBoard with the current Model's MarketBoard status
     * @param market a LightDevelopmentBoard instance
     */
    public void updateMarketBoard(LightMarketBoard market){
        game.updateMarketBoard(market);
        view.updateMarketBoard(market);
    }

    /**Calls the view method to show the Resource list that were thrown by WarehouseDepot in Model
     * @param username the Player's username who purchased the Resources
     * @param threwResources a LightResourceArrayList
     */
    public void showThrewResources(String username, ArrayList<LightResource> threwResources){
        if(game.getUsername().equals(username))
            view.showThrewResources(threwResources);
    }

    /**Prints an error message
     * @param username the Player's username who message is directed to
     * @param message the String containing the message
     */
    public void showError(String username, String message) {
        if(getUsername().equals(username))
            view.showError(message);
    }

    /**Prints an error message
     * @param message the String containing the message
     */
    public void showError(String message) {
            view.showError(message);
    }

    /**Prints a success message
     * @param username the Player's username who message is directed to
     * @param message the String containing the message
     */
    public void showSuccess(String username, String message){
        if(getUsername().equals(username))
            view.showSuccess(message);
    }

    /**Prints an error message
     * @param message the String containing the message
     */
    public void showSuccess(String message){
        view.showSuccess(message);
    }

    /**Shows the 4 LeaderCard that Player has to choose and eventually the free choice Resource and the notify that
     * Player earned a FaithPoint
     * @param quartet a 4-length LightLeaderCard ArrayList
     * @param numResources the number of free choice LightResources
     * @param faithPoints true if Player earned a FaithPoint, false if not
     */
    public void chooseStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints){
        view.askStartItems(quartet, numResources, faithPoints);
    }

    /**Sends a SetupRequest containing the user chosen Resources and LightLeaderCards before the game's starting
     * and runs the SetupResponse commands
     * @param couple a length 2 LightLeaderCard ArrayList
     * @param chosenResources a 0-to-2 length LightResource ArrayList
     * @param faithPoint a boolean that notifies User if has gained a FaithPoint
     */
    public void sendStartItems(ArrayList<LightLeaderCard> couple, ArrayList<LightResource> chosenResources, boolean faithPoint){
        Gson gson = new Gson();
        SetupRequest request = new SetupRequest(getUsername(), couple, chosenResources, faithPoint);
        client.send(gson.toJson(request));
        try{
            gson = new GsonBuilder().registerTypeAdapter(LightLeaderCard.class, new LightLeaderCardDeserializer()).create();
            String s = client.recv();
            SetupResponse response = gson.fromJson(s, SetupResponse.class);
            response.executeCommand(this);
        } catch(IOException e){
            view.showError(e.getMessage());
        }

    }

    //todo aga
    public LightPlayer getPlayerFull(String username){
        try {
            return game.getPlayer(username);
        } catch (NoSuchUsernameException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**Returns the Player's PlayerBoard
     * @return a LightPlayerBoard instance
     */
    public LightPlayerBoard getPlayerBoard(){ return game.getPlayerBoard(); }

    /**First LightModel's LightMarketBoard update
     * @param market a LightMarketBoard instance
     */
    public void setMarketBoard(LightMarketBoard market){
        this.game.setMarketBoard(market);
    }

    /**Returns the current LightModel's LightMarketBoard status
     * @return a LightMarketBoard instance
     */
    public LightMarketBoard getMarketBoard(){ return this.game.getMarketBoard(); }

    /**First LightModel's LightDevelopmentBoard update
     * @param board a LightDevelopmentBoard instance
     */
    public void setDevBoard(LightDevelopmentBoard board){
        this.game.setDevBoard(board);
    }

    /**Returns the current LightModel's LightDevelopmentBoard status
     * @return a LightDevelopmentBoard instance
     */
    public LightDevelopmentBoard getDevBoard(){ return this.game.getDevBoard();}

    /**First LightModel's LightPlayers setup
     * @param players a LightPlayer ArrayList
     */
    public void setPlayers(ArrayList<LightPlayer> players){
        this.game.setPlayers(getUsername(), players);
    }

    /**First LightModel's User LightPlayer setup
     * @param player a LightPlayer ArrayList
     */
    public void setPlayer(LightPlayer player){
        game.setPlayer(player);
    }

    //todo aga
    public void reconnectSinglePlayer(){
        sendEndTurnRequest();
    }

    /**todo agatinoooooo
     *
     */
    public void waitForMyTurn(){
        view.waitingForMyTurn();
        new Thread(()->{
            boolean myTurn = false;
            do{
                try {
                    String responseS = client.recv();
                    MessageType msgType = MessageType.valueOf(
                            gson.fromJson(responseS, JsonObject.class).get("messageType").getAsString());
                    if(msgType == MessageType.ENDTURNUPDATE && (gson.fromJson(responseS, EndTurnResponse.class).getNewPlayerName()).equals(username)){
                        myTurn = true;
                    }
                    ((GameMessage) gson.fromJson(responseS, msgType.getClassType())).executeCommand(this);
                } catch (IOException e) {
                    view.showError(e.getMessage());
                }
            }while(!myTurn);
        }).start();
    }

    /**Calls the View method to print the potential turn's actions and make the User to choose them
     */
    public void startTurn(){
        view.askTurn();
    }

    /**Sends to View the final message in single player mode
     * @param message a String containing the message if Player won or Lorenzo did
     */
    public void endSinglePlayerGame(String message){
        view.endSinglePlayerGame(message);
    }

    /**Sends to View the final rank amd the winner Player's username
     * @param winner the winner Player's username
     * @param rank a String containing the final rank
     */
    public void endMultiPlayerGame(String winner, String rank){
        view.showRanking(winner, rank);
        //view.endGame();
    }

    /**Shows to View the actions that Lorenzo did in its turn
     * @param lorenzo a LightLorenzo instance
     */
    public void updateLorenzo(LightLorenzo lorenzo){
        view.showLorenzoActions(lorenzo);
    }

    /**Updates LightModel and notifies View after a successful BuyDevCardRequest
     * @param username the Player who purchased a DevelopmentCard
     * @param players the entire list of LightPlayers
     */
    public void updateBuyDevCard(String username, ArrayList<LightPlayer> players) {
        for (LightPlayer p : players) {
            if (p.getNickname().equals(username)) {
                try {
                    game.updateCardSlots(p.getNickname(), p.getPlayerBoard().getCardSlots());
                    game.updateWarehouse(p.getNickname(), p.getPlayerBoard().getWarehouseDepot());
                    game.updateStrongbox(p.getNickname(), p.getPlayerBoard().getStrongbox());
                } catch (NoSuchUsernameException e) {
                    view.showError("INTERNAL ERROR");
                }
            }
        }
        try{
            view.updateWarehouseDepot(username, game.getPlayer(username).getPlayerBoard().getWarehouseDepot());
            view.updateStrongbox(username, game.getPlayer(username).getPlayerBoard().getStrongbox());
            view.updateCardSlots(username, game.getPlayer(username).getPlayerBoard().getCardSlots());
        } catch (NoSuchUsernameException e) {
            view.showError("INTERNAL ERROR");
        }
    }

    /**Updates LightModel and notifies View after a successful BuyResourceRequest
     * @param username the Player who purchased the Resources
     * @param players theentire list of LightPlayers
     */
    public void updateBuyResources(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            try {
                if(p.getNickname().equals(username))
                    game.updateWarehouse(p.getNickname(), p.getPlayerBoard().getWarehouseDepot());
                game.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
                view.updateFaithTrack(p.getNickname(), game.getPlayer(p.getNickname()).getPlayerBoard().getFaithTrack());
            }catch (NoSuchUsernameException e) {
                view.showError("INTERNAL ERROR");
            }
        }
        try{
            view.updateWarehouseDepot(username, game.getPlayer(username).getPlayerBoard().getWarehouseDepot());
        } catch (NoSuchUsernameException e) {
            view.showError("INTERNAL ERROR");
        }
    }

    /**Updates LightModel and notifies View after a successful DevCardProductionRequest or a DefaultProductionRequest
     * @param username the Player who activated a production
     * @param players the entire list of LightPlayers
     */
    public void updateProduction(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            try {
                if(p.getNickname().equals(username)) {
                    game.updateWarehouse(p.getNickname(), p.getPlayerBoard().getWarehouseDepot());
                    game.updateStrongbox(p.getNickname(), p.getPlayerBoard().getStrongbox());
                    game.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
                }
            }catch (NoSuchUsernameException e) {
                    view.showError("INTERNAL ERROR");
            }
        }
        try{
            view.updateFaithTrack(username, game.getPlayer(username).getPlayerBoard().getFaithTrack());
            view.updateWarehouseDepot(username, game.getPlayer(username).getPlayerBoard().getWarehouseDepot());
            view.updateStrongbox(username, game.getPlayer(username).getPlayerBoard().getStrongbox());
        } catch (NoSuchUsernameException e) {
            view.showError("INTERNAL ERROR");
        }
    }

    /**Updates LightModel and notifies View after a successful LeaderCardActivationRequest
     * @param username the Player who activated a LeaderCard username
     * @param players the entire list of LightPlayers
     */
    public void updateLeaderCardActivation(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            try {
                if(p.getNickname().equals(username)) {
                    game.updateLeaderSlot(p.getNickname(), p.getPlayerBoard().getLeaderSlot());
                }
            }catch (NoSuchUsernameException e) {
                view.showError("INTERNAL ERROR");
            }
        }
        try{
            view.updateLeaderSlot(username, game.getPlayer(username).getPlayerBoard().getLeaderSlot());
        } catch (NoSuchUsernameException e) {
            view.showError("INTERNAL ERROR");
        }

    }

    /**Updates LightModel and notifies View after a successful LeaderCardThrowRequest
     * @param username the Player who thrown a LeaderCard username
     * @param players the entire list of LightPlayers
     */
    public void updateLeaderCardThrow(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            try {
                if(p.getNickname().equals(username)) {
                    game.updateLeaderSlot(p.getNickname(), p.getPlayerBoard().getLeaderSlot());
                    game.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
                }
            }catch (NoSuchUsernameException e) {
                view.showError("INTERNAL ERROR");
            }
        }
        try{
            view.updateLeaderSlot(username, game.getPlayer(username).getPlayerBoard().getLeaderSlot());
            view.updateFaithTrack(username, game.getPlayer(username).getPlayerBoard().getFaithTrack());
        } catch (NoSuchUsernameException e) {
            view.showError("INTERNAL ERROR");
        }
    }

    /**Updates LightModel and notifies View after a successful EndTurnRequest
     * @param username the Player's username
     * @param cpu the LightLorenzo instance
     * @param player the LightPlayer copy of Player
     * @param board the new LightDevelopmentBoard
     */
    public void updateSinglePlayerEndTurn(String username, LightLorenzo cpu, LightPlayer player, LightDevelopmentBoard board){
        try {
            game.updateStrongbox(username, player.getPlayerBoard().getStrongbox());
            game.updateFaithTrack(username, player.getPlayerBoard().getFaithTrack());
            game.getPlayer().getPlayerBoard().getFaithTrack().setLorenzoPos(cpu.getPosition());
        }catch (NoSuchUsernameException e) {
            //IT NEVER HAPPENS !!!!!!!!!!!!!!!!!
            view.showError(e.getMessage());
        }
        game.updateDevBoard(board);
        updateLorenzo(cpu);
        view.updateStrongbox(username, game.getPlayerBoard().getStrongbox());
        view.updateFaithTrack(username, game.getPlayerBoard().getFaithTrack());
        view.updateDevBoard(game.getDevBoard());
        startTurn();
    }

    /**Updates LightModel and notifies View after a successful EndTurnRequest
     * @param username the Player who ended the turn username
     * @param players the entire list of Players
     * @param newPlayer the starting turn Player username
     */
    public void updateMultiPlayerEndTurn(String username, ArrayList<LightPlayer> players, String newPlayer){
        for(LightPlayer p: players) {
            if (p.getNickname().equals(username)) {
                try {
                    game.updateStrongbox(username, p.getPlayerBoard().getStrongbox());
                } catch (NoSuchUsernameException e) {
                    view.showError(e.getMessage());
                }
            }
        }
        try {
            view.updateStrongbox(username, game.getPlayer(username).getPlayerBoard().getStrongbox());
        }catch(NoSuchUsernameException e){
            view.showError(e.getMessage());
        }

        if(game.getUsername().equals(newPlayer)) {
            view.showSuccess("IT'S YOUR TURN!");
            startTurn();
        }
        else {
            view.showOthersActions(username + " has ended turn. " + newPlayer + " has started turn");
        }

    }
}

