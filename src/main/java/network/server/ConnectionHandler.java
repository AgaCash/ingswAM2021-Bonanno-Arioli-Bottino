package network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Controller;
import model.Game;
import network.JsonParserNetwork;
import network.messages.*;
import network.messages.gameMessages.GameMessage;
import network.messages.lobbyMessages.*;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler extends Thread{
    /*
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private int id;
    private JsonParserNetwork jsonParserNetwork;


    private String username;
    private boolean isUsernameUnique;
    private LobbyHandler lobbyHandler; //DA SINCRONIZZARE
    private Lobby lobby;
    private Controller controller;



    public ConnectionHandler(Socket clientSocket, int id){
        this.clientSocket = clientSocket;
        this.id = id;
        jsonParserNetwork = new JsonParserNetwork();
        lobbyHandler = LobbyHandler.getInstance();
        isUsernameUnique = false;
        controller = null;
        lobby = null;
        username = null;
    }

    public void checkUsername(String username){
        if(this.username == null){
            this.username = username;
        }
        //DA COMPLETARE
        //
        //Controllare tutti gli username nelle lobbies:
        //  ->Se è in una partita avviata ed è online oppure in una lobby in attesa throw "esiste già"
        //  ->Se è in una partita ma non è online dargli il controller della partita e aggiornarlo sul game
        //  -> altrimenti this.username = username -> OK!
    }

    private void handleMessage(String s){
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        switch (messageType.megaType){
            case "LOBBY":
                ((LobbyMessage) gson.fromJson(s, messageType.c)).executeCommand(lobbyHandler, out);
            case "GAME":
                ((GameMessage) gson.fromJson(s, messageType.c)).executeCommand(controller, out);
                break;
        }
    }


    private Boolean readLoop(){
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                handleMessage(s);
            }
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("DISCONNESSO?");
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



     */
}
