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
        clientPingReciverTimer = new ClientPingReceiverTimer(10000, lightController);
    }

    public void connect() throws IOException {
        socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())),true);
        new Thread(()->{
            do{
                try {
                    String r = in.readLine();
                    //System.out.println("LETTO DALLA RETE::::"+r); //DEBUG
                    clientPingReciverTimer.reset();
                    synchronized (queueLock){
                        if(!ifAsyncMessageExecute(r)) //se non è un messaggio di ping lo aggiunge alla coda
                            messageBuffer.add(r);
                        queueLock.notifyAll();
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println(e.getMessage());
                    lightController.serverDisconnected();
                    break;
                }
            }while (true);

        }).start();

        new Thread(clientPingReciverTimer).start();
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

    /*
   /* public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        Client client = new Client("localhost", 1234);
        client.connect();

        boolean success = false;
        String username = null;
        //FASE LOGIN
        Scanner s = new Scanner(System.in);
        while (!success){
            System.out.println("Inserisci il tuo username:");
            username = s.nextLine();
            RegisterUsernameRequest registerUsernameRequest = new RegisterUsernameRequest(username);
            String a = gson.toJson(registerUsernameRequest);
            client.send(a);
            String b = client.recv();
            StandardLobbyResponse response = gson.fromJson(b, StandardLobbyResponse.class);
            success = response.getSuccess();
            if(!success)
                System.out.println(response.getMessage());
        }

        //FASE MENU'

        boolean lobbyState = true;
        boolean waitingLobbyState = true;
        boolean isLobbyCreator = false;
        StandardLobbyResponse slr = null;
        while (lobbyState){
            System.out.println("Scegli cosa vuoi fare \n1- Singleplayer \n2- Join Multiplayer Lobby"+
                    " \n3- Create Multiplayer Lobby");

            switch (s.nextInt()){
                case 1:
                    StartSinglePlayerRequest loginSinglePlayerRequest = new StartSinglePlayerRequest(username);
                    client.send(gson.toJson(loginSinglePlayerRequest));
                    slr = gson.fromJson(client.recv(), StandardLobbyResponse.class);
                    waitingLobbyState = false;
                    if(slr.getSuccess()){
                        lobbyState = false;
                    }else{
                        System.out.println(slr.getMessage());
                    }
                    break;
                case 2:
                    GetLobbyRequest getLobbyRequest = new GetLobbyRequest(username);
                    System.out.println("SEND: "+gson.toJson(getLobbyRequest));

                    client.send(gson.toJson(getLobbyRequest));
                    String a = client.recv();
                    System.out.println("recv: "+a);
                    GetLobbyResponse lobbyResponse = gson.fromJson(a, GetLobbyResponse.class);
                    //System.out.println(lobbyResponse);
                    if(lobbyResponse.getLobbies().size() == 0){
                        System.out.println("Nessuna lobby esistente");
                        break;
                    }
                    //System.out.println(lobbyResponse.getLobbies());
                    System.out.println("Scegli l'id della lobby a cui vuoi unirti");
                    int lobbyId = s.nextInt();
                    System.out.println("Creating the message");
                    LoginMultiPlayerRequest loginMultiPlayerRequest = new LoginMultiPlayerRequest(username, lobbyId);
                    System.out.println("Sending the message");
                    String p = gson.toJson(loginMultiPlayerRequest);
                    System.out.println("P: "+p);
                    client.send(p);
                    System.out.println("Receiving the answer");
                    String x = client.recv();
                    System.out.println("Traslating the message");
                    slr = gson.fromJson(x, StandardLobbyResponse.class);
                    if(slr.getSuccess()){
                        lobbyState = false;
                    }else{
                        System.out.println(slr.getMessage());
                    }
                    break;
                case 3:
                    CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest(username);
                    client.send(gson.toJson(createLobbyRequest));
                    slr = gson.fromJson(client.recv(), StandardLobbyResponse.class);
                    isLobbyCreator = true;
                    if(slr.getSuccess()){
                        lobbyState = false;
                    }else{
                        System.out.println(slr.getMessage());
                    }
                    break;
            }

        }

        //FASE WAITING GAME STARTING (ifMulti)
        while(waitingLobbyState){
            if(isLobbyCreator){
                System.out.println("Premi invio quando sei pronto a partire");
                s.nextLine();
                s.nextLine();
                s.nextLine();
                s.nextLine();
                StartMultiPlayerRequest x = new StartMultiPlayerRequest(username);
                System.out.println(gson.toJson(x));
                client.send(gson.toJson(x));
                System.out.println(client.recv());
            }else{
                System.out.println("premi invio se vuoi unirti alla partita se è iniziata");
                s.nextLine();
            }
        }


        //FASE START GAME
        System.out.println("STAI GIOCANDO, NON TI STAI DIVERTENDO?");
        if((int)(Math.random() * 101)%2 == 0){
            System.out.println("BRAVO HAI VINTO");
        }else{
            System.out.println("SCARSONE HAI PERSO,");
        }
        while(true){
            s.nextLine();
        }


        //client.close();
    }*/
}
