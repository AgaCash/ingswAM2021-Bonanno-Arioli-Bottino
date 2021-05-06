package network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Controller;
import model.Game;
import network.JsonParserNetwork;
import network.messages.*;
import network.messages.lobbyMessages.CreateLobbyRequest;
import network.messages.lobbyMessages.GetLobbyRequest;
import network.messages.lobbyMessages.LoginMultiPlayerRequest;
import network.messages.lobbyMessages.LoginSinglePlayerRequest;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread{
    Socket clientSocket;
    BufferedReader in;
    PrintWriter out;
    int id;
    JsonParserNetwork jsonParserNetwork;
    LobbyHandler lobbyHandler;
    Controller controller;


    public ConnectionHandler(Socket clientSocket, int id){
        this.clientSocket = clientSocket;
        this.id = id;
        jsonParserNetwork = new JsonParserNetwork();
        lobbyHandler = LobbyHandler.getInstance();
        controller = null;
    }

    /*
    private void handleMessageOld(String s){
        Message message = jsonParserNetwork.getMessage(s);
        switch (message.getMessageType()){
            case JOIN_SINGLEPLAYER:
                System.out.println("SINGLEPLAYER, username: "+((LoginSinglePlayerRequest) message).getUsername());
                break;
            case JOINMULTIPLAYER:
                LoginMultiPlayerRequest loginMultiPlayerRequest = (LoginMultiPlayerRequest) message;
                System.out.println("MULTIPLAYER, username: "+ loginMultiPlayerRequest.getUsername()+
                        "  --  lobbyId: "+loginMultiPlayerRequest.getLobbyId());
                break;
            case GETLOBBIES:
                GetLobbyRequest getLobbyRequest = (GetLobbyRequest) message;
                System.out.println("Sending lobbyList");
                out.println(jsonParserNetwork.getMessage(lobbyHandler.getLobbies().toString()));
                break;
            case CREATEMULTIPLAYER:
                CreateLobbyRequest createLobbyRequest = (CreateLobbyRequest) message;
                System.out.println("Creating lobby");
                break;

        }
    }

     */

    private void handleMessage(String s){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);;
        switch (MessageType.valueOf(jsonObject.get("messageType").getAsString())){
            case CREATEMULTIPLAYER:
                CreateLobbyRequest createLobbyRequest = gson.fromJson(s, CreateLobbyRequest.class);
                createLobbyRequest.executeCommand(lobbyHandler, out);
                break;
        }
    }


    private Boolean readLoop(){
        String s = "";
        JsonParserNetwork jsonParser = new JsonParserNetwork();
        //LoginMessage loginMessage;
        try {
            while ((s = in.readLine()) != null) {
                handleMessage(s);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR FROM CLIENT");
        }
        return false;
    }

    @Override
    public void run(){
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())),true);
            readLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
