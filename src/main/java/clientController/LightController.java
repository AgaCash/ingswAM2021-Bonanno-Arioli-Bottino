package clientController;

import clientModel.LightGame;
import clientModel.cards.LightCardSlots;
import clientModel.cards.LightWhiteConverter;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightDevelopmentBoard;
import clientModel.table.LightMarketBoard;
import clientModel.warehouse.LightWarehouseDepot;
import clientView.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.MessageNotSuccededException;
import exceptions.NoSuchUsernameException;
import model.cards.*;
import model.resources.Resource;
import model.strongbox.Strongbox;
import model.table.DevelopmentBoard;
import model.table.MarketBoard;
import model.utilities.LeaderCardDeserializer;
import model.warehouse.WarehouseDepot;
import network.client.Client;
import network.messages.MessageType;
import network.messages.gameMessages.*;
import network.messages.lobbyMessages.*;
import network.messages.pingMessages.PingGameMessage;
import network.messages.pingMessages.PingLobbyMessage;
import network.messages.pingMessages.PongGameMessage;
import network.messages.pingMessages.PongLobbyMessage;
import network.server.Lobby;

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
        gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
    }


    public void serverDisconnected(){
        System.out.println("LOST SERVER CONNECTION");
        //view.serverLostConnection();
        //poi quit?
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
        String b = null;
        try {
            b = client.recv();
            //view.showSuccess(username+" sent");
        } catch (IOException e) {
            // capiamo
            view.showError(e.getMessage());
        }
        RegisterUsernameResponse response = gson.fromJson(b, RegisterUsernameResponse.class);
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

    client.recv():
            TOGLIERE I TRY CATCH DA QUA E METTERLI IN VIEW

     */

    public void createSinglePlayerLobby(){
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

    //TODO:
    //  Check if this method won't create problems
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
                    if(gson.fromJson(lobbyWaiting, msgType.getClassType()).getMessageType() == MessageType.LOBBYSTARTGAME_RESPONSE){
                        gameStarted = true;
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
            System.out.println("errore");
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
        try {
            String responseS = client.recv();
            StartMultiPlayerResponse response = gson.fromJson(responseS, StartMultiPlayerResponse.class);
            response.executeCommand(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void activateLeaderCard(int numcard){
        //wait 4 later
    }

    public void throwLeaderCard(){}


    public void buyResources(boolean line, int num){
        sendBuyResourceRequest(line, num, null);
        //todo aggiungere leadercards
    }

    public void buyDevCards(int deckNum, int slotNum){
        sendBuyDevCardRequest(deckNum, slotNum, null);
        //todo leadercards
    }

    public void devCardProduction(int slotNum){
        sendDevCardProductionRequest(slotNum, null, null);
        //todo leadercards
    }
    public void sendDefaultProductionRequest (ArrayList<Resource> input, Resource output, ExtraProd card, Resource chosenOutput){
        Gson gson = new Gson();
        DefaultProductionRequest request = new DefaultProductionRequest(game.getUsername(), input, output, card, chosenOutput);
        client.send(gson.toJson(request));

    }

    public void sendDevCardProductionRequest (int slot, Resource chosenResource, ExtraProd card){
        Gson gson = new Gson();
        DevCardProductionRequest request = new DevCardProductionRequest(game.getUsername(), slot, chosenResource, card);
        client.send(gson.toJson(request));
    }

    public void sendBuyResourceRequest (boolean line, int num, LightWhiteConverter lightCard){
        Gson gson = new Gson();
        String s = gson.toJson(lightCard);
        WhiteConverter card = gson.fromJson(s, WhiteConverter.class);
        BuyResourcesRequest request = new BuyResourcesRequest(game.getUsername(), line, num, card);
        client.send(gson.toJson(request));
    }

    public void sendBuyDevCardRequest (int deck, int slot, Discount card){
        Gson gson = new Gson();
        BuyDevCardRequest request = new BuyDevCardRequest(game.getUsername(), deck, slot, card);
        client.send(gson.toJson(request));
    }

    public void sendLeaderCardActivationRequest (LeaderCard card){
        Gson gson = new Gson();
        LeaderCardActivationRequest request = new LeaderCardActivationRequest(game.getUsername(), card);
        client.send(gson.toJson(request));
    }

    public void defaultProduction(ArrayList<Resource> input, Resource output){
        sendDefaultProductionRequest(input, output, null, null);
        //todo leadercards
    }

    public void updateCardSlots(String username, CardSlots cardSlots){
        Gson gson = new Gson();
        String s = gson.toJson(cardSlots);
        LightCardSlots lightCardSlots = gson.fromJson(s, LightCardSlots.class);
        try {
            game.updateCardSlots(username, lightCardSlots);
        }catch (NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void updateDevBoard(DevelopmentBoard board){
        Gson gson = new Gson();
        String s = gson.toJson(board);
        LightDevelopmentBoard devBoard = gson.fromJson(s, LightDevelopmentBoard.class);
        game.updateDevBoard(devBoard);

    }

    public void updateWarehouse(String username, WarehouseDepot warehouseDepot){
        Gson gson = new Gson();
        String s = gson.toJson(warehouseDepot);
        LightWarehouseDepot newWarehouse = gson.fromJson(s, LightWarehouseDepot.class);
        try {
            game.updateWarehouse(username, newWarehouse);
        } catch(NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void updateMarketBoard(MarketBoard market){
        Gson gson = new Gson();
        String s = gson.toJson(market);
        LightMarketBoard newMarket = gson.fromJson(s, LightMarketBoard.class);
        game.updateMarketBoard(newMarket);
    }

    public void showThrewResources(String username, ArrayList<Resource> threwResources){
        if(game.getUsername().equals(username))
            view.showThrewResources(threwResources);
    }

    public void showError(String username, String message) {
        view.showError(message);
    }

    public void showSuccess(String username, String message){
        view.showSuccess(message);
    }


    public void updateStrongbox(String username, Strongbox strongbox){
        Gson gson = new Gson();
        String s = gson.toJson(strongbox);
        LightStrongbox newStrongbox = gson.fromJson(s, LightStrongbox.class);
        try {
            game.updateStrongbox(username, newStrongbox);
        } catch(NoSuchUsernameException e){
            view.showError(e.getMessage());
        }
    }

    public void chooseStartItems(ArrayList<LeaderCard> quartet, int numResources, boolean faithPoints){
        view.askStartItems(quartet, numResources, faithPoints);
    }



}