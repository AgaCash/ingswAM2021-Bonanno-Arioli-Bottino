package network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Controller;
import model.Game;
import network.JsonParserNetwork;
import network.messages.*;
import network.messages.lobbyMessages.*;

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

    private void handleMessage(String s){
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        switch (messageType.megaType){
            case "LOBBY":
                ((LobbyMessage) gson.fromJson(s, messageType.c)).executeCommand(lobbyHandler, out);
        }
        /*
        switch (MessageType.valueOf(jsonObject.get("messageType").getAsString())){
            case CREATEMULTIPLAYER:
                CreateLobbyRequest createLobbyRequest = gson.fromJson(s, CreateLobbyRequest.class);
                createLobbyRequest.executeCommand(lobbyHandler, out);
                break;

        }
        */
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
