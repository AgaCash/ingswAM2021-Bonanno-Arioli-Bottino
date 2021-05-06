package network.messages.lobbyMessages;

import network.JsonParserNetwork;
import network.messages.MessageType;
import network.server.LobbyHandler;
import network.server.Player;

import javax.naming.SizeLimitExceededException;
import java.io.PrintWriter;

public class CreateLobbyRequest extends LobbyMessage{


    public CreateLobbyRequest(String username) {
        super(username, MessageType.CREATEMULTIPLAYER);
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, PrintWriter out) {
        lobbyHandler = LobbyHandler.getInstance();
        try {
            lobbyHandler.createLobby(new Player(super.getUsername()));
            out.println(new JsonParserNetwork().createLobbyResponse(true));
        }catch (SizeLimitExceededException s){
            System.out.println("Devo avvisare l'user, quindi a questo serve la virtual view?");
            out.println(new JsonParserNetwork().createLobbyResponse(false));
        }
    }
}
