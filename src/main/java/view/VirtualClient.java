package view;

import clientModel.cards.LightLeaderCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import controller.Controller;
import exceptions.InvalidMessageException;
import utilities.LightLeaderCardDeserializer;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;
import network.messages.lobbyMessages.LobbyMessage;
import network.messages.lobbyMessages.StandardLobbyResponse;
import network.messages.pingMessages.PongGameMessage;
import network.messages.pingMessages.PongLobbyMessage;
import network.timer.ServerPingHandler;

import java.io.*;
import java.net.Socket;

public class VirtualClient extends Thread{
    private BufferedReader inStream;
    public PrintWriter outStream;
    private Controller controller;
    private VirtualView virtualView;
    private ServerPingHandler serverPingHandler;

    public VirtualClient(Socket clientSocket){
        try{
            inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outStream = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())),true);
            virtualView = new VirtualView(outStream);
            serverPingHandler = new ServerPingHandler(5000, this);
            new Thread(serverPingHandler).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Controller getController() {
        return controller;
    }

    public void resetLobbyTimer(){
        serverPingHandler.reset();
        //lobbyServerTimerCheckConnection.reset();
    }

    public void resetGameTimer(){
        serverPingHandler.reset();
        //gameServerTimerCheckConnection.reset();
    }
    /*
    //TODO: todo???
    public void disconnectPlayerFromGame(){
        System.out.println("DISCONNECT FROM GAME");
        //controller.disconnectPlayer(virtualView.getUsername());
    }

    public void disconnectPlayerFromLobby(){
        System.out.println("DISCONNECT FROM LOBBY");
        try {
            Lobby l = LobbyHandler.getInstance().getLobbyFromUsername(virtualView.getUsername());
            l.leaveLobby(virtualView.getUsername());
        } catch (NoSuchUsernameException e) {
            System.out.println(e.getMessage());
            //Player was not in any lobby
        }
    }*/

    public void setController(Controller controller) {
        this.controller = controller;
        //System.out.println(this.controller);
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    private void handleLobbyMessage(String s) throws InvalidMessageException{
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        if(messageType==null){
            throw new InvalidMessageException("Invalid message");
        }
        ((LobbyMessage) gson.fromJson(s, messageType.getClassType()))
                .executeCommand(this);
    }

    private void handleGameMessage(String s) throws InvalidMessageException{
        Gson gson = new Gson();
        gson = new GsonBuilder().registerTypeAdapter(LightLeaderCard.class, new LightLeaderCardDeserializer()).create();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        if(messageType==null){
            throw new InvalidMessageException("Invalid message");
        }
        System.out.println("sto per eseguire il game message ");
        ((GameMessage) gson.fromJson(s, messageType.getClassType()))
                .executeCommand(controller, this);


    }

    private void sendInvalidMessage(String msg){
        System.out.println("MESSAGGIO NON VALIDO");
        //da togliere, si può anche non inviare niente se il messaggio non è valido.
        //Si presuppone che il client invii sempre messaggi corretti
        virtualView.sendStandardLobbyResponse(
                new StandardLobbyResponse(virtualView.getUsername(), false, msg));
    }

    private boolean ifPingExecute(String s){
        Gson gson = new Gson();
        boolean retValue = false;
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        try{
            MessageType m = MessageType.valueOf(jsonObject.get("messageType").getAsString());
            switch (m) {
                case PONG_LOBBY -> {
                    ((LobbyMessage) gson.fromJson(s, PongLobbyMessage.class)).executeCommand(this);
                    retValue = true;
                }
                case PONG_GAME -> {
                    ((GameMessage) gson.fromJson(s, PongGameMessage.class)).executeCommand(controller, this);
                    retValue = true;
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println("message not valid");
        }
        return retValue;
    }

    private void readLoop(){
        String s = "";
        try {
            while (controller == null) {
                do{
                    s = inStream.readLine();
                    System.out.println("LOBBY STRINGA ENTRANTE:::"+s);
                    //lobbyServerTimerCheckConnection.reset();
                    serverPingHandler.reset();
                }while (ifPingExecute(s) && controller == null);
                try {
                    handleLobbyMessage(s);//handle lobby message
                    s = "";
                } catch (InvalidMessageException e) {
                    sendInvalidMessage(e.getMessage());
                } catch (ClassCastException c){
                }
            }

            System.out.println("CAMBIO STATO, PARTITA INIZIATA");
            //setup change state of pinger
            serverPingHandler.setGameState(true);
            //robe initialize
            while (true) { // TODO: esce se gioco finisce
                do{
                    if(s == "")
                        s = inStream.readLine();
                    System.out.println("GAME STRINGA ENTRANTE:::"+s);
                    serverPingHandler.reset();
                }while (ifPingExecute(s));
                try{
                    //gameServerTimerCheckConnection.reset();

                    handleGameMessage(s);//handle game message
                }catch (InvalidMessageException e) {
                    sendInvalidMessage(e.getMessage());
                }
                s = "";
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Client disconnesso");
        }
    }
/*
    public void stopGameTimer(){
        gameServerTimerCheckConnection.finish();
    }*/

    @Override
    public void run(){
        readLoop();
    }
}


