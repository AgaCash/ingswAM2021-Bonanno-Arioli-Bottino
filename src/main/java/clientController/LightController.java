package clientController;

import clientModel.LightGame;
import clientModel.cards.LightDevelopmentCard;
import clientModel.cards.LightLeaderCard;
import clientModel.player.LightPlayer;
import clientModel.resources.LightResource;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import clientModel.table.LightPlayerBoard;
import clientModel.warehouse.LightWarehouseDepot;
import clientView.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.MessageNotSuccededException;
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
import java.net.UnknownHostException;
import java.util.ArrayList;

public class LightController {
    private View view;
    private Client client;
    private LightGame game;
    private String username;
    private Gson gson;

    public LightController(View view){
        this.view = view;
        this.game = new LightGame();
        gson = new GsonBuilder().registerTypeAdapter(LightLeaderCard.class, new LightLeaderCardDeserializer()).create();
    }

    //RESILIENCE

    public void reconnectToGame(){
        System.out.println("RICONNESSIONE AL GAME...");
        //TODO:
        //  CAMBIARE LA SCENA IN GAME
        //  RICARICARE LO STATO DEL GAME FINO A QUEL MOMENTO:
        //          NUOVA IDEA:::::::
        //                  SE SI DISCONNETTE NON POTRA' MAI ESSERE IL SUO TURNO, QUINDI
        //                  ASPETTERA' SEMPRE CHE TOCCHI DI NUOVO A LUI
        //
        //

        UpdateReconnectionRequest request = new UpdateReconnectionRequest(username);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            UpdateReconnectionResponse response = gson.fromJson(responseS, UpdateReconnectionResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
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
        view.quittingApplication();
    }

    //PING

    public void serverDisconnected(){
        view.serverLostConnection();
        //poi quit
        //in view o in controller?
    }

    public void sendGamePong(){
        PongGameMessage pongMessage = new PongGameMessage(username);
        client.send(gson.toJson(pongMessage));
    }

    public void sendLobbyPong(){
        PongLobbyMessage pongLobbyMessage = new PongLobbyMessage(username);
        client.send(gson.toJson(pongLobbyMessage));
    }


    public void connectToServer(String address, int port) throws UnknownHostException, IOException{
        client = new Client(address, port, this);
        client.connect();
    }

    public void setUsername(String username) throws MessageNotSuccededException {
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
        if(!success)
            throw new MessageNotSuccededException(response.getMessage());
        this.username = username;
        view.showSuccess(username+" registred");
        response.executeCommand(this);
    }

    public String getUsername() {
        return username;
    }

    public void askLobbyMenu(){
        view.askMenu();
    }

    /*
    TODO:
        client.recv():
            TOGLIERE I TRY CATCH DA QUA E METTERLI IN VIEW

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

    public void createMultiLobby(){
        CreateLobbyRequest request = new CreateLobbyRequest(username);
        String requestS = gson.toJson(request);
        client.send(requestS);
        String responseS;
        try {
            responseS = client.recv();
            //System.out.println(responseS);
            CreateLobbyResponse response = gson.fromJson(responseS, CreateLobbyResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            view.showError(e.getMessage());
        }
    }

    /*
    public void createLobbyWaiting(){
        view.notifyLobbyCreated();
        boolean startSignal = false;
        do{
            try {
                String someoneJoinedString = client.recv();
                //quando uno entra viene inviato a tutti lo stesso messaggio no?
                LoginMultiPlayerResponse response =
                        gson.fromJson(someoneJoinedString, LoginMultiPlayerResponse.class);
                response.executeCommand(this);
                view.notifyCreatorPlayerJoined();
            } catch (IOException e) {
                view.showError(e.getMessage());
            }
        }while (!startSignal);
    }
*/



    //RICORDA CHE 2 RECV IN CONTEMPORANEA FREGANO IL TUTTO

    //V1 (t'appooo!!)
    public void createLobbyWaiting(){
        new Thread(()->{
            boolean gameStarted = false;
            view.showWaitingRoom();
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
                e.printStackTrace();
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

    public void joinLobbyWaiting(){
        //mostra che è entrato nella lobby
        //notifica attesa che il gioco inizi
        boolean gameStarted = false;
        view.showWaitingRoom();
        try {
            do{
                String lobbyWaiting = client.recv();
                JsonObject jsonObject = gson.fromJson(lobbyWaiting, JsonObject.class);
                MessageType msgType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
                ((LobbyMessage) gson.fromJson(lobbyWaiting, msgType.getClassType())).executeCommand(this);
                if(gson.fromJson(lobbyWaiting, msgType.getClassType()).getMessageType() == MessageType.LOBBYSTARTGAME_RESPONSE){
                    gameStarted = true;
                }
            }while (!gameStarted);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startSinglePlayerGame() {
        view.switchToGame(true);
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

    public void startMultiPlayerGame(){
        view.switchToGame(false);
        //todo:
        // da qui sei tu teoo
    }

    public void sendSignalMultiPlayerGame(){
        StartMultiPlayerRequest request = new StartMultiPlayerRequest(username);
        client.send(gson.toJson(request));
        //MA DAI ERA QUA IL PROBLEMA VERAMENTE???
        //2 client.recv in contemporanea bastano a spaccare tutto??????
        //    :( :( :(
        /*
        try {
            String responseS = client.recv();
            StartMultiPlayerResponse response = gson.fromJson(responseS, StartMultiPlayerResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void getLobbyList(){
        GetLobbyRequest request = new GetLobbyRequest(username);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            GetLobbyResponse response = gson.fromJson(responseS, GetLobbyResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void sendDefaultProductionRequest (ArrayList<LightResource> input, LightResource output, LightLeaderCard card, LightResource chosenOutput){
        Gson gson = new Gson();
        DefaultProductionRequest request = new DefaultProductionRequest(game.getUsername(), input, output, card, chosenOutput);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            DefaultProductionResponse response = gson.fromJson(responseS, DefaultProductionResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendDevCardProductionRequest (int slot, LightResource chosenResource, LightLeaderCard card){
        Gson gson = new Gson();
        DevCardProductionRequest request = new DevCardProductionRequest(game.getUsername(), slot, chosenResource, card);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            DevCardProductionResponse response = gson.fromJson(responseS, DevCardProductionResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBuyResourceRequest (boolean line, int num, LightLeaderCard lightCard){
        Gson gson = new Gson();
        BuyResourcesRequest request = new BuyResourcesRequest(game.getUsername(), line, num, lightCard);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            BuyResourcesResponse response = gson.fromJson(responseS, BuyResourcesResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBuyDevCardRequest (int deck, int slot, LightLeaderCard card){
        Gson gson = new Gson();
        BuyDevCardRequest request = new BuyDevCardRequest(game.getUsername(), deck, slot, card);
        client.send(gson.toJson(request));
        try{
            String responseS = client.recv();
            BuyDevCardResponse response = gson.fromJson(responseS, BuyDevCardResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendLeaderCardActivationRequest (LightLeaderCard card){
        Gson gson = new Gson();
        //System.out.println("capiamo");
        LeaderCardActivationRequest request = new LeaderCardActivationRequest(getUsername(), card);
        client.send(gson.toJson(request));
        try {
            String responseS = client.recv();
            LeaderCardActivationResponse response = gson.fromJson(responseS, LeaderCardActivationResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEndTurnRequest(){
        Gson gson = new Gson();
        EndTurnRequest request = new EndTurnRequest(getUsername());
        client.send((gson.toJson(request)));
        try{
            String responseS = client.recv();
            EndTurnResponse response = gson.fromJson(responseS, EndTurnResponse.class);
            response.executeCommand(this);
            waitForMyTurn();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateCardSlots(String username, ArrayList<LightDevelopmentCard> cardSlots){
        Gson gson = new Gson();
        //todo assolutamente
        try {
            game.updateCardSlots(username, cardSlots);
        }catch (NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void updateDevBoard(LightDevelopmentBoard board){
        game.updateDevBoard(board);

    }

    public void updateWarehouse(String username, LightWarehouseDepot warehouseDepot){
        Gson gson = new Gson();
        String s = gson.toJson(warehouseDepot);
        LightWarehouseDepot newWarehouse = gson.fromJson(s, LightWarehouseDepot.class);
        try {
            game.updateWarehouse(username, newWarehouse);
        } catch(NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void updateMarketBoard(LightMarketBoard market){
        Gson gson = new Gson();
        String s = gson.toJson(market);
        LightMarketBoard newMarket = gson.fromJson(s, LightMarketBoard.class);
        game.updateMarketBoard(newMarket);
    }

    public void showThrewResources(String username, ArrayList<LightResource> threwResources){
        if(game.getUsername().equals(username))
            view.showThrewResources(threwResources);
    }

    public void showError(String username, String message) {
        view.showError(message);
    }

    public void showSuccess(String message){
        view.showSuccess(message);
    }


    public void updateStrongbox(String username, LightStrongbox strongbox){
        try {
            game.updateStrongbox(username, strongbox);
        } catch(NoSuchUsernameException e){
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

    public void setPlayer(String username, ArrayList<LightPlayer> players){
        for(LightPlayer p: players)
            if(p.getNickname().equals(this.username))
                game.setPlayer(p);
    }

    public LightPlayer getPlayer(){
        return this.game.getPlayer();
    }

    public void waitForMyTurn(){
        //todo:
        // waitForMyTurn è il metodo che ascolta gli update degli altri giocatori e vede quando è il suo turno
        //  singleplayer: get state and ask again things
        System.out.println("Others are playing, waiting for your turn starts");
        boolean myTurn = false;
        do{
            try {
                String responseS = client.recv();
                MessageType msgType = MessageType.valueOf(
                        gson.fromJson(responseS, JsonObject.class).get("messageType").getAsString());
                ((GameMessage) gson.fromJson(responseS, msgType.getClassType())).executeCommand(this);
                if(true/* todo: DAL MESSAGGIO CAPISCO CHE TOCCA A ME*/){
                    myTurn = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(!myTurn);
        view.askTurn();

    }

    public void startTurn(){
        view.askTurn();
    }

}

