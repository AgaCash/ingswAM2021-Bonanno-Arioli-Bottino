package network.client;

import com.google.gson.Gson;
import network.JsonParserNetwork;
import network.messages.lobbyMessages.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private InetAddress address;
    private int port;
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    JsonParserNetwork jsonParserNetwork;

    public Client(String address, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
        this.port = port;
        jsonParserNetwork = new JsonParserNetwork();
    }

    public void connect() throws IOException {
        socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
    }

    public void send(String s){
        out.println(s);
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
        Scanner s = new Scanner(System.in);
        System.out.println("Inserisci il tuo username:");
        String username = s.nextLine();
        while (true){
            System.out.println("Scegli cosa vuoi fare \n1- Singleplayer \n2- Join Multiplayer Lobby"+
                    " \n3- Create Multiplayer Lobby");

            switch (s.nextInt()){
                case 1:
                    LoginSinglePlayerRequest loginSinglePlayerRequest = new LoginSinglePlayerRequest(username);
                    client.send(gson.toJson(loginSinglePlayerRequest));
                    break;
                case 2:
                    GetLobbyRequest getLobbyRequest = new GetLobbyRequest(username);
                    client.send(gson.toJson(getLobbyRequest));
                    GetLobbyResponse lobbyResponse = gson.fromJson(client.recv(), GetLobbyResponse.class);
                    System.out.println(lobbyResponse.getLobbies());
                    System.out.println("Scegli l'id della lobby a cui vuoi unirti");
                    int lobbyId = s.nextInt();
                    LoginMultiPlayerRequest loginMultiPlayerRequest = new LoginMultiPlayerRequest(username, lobbyId);
                    client.send(gson.toJson(loginMultiPlayerRequest));
                    break;
                case 3:
                    CreateLobbyRequest createLobbyRequest = new CreateLobbyRequest(username);
                    client.send(gson.toJson(createLobbyRequest));
                    break;
            }
            System.out.println(client.recv());
        }

        //client.close();
    }
}
