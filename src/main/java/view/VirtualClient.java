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
    private BufferedReader in;
    private PrintWriter outStream;
    Controller controller;
    VirtualView virtualView;

    public VirtualClient(Socket clientSocket){
        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outStream = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())),true);
        }catch (IOException e){
            //e.printStackTrace();
        }
        virtualView = new VirtualView(outStream);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    private void handleLobbyMessage(String s){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        ((LobbyMessage) gson.fromJson(s, messageType.getClassType()))
                .executeCommand(LobbyHandler.getInstance(), this);
    }

    private void handleGameMessage(String s){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        GameMessage msg = (GameMessage) gson.fromJson(s, messageType.getClassType());
        controller.executeCommand(msg, this);
    }

    private void readLoop(){
        String s = "";
        try {
            //
            //BISOGNA SETTARE I CONTROLLER A TUTTI I PLAYER
            //
            while (controller == null) {
                s = in.readLine();
                handleLobbyMessage(s);//handle lobby message
            }
            while ((s = in.readLine()) != null) {
                handleGameMessage(s);//handle game message
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Client disconnesso");
        }
    }

    @Override
    public void run(){
        readLoop();
    }
}


