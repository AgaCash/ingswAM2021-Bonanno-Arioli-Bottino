package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// to test against console:
//  /usr/bin/nc 127.0.0.1 1234
// and type in console: server will receive.
// it will NOT block socket (for now..) when timeout.

public class Server {
    private int port = 1234;
    private LobbyHandler lobbyHandler;

    public Server(int port) {
        this.port = port;
    }
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }

        System.out.println("Server ready");

        lobbyHandler = LobbyHandler.getInstance();

        int id = 1;
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new ConnectionHandler(socket, id));
            } catch(IOException e) {
                e.printStackTrace();
            }
            id++;
        }
    }

    public static void main(String[] args) {
        Server server = new Server(1234);
        server.startServer();
    }

}

