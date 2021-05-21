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
import exceptions.MessageNotSuccededException;
import exceptions.NoSuchUsernameException;
import model.cards.*;
import model.resources.Resource;
import model.strongbox.Strongbox;
import model.table.DevelopmentBoard;
import model.table.MarketBoard;
import model.warehouse.WarehouseDepot;
import network.client.Client;
import network.messages.gameMessages.*;
import network.messages.lobbyMessages.*;

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
        gson = new Gson();
    }

    public void connectToServer(String address, int port) throws UnknownHostException, IOException{
        client = new Client(address, port);
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
        } catch (IOException e) {
            // capiamo
            view.showError(e.getMessage());
        }
        RegisterUsernameResponse response = gson.fromJson(b, RegisterUsernameResponse.class);
        success = response.isSuccess();
        if(!success)
            throw new MessageNotSuccededException(response.getMessage());
        this.username = username;
        response.executeCommand(this);
    }

    public void askLobbyMenu(){
        view.askMenu();
    }

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

    public void startSinglePlayerGame(){
        view.switchToGame(true);
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

    public void showError(String username, String message){
        if(game.getUsername().equals(username))
            view.showError(message);

    }

    public void showSuccess(String username, String message){
        if(game.getUsername().equals(username))
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



}