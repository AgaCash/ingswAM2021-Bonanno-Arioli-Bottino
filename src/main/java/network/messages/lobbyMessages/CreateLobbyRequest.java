package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.server.LobbyHandler;
import model.Player;
import view.View;
import view.VirtualClient;

import javax.naming.SizeLimitExceededException;

public class CreateLobbyRequest extends LobbyMessage{


    public CreateLobbyRequest(String username) {
        super(username, MessageType.CREATEMULTIPLAYER);
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, View view, VirtualClient virtualClient) {
        try {
            lobbyHandler.createLobby(new Player(super.getUsername()));
        }catch (SizeLimitExceededException s){
            System.out.println("Devo avvisare l'user, quindi a questo serve la virtual view?");
        }finally {
            //out.println( gson.toJson(standardLobbyResponse) );
        }
    }
}
