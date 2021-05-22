package view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.Controller;
import exceptions.InvalidMessageException;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;
import network.messages.lobbyMessages.LobbyMessage;
import network.messages.lobbyMessages.StandardLobbyResponse;

import java.net.Socket;
import java.io.*;
import java.util.InputMismatchException;

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

    private void handleLobbyMessage(String s) throws  InvalidMessageException{
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        if(messageType==null){
            throw new InvalidMessageException("Invalid message");
        }
        System.out.println(s);
        ((LobbyMessage) gson.fromJson(s, messageType.getClassType()))
                .executeCommand(this);
    }

    private void handleGameMessage(String s) throws InvalidMessageException{
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        MessageType messageType = MessageType.valueOf(jsonObject.get("messageType").getAsString());
        GameMessage msg = (GameMessage) gson.fromJson(s, messageType.getClassType());
        controller.executeCommand(msg, this);
    }

    private void sendInvalidMessage(String msg){
        System.out.println("MESSAGGIO NON VALIDO");
        //da togliere, si può anche non inviare niente se il messaggio non è valido.
        //Si presuppone che il client invii sempre messaggi corretti
        virtualView.sendStandardLobbyResponse(
                new StandardLobbyResponse(virtualView.getUsername(), false, msg));
    }

    private void readLoop(){
        String s = "";
        try {
            while (controller == null) {
                s = inStream.readLine();
                System.out.println("LOBBY STRINGA ENTRANTE:::"+s);
                try {
                    handleLobbyMessage(s);//handle lobby message
                } catch (InvalidMessageException e) {
                    sendInvalidMessage(e.getMessage());
                }
            }

            System.out.println("CAMBIO STATO, PARTITA INIZIATA");
            //robe initialize
            while ((s = inStream.readLine()) != null) {
                System.out.println("GAME STRINGA ENTRANTE:::"+s);
                try{
                    handleGameMessage(s);//handle game message
                }catch (InvalidMessageException e) {
                    sendInvalidMessage(e.getMessage());
                }
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


