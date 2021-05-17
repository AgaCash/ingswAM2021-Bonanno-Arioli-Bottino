package view;

import com.google.gson.Gson;
import network.messages.gameMessages.*;
import network.messages.lobbyMessages.GetLobbyResponse;
import network.messages.lobbyMessages.LobbyMessage;
import network.messages.lobbyMessages.StandardLobbyResponse;
import network.messages.gameMessages.FailedActionNotify;

import java.io.PrintWriter;

public class VirtualView implements View{
    private PrintWriter outStream;
    private String username;
    private Gson gson;

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
        String s = gson.toJson(lobbyMessage);
        outStream.println(s);
        System.out.println(lobbyMessage);
        System.out.println(gson.toJson(lobbyMessage));
    }

    public void sendLobbyMessage(LobbyMessage lobbyMessage){
        outStream.println(gson.toJson(lobbyMessage));
    }

    public void sendStandardLobbyResponse(StandardLobbyResponse lobbyMessage){
        outStream.println(gson.toJson(lobbyMessage));
    }

    public String getUsername(){
        return this.username;
    }


}
