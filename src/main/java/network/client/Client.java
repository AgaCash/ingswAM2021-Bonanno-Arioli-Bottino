package network.client;

import com.google.gson.Gson;
import network.JsonParserNetwork;
import network.messages.lobbyMessages.*;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class Client {
    private InetAddress address;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter outStream;

    public Client(String address, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
    }

    public void send(String s){
        outStream.println(s);
    }

    public String recv() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) throws Exception {
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
            RegisterUsernameRequest registerUsernameRequest =
                    new RegisterUsernameRequest(username);
            client.send(gson.toJson(registerUsernameRequest));
            StandardLobbyResponse response = gson.fromJson(client.recv(), StandardLobbyResponse.class);
            success = response.getSuccess();
            if(!success)
                System.out.println(response.getMessage());
        }

        //FASE MENU'

        boolean lobbyState = true;
        boolean waitingLobbyState = true;
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
                    LoginMultiPlayerRequest loginMultiPlayerRequest = new LoginMultiPlayerRequest(username, lobbyId);
                    client.send(gson.toJson(loginMultiPlayerRequest));
                    slr = gson.fromJson(client.recv(), StandardLobbyResponse.class);
                    break;
                case 3:
                    CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest(username);
                    client.send(gson.toJson(createLobbyRequest));
                    slr = gson.fromJson(client.recv(), StandardLobbyResponse.class);
                    break;
            }
            assert slr != null;
            if(slr.getSuccess()){
                lobbyState = false;
            }else{
                System.out.println(slr.getMessage());
            }
        }

        //FASE WAITING GAME STARTING (ifMulti)
        if(s.nextInt() == 7){
            StartMultiPlayerRequest x = new StartMultiPlayerRequest(username);
            System.out.println(gson.toJson(x));
            client.send(gson.toJson(x));
            System.out.println(client.recv());
        }else{
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
        }





        //client.close();
    }
}
