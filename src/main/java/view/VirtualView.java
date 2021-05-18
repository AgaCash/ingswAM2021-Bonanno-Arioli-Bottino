package view;

import com.google.gson.Gson;
import exceptions.UsernameAlreadyUsedException;
import network.messages.gameMessages.*;
import network.messages.lobbyMessages.GetLobbyResponse;
import network.messages.lobbyMessages.LobbyMessage;
import network.messages.lobbyMessages.StandardLobbyResponse;
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

    public void update(GameMessage response){
        outStream.println(gson.toJson(response));
    }

    public void sendGetLobby(GetLobbyResponse lobbyMessage){
        String s = lobbyMessage.toString();//gson.toJson(lobbyMessage);
        outStream.println(s);
        System.out.println("sendgetlobby: "+lobbyMessage);
        System.out.println("gson_sendgetlobby: " +gson.toJson(lobbyMessage));
    }

    public void sendLobbyMessage(LobbyMessage lobbyMessage){
        System.out.println("sendLobbyMessage: "+lobbyMessage);
        outStream.println(gson.toJson(lobbyMessage));
    }

    public void sendStandardLobbyResponse(StandardLobbyResponse lobbyMessage){
        outStream.println(gson.toJson(lobbyMessage));
    }

    public String getUsername(){
        return this.username;
    }


}
