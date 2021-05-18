package view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Controller;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;
import network.messages.lobbyMessages.LobbyMessage;
import java.net.Socket;
import java.io.*;

public class VirtualClient extends Thread{
    private BufferedReader inStream;
    public PrintWriter outStream;
    private Controller controller;
    private VirtualView virtualView;

    public VirtualClient(Socket clientSocket){
        try{
            inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outStream = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())),true);
            virtualView = new VirtualView(outStream);
        }catch (IOException e){
            e.printStackTrace();
        }
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
                .executeCommand(this);
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
            //TODO:
            //  BISOGNA SETTARE I CONTROLLER A TUTTI I PLAYER
            //
            while (controller == null) {
                s = inStream.readLine();
                handleLobbyMessage(s);//handle lobby message
            }
            //robe initialize
            while ((s = inStream.readLine()) != null) {
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


