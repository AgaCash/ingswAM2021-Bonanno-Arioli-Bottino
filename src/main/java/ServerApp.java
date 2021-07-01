import network.server.Server;

public class ServerApp {
    public static void main(String[] args){
        Server server = new Server(80);
        server.startServer();
    }
}
