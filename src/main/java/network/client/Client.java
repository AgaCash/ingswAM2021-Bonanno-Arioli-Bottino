package network.client;

import clientController.LightController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import network.messages.MessageType;
import network.messages.gameMessages.GameMessage;
import network.messages.lobbyMessages.LobbyMessage;
import network.messages.lobbyMessages.LoginMultiPlayerResponse;
import network.timer.ClientPingReceiverTimer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    private final InetAddress address;
    private final int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter outStream;
    private LightController lightController;
    private ArrayList<String> messageBuffer;
    private final Object queueLock = new Object();
    private ClientPingReceiverTimer clientPingReciverTimer;

    public Client(String address, int port, LightController lightController) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
        this.port = port;
        this.lightController = lightController;
        messageBuffer = new ArrayList<>();
        clientPingReciverTimer = new ClientPingReceiverTimer(3000, lightController);
    }

    public void connect() throws IOException {
        socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())),true);
        new Thread(clientPingReciverTimer).start();
        new Thread(()->{
            do{
                try {
                    String r = in.readLine();
                    clientPingReciverTimer.reset();
                    synchronized (queueLock){
                        if(!ifAsyncMessageExecute(r))
                            messageBuffer.add(r);
                        queueLock.notifyAll();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    lightController.serverDisconnected();
                    break;
                }
            }while (true);

        }).start();


    }

    private boolean ifAsyncMessageExecute(String s){
        Gson gson = new Gson();
        boolean retValue = false;
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        try{
            MessageType m = MessageType.valueOf(jsonObject.get("messageType").getAsString());
            switch (m) {
                case PING_LOBBY, LOBBY_PLAYER_DISCONNECTED -> {
                    ((LobbyMessage) gson.fromJson(s, m.getClassType())).executeCommand(lightController);
                    retValue = true;
                }
                case JOINMULTIPLAYER_RESPONSE ->{
                    LoginMultiPlayerResponse l = gson.fromJson(s, LoginMultiPlayerResponse.class);
                    if(!l.getUsername().equals(lightController.getUsername())){
                        l.executeCommand(lightController);
                        retValue = true;
                    }
                }
                case PING_GAME, PLAYER_DISCONNECTED, PLAYER_RECONNECTED -> {
                    ((GameMessage) gson.fromJson(s, m.getClassType())).executeCommand(lightController);
                    retValue = true;
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println("message not valid");
        }
        return retValue;
    }

    public void send(String s){
        outStream.println(s);
    }

    public String recv() throws IOException {
        String s = null;
        synchronized (queueLock){
            while(messageBuffer.isEmpty()) {
                try {
                    queueLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            s = messageBuffer.get(0);
            messageBuffer.remove(0);
        }
        return s;
    }

    public void close() throws IOException {
        socket.close();
    }
}
