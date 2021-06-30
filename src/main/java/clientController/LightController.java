package clientController;

import clientModel.LightGame;
import clientModel.cards.LightCardSlots;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.singleplayer.LightLorenzo;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightFaithTrack;
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

public class LightController {
    private View view;
    private Client client;
    private LightGame game;
    private String username;
    private Gson gson;
    private int numOfPlayerInLobby;
    private ArrayList<String> usernamesList;

    public LightController(View view){
        this.view = view;
        this.game = new LightGame();
        gson = new GsonBuilder().registerTypeAdapter(LightLeaderCard.class, new LightLeaderCardDeserializer()).create();
        numOfPlayerInLobby = -1;
        usernamesList = new ArrayList<>();
    }

    //helper for gui

    public void setNumOfPlayerInLobby(int numOfPlayerInLobby) {
        this.numOfPlayerInLobby = numOfPlayerInLobby;
    }

    public int getNumOfPlayerInLobby() {
        return numOfPlayerInLobby;
    }

    public void setUsernamesList(ArrayList<String> usernamesList) {
        this.usernamesList = usernamesList;
    }

    public ArrayList<String> getUsernamesList() {
        return usernamesList;
    }

    //quitting app

    public void quittingApplication(){
        view.showSuccess("Quitting the game in 10 seconds");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            view.showError(e.getMessage());
        }
        System.exit(0);
    }

    public void instantQuittingApplication(){
        System.exit(0);
    }

    //RESILIENCE

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

    public void setUsername(String username) {
        boolean success = false;
        RegisterUsernameRequest registerUsernameRequest = new RegisterUsernameRequest(username);
        String a = gson.toJson(registerUsernameRequest);
        client.send(a);
        String responseS = null;
        try {
            responseS = client.recv();
            //view.showSuccess(username+" sent");
        } catch (IOException e) {
            // capiamo
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

    public String getUsername() {
        return username;
    }

    public void askLobbyMenu(){
        view.askMenu();
    }

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

    //V1 (t'appooo!!)
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

    public void waitStartGameString(){
        view.waitStartGameString();
    }

    public void notifyPlayerJoined(String username){
        view.notifyPlayerJoined(username);
    }

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


    public void startSinglePlayerGame() {
        startGameProcedure();
    }

    public void startMultiPlayerGame(){
        startGameProcedure();
    }

    private void startGameProcedure() {
        StartGameRequest startGameRequest = new StartGameRequest(getUsername());
        String request = gson.toJson(startGameRequest);
        client.send(request);
        try{
            String response = client.recv();
            StartGameResponse startGameResponse = gson.fromJson(response, StartGameResponse.class);
            startGameResponse.executeCommand(this);
        } catch (IOException e){
            //System.out.println("errore");
            view.showError("error ");
            //todo da abbellire
        }
    }

    public void sendSignalMultiPlayerGame(){
        StartMultiPlayerRequest request = new StartMultiPlayerRequest(username);
        client.send(gson.toJson(request));
        //MA DAI ERA QUA IL PROBLEMA VERAMENTE???
        //2 client.recv in contemporanea bastano a spaccare tutto??????
        //    :( :( :(
    }

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

    public void askLobbyId(ArrayList<Lobby> lobbies){
        view.askLobbyID(lobbies);
    }

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

    public void sendEndTurnRequest(){
        EndTurnRequest request = new EndTurnRequest(getUsername());
        client.send((gson.toJson(request)));
        try{
            String responseS = client.recv();
            EndTurnResponse response = gson.fromJson(responseS, EndTurnResponse.class);
            response.executeCommand(this);
            waitForMyTurn();
        }catch (IOException e){
            view.showError(e.getMessage());
        }
    }

    public void updateCardSlots(String username, LightCardSlots cardSlots){
        try {
            game.updateCardSlots(username, cardSlots.getCards());
            //view.updateCardSlots(username, );
        }catch (NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void updateDevBoard(LightDevelopmentBoard board) {
        game.updateDevBoard(board);
        view.updateDevBoard(board);
    }

    public void updateMarketBoard(LightMarketBoard market){
        game.updateMarketBoard(market);
        view.updateMarketBoard(market);
    }

    public void showThrewResources(String username, ArrayList<LightResource> threwResources){
        if(game.getUsername().equals(username))
            view.showThrewResources(threwResources);
    }

    public void showError(String username, String message) {
        if(getUsername().equals(username))
            view.showError(message);
    }

    public void showError(String message) {
            view.showError(message);
    }

    public void showSuccess(String username, String message){
        if(getUsername().equals(username))
            view.showSuccess(message);
    }

    public void showSuccess(String message){
        view.showSuccess(message);
    }


    public void updateStrongbox(String username, LightStrongbox strongbox){
        try {
            game.updateStrongbox(username, strongbox);
            view.updateStrongbox(username, strongbox);
        } catch(NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void updateFaithTrack(String username, LightFaithTrack newFaithTrack){
        try{
            game.updateFaithTrack(username, newFaithTrack);
        }catch(NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void updateLeaderSlot(String username, ArrayList<LightLeaderCard> cards){
        try{
            game.updateLeaderSlot(username, cards);
            view.updateLeaderSlot(username, cards);
        }catch(NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void chooseStartItems(ArrayList<LightLeaderCard> quartet, int numResources, boolean faithPoints){
        view.askStartItems(quartet, numResources, faithPoints);
    }

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

    public void start(){
        view.askTurn();
    }

    public LightPlayerBoard getPlayerBoard(){ return game.getPlayerBoard(); }

    public void setMarketBoard(LightMarketBoard market){
        this.game.setMarketBoard(market);
    }
    public LightMarketBoard getMarketBoard(){ return this.game.getMarketBoard(); }

    public void setDevBoard(LightDevelopmentBoard board){
        this.game.setDevBoard(board);
    }
    public LightDevelopmentBoard getDevBoard(){ return this.game.getDevBoard();}

    public void setPlayers(ArrayList<LightPlayer> players){
        this.game.setPlayers(players);
    }

    public void setPlayer(ArrayList<LightPlayer> players){
        for(LightPlayer p: players)
            if(p.getNickname().equals(this.username))
                game.setPlayer(p);
    }

    public void setPlayer(LightPlayer player){
        game.setPlayer(player);
    }

    public LightPlayer getPlayer(){
        return this.game.getPlayer();
    }

    public void reconnectSinglePlayer(){
        sendEndTurnRequest();
    }

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

    public void startTurn(){
        view.askTurn();
    }

    public void endSinglePlayerGame(String message){
        view.showSuccess(message);
        view.endGame();
    }

    public void endMultiPlayerGame(String winner, String rank){
        view.showRanking(winner, rank);
        view.endGame();
    }

    public void sendCheat(int ans){
        Gson gson = new Gson();
        CheatRequest request = new CheatRequest(getUsername(), ans);
        client.send(gson.toJson(request));
        try{
            String s = client.recv();
            CheatResponse response = gson.fromJson(s, CheatResponse.class);
            response.executeCommand(this);
        } catch(IOException e){
            view.showError(e.getMessage());
        }

    }

    public void showOthersActions(String username, String message){
        if(!game.getUsername().equals(username))
            view.showOthersActions(username+message);
    }
    public void updateLorenzo(LightLorenzo lorenzo){
        view.showLorenzoActions(lorenzo.actions(game.getPlayerBoard().getFaithTrack()));
    }

    public void updateBuyDevCard(String username, ArrayList<LightPlayer> players) {
        for (LightPlayer p : players) {
            if (p.getNickname().equals(username)) {
                String name = p.getNickname();
                try {
                    game.getPlayer(name).getPlayerBoard().setCardSlots(p.getPlayerBoard().getCardSlots());
                    game.getPlayer(name).getPlayerBoard().setWarehouse(p.getPlayerBoard().getWarehouseDepot());
                    game.getPlayer(name).getPlayerBoard().setStrongbox(p.getPlayerBoard().getStrongbox());
                } catch (NoSuchUsernameException e) {
                    view.showError("SÉ SPACCATO TUTTO");
                }
                view.updateCardSlots(p.getNickname(), p.getPlayerBoard().getCardSlots());
                view.updateWarehouseDepot(p.getNickname(), p.getPlayerBoard().getWarehouseDepot());
                view.updateStrongbox(p.getNickname(), p.getPlayerBoard().getStrongbox());
            }
        }
    }

    public void updateBuyResources(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            String name = p.getNickname();
            try {
                if(p.getNickname().equals(username))
                        game.getPlayer(name).getPlayerBoard().setWarehouse(p.getPlayerBoard().getWarehouseDepot());
                game.getPlayer(name).getPlayerBoard().setFaithTrack(p.getPlayerBoard().getFaithTrack());
            }catch (NoSuchUsernameException e) {
                view.showError("SÉ SPACCATO TUTTO");
            }
            view.updateWarehouseDepot(p.getNickname(), p.getPlayerBoard().getWarehouseDepot());
            view.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
        }
    }

    public void updateProduction(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            String name = p.getNickname();
            try {
                if(p.getNickname().equals(username)) {
                    game.getPlayer(name).getPlayerBoard().setWarehouse(p.getPlayerBoard().getWarehouseDepot());
                    game.getPlayer(name).getPlayerBoard().setStrongbox(p.getPlayerBoard().getStrongbox());
                    game.getPlayer(name).getPlayerBoard().setFaithTrack(p.getPlayerBoard().getFaithTrack());
                }
            }catch (NoSuchUsernameException e) {
                    view.showError("SÉ SPACCATO TUTTO");
            }
            view.updateWarehouseDepot(p.getNickname(), p.getPlayerBoard().getWarehouseDepot());
            view.updateStrongbox(p.getNickname(), p.getPlayerBoard().getStrongbox());
            view.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
        }
    }

    public void updateLeaderCardActivation(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            String name = p.getNickname();
            try {
                if(p.getNickname().equals(username)) {
                    game.updateLeaderSlot(p.getNickname(), p.getPlayerBoard().getLeaderSlot());
                }
            }catch (NoSuchUsernameException e) {
                view.showError("SÉ SPACCATO TUTTO");
            }
            view.updateLeaderSlot(p.getNickname(), p.getPlayerBoard().getLeaderSlot());
        }

    }

    public void updateLeaderCardThrow(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players){
            String name = p.getNickname();
            try {
                if(p.getNickname().equals(username)) {
                    game.updateLeaderSlot(p.getNickname(), p.getPlayerBoard().getLeaderSlot());
                    game.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
                }
            }catch (NoSuchUsernameException e) {
                view.showError("SÉ SPACCATO TUTTO");
            }
            view.updateLeaderSlot(p.getNickname(), p.getPlayerBoard().getLeaderSlot());
            view.updateFaithTrack(p.getNickname(), p.getPlayerBoard().getFaithTrack());
        }
    }




}

