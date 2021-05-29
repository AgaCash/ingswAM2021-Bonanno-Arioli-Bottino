package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.UsernameAlreadyUsedException;
import model.cards.LeaderCard;
import utilities.LeaderCardDeserializer;
import network.messages.gameMessages.*;
import network.messages.lobbyMessages.*;
import network.messages.pingMessages.PingGameMessage;
import network.messages.pingMessages.PingLobbyMessage;
import network.server.LobbyHandler;

import java.io.PrintWriter;

public class VirtualView implements ServerView {
    private PrintWriter outStream;
    private String username;
    private Gson gson;

    //TODO:
    //  riuscire a creare VirtualView già con l'username
    //  da VirtualClient
    public VirtualView(PrintWriter out, String username) {
        this.outStream = out;
        this.username = username;
        gson = new Gson();
        gson = new GsonBuilder().registerTypeAdapter(LeaderCard.class, new LeaderCardDeserializer()).create();
        //todo a caso? capiamo
    }

    public VirtualView(PrintWriter out) {
        this.outStream = out;
        gson = new Gson();
    }

    public void sendPing(PingLobbyMessage pingMessage){
        System.out.println("SEND LOBBY PING");
        outStream.println(gson.toJson(pingMessage));
    }

    public void sendPing(PingGameMessage pingMessage){
        System.out.println("SEND GAME PING");
        outStream.println(gson.toJson(pingMessage));
    }

    public void setUsername(String username) throws UsernameAlreadyUsedException {
        if(!LobbyHandler.getInstance().isUsernameAvailable(username)){
            throw new UsernameAlreadyUsedException("Username already used");
        }
        this.username = username;

    }

    public void updateBuyDevCard(BuyDevCardResponse response){
        System.out.println("GAME STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }
    public void updateBuyResources(BuyResourcesResponse response){
        System.out.println("GAME STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }
    public void updateDefaultProduction(DefaultProductionResponse response){
        System.out.println("GAME STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));}
    public void updateDevCardProduction(DevCardProductionResponse response){
        System.out.println("GAME STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));}
    public void updateLeaderCardActivation(LeaderCardActivationResponse response){
        System.out.println("GAME STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));}
    public void updateStartGame(StartGameResponse response){
        System.out.println("GAME STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));}
    public void updateSetup(SetupResponse response){
        System.out.println("GAME STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }
    //public void updateFailedAction(FailedActionNotify notify){outStream.println(gson.toJson(notify));}
    //todo stesso discorso di controller: tenere un messaggio notify per tutti nei casi eccezionali?
    public void updateInternalError(InternalErrorNotify notify){ outStream.println(gson.toJson(notify));}


    public void sendGetLobby(GetLobbyResponse lobbyMessage){
        outStream.println(gson.toJson(lobbyMessage));
        //System.out.println("sendgetlobby: "+lobbyMessage);
        //System.out.println("gson_sendgetlobby: " +gson.toJson(lobbyMessage));
    }

    public void sendLobbyMessage(LobbyMessage lobbyMessage){
        outStream.println(gson.toJson(lobbyMessage));
    }

    public void sendStartMultiPlayerSignal(){
        StartMultiPlayerResponse lobbyMessage =
                new StartMultiPlayerResponse(username);
        outStream.println(gson.toJson(lobbyMessage));
    }

    public void sendStartSinglePlayerSignal(){
        StartSinglePlayerResponse lobbyMessage =
                new StartSinglePlayerResponse(username);
        outStream.println(gson.toJson(lobbyMessage));
    }

    public void sendLobbyResponse(CreateLobbyResponse response){
        System.out.println("LOBBY STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }

    public void sendLobbyResponse(GetLobbyResponse response){
        System.out.println("LOBBY STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }

    public void sendLobbyResponse(LoginMultiPlayerResponse response){
        System.out.println("LOBBY STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }

    public void sendLobbyResponse(RegisterUsernameResponse response){
        System.out.println("LOBBY STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }

    public void sendLobbyResponse(StartMultiPlayerResponse response){
        System.out.println("LOBBY STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }

    public void sendLobbyResponse(StartSinglePlayerResponse response){
        System.out.println("LOBBY STRINGA USCENTE:::"+gson.toJson(response));
        outStream.println(gson.toJson(response));
    }

    public void sendStandardLobbyResponse(StandardLobbyResponse lobbyMessage){
        System.out.println("LOBBY STRINGA USCENTE:::"+gson.toJson(lobbyMessage));
        outStream.println(gson.toJson(lobbyMessage));
    }


    public String getUsername(){
        return this.username;
    }


}
