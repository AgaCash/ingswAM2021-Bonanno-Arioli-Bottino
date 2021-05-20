package view;

import com.google.gson.Gson;
import exceptions.UsernameAlreadyUsedException;
import network.messages.gameMessages.*;
import network.messages.lobbyMessages.*;
import network.messages.gameMessages.FailedActionNotify;
import network.server.LobbyHandler;

import java.io.PrintWriter;

public class VirtualView implements View{
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
    }

    public VirtualView(PrintWriter out) {
        this.outStream = out;
        this.username = "username";
        gson = new Gson();
    }

    public void setUsername(String username) throws UsernameAlreadyUsedException {
        if(!LobbyHandler.getInstance().isUsernameAvailable(username)){
            throw new UsernameAlreadyUsedException("Username already used");
        }
        this.username = username;
    }

    public void sendError(FailedActionNotify error){
        outStream.println(gson.toJson(error));
    }

    public void updateStartingSituation(){
        StandardLobbyResponse s = new StandardLobbyResponse(username, true);
        outStream.println(gson.toJson(s));

    }

    public void updateBuyDevCard(BuyDevCardResponse response){
        outStream.println(gson.toJson(response));
    }
    public void updateBuyResources(BuyResourcesResponse response){
        outStream.println(gson.toJson(response));
    }
    public void updateDefaultProduction(DefaultProductionResponse response){outStream.println(gson.toJson(response));}
    public void updateDevCardProduction(DevCardProductionResponse response){outStream.println(gson.toJson(response));}
    public void updateLeaderCardActivation(LeaderCardActivationResponse response){ outStream.println(gson.toJson(response));}
    public void updateStartGame(StartGameResponse response){ outStream.println(gson.toJson(response));}
    public void updateSetup(SetupResponse response){ outStream.println(gson.toJson(response));}
    public void updateFailedAction(FailedActionNotify notify){outStream.println(gson.toJson(notify));}
    public void updateInternalError(InternalErrorNotify notify){ outStream.println(gson.toJson(notify));}


    public void sendGetLobby(GetLobbyResponse lobbyMessage){
        String s = gson.toJson(lobbyMessage);
        outStream.println(s);
        System.out.println("sendgetlobby: "+lobbyMessage);
        System.out.println("gson_sendgetlobby: " +gson.toJson(lobbyMessage));
    }

    public void sendLobbyMessage(LobbyMessage lobbyMessage){
        System.out.println("sendLobbyMessage: "+gson.toJson(lobbyMessage));
        outStream.println(gson.toJson(lobbyMessage));
    }

    public void sendStartMultiPlayerSignal(){
        StartMultiPlayerResponse lobbyMessage =
                new StartMultiPlayerResponse(username);
        outStream.println(gson.toJson(lobbyMessage));
    }

    public void sendStandardLobbyResponse(StandardLobbyResponse lobbyMessage){
        outStream.println(gson.toJson(lobbyMessage));
    }

    public String getUsername(){
        return this.username;
    }


}
