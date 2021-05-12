package view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Controller;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;
import network.messages.lobbyMessages.LobbyMessage;
import network.server.LobbyHandler;

import java.io.*;
import java.net.Socket;

public class VirtualClient extends Thread{
    private LobbyHandler lobbyHandler;
    private BufferedReader in;
    private PrintWriter out;
    Controller controller;
    VirtualView virtualView;

    public VirtualClient(Socket clientSocket){
        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())),true);
            lobbyHandler = LobbyHandler.getInstance();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void handleMessage(String s){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());


        switch (messageType.getUpperType()){
            case "LOBBY":
                ((LobbyMessage) gson.fromJson(s, messageType.getClassType()))
                        .executeCommand(lobbyHandler, virtualView, this);
            case "GAME":
                GameMessage msg = (GameMessage) gson.fromJson(s, messageType.getClassType());
                controller.executeCommand(msg);
                break;
        }
    }

    private void readLoop(){
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                handleMessage(s);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("DISCONNESSO?");
        }
    }

    @Override
    public void run(){
        readLoop();
    }
}

//TODO:
//  StartGameSinglePlayerRequest:
//      seeTODO
//  multiplayer
//  LOBBY HANDLER:
//      messaggio:
//          -crea Lobby
//          -join lobby
//          messaggioStartGame:
//            -> avvia il gioco
//              -> ritorna il controller a VirtualClient

