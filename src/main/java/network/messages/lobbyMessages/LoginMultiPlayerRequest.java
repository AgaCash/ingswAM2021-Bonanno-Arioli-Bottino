package network.messages.lobbyMessages;

import exceptions.LobbyFullException;
import network.messages.MessageType;
import network.server.LobbyHandler;
import model.Player;
import view.View;
import view.VirtualClient;

public class LoginMultiPlayerRequest extends LobbyMessage{
    private int lobbyId;

    public LoginMultiPlayerRequest(String username, int lobbyId) {
        super(username, MessageType.JOINMULTIPLAYER);
        this.lobbyId = lobbyId;
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, View view, VirtualClient virtualClient) {
        try{
            lobbyHandler.joinLobby(new Player(this.getUsername()), this.lobbyId);

            //out.println( (new Gson()).toJson( new StandardLobbyResponse(super.getUsername(), true) ) );
        }catch (LobbyFullException s){

            //out.println( (new Gson()).toJson( new StandardLobbyResponse(super.getUsername(), false) ) );
        }catch (IndexOutOfBoundsException i){
            //out.println( (new Gson()).toJson( new StandardLobbyResponse(super.getUsername(), false) ) );
        }
    }

    @Override
    public String toString() {
        return "LoginMultiPlayerRequest{" +
                "lobbyId=" + lobbyId +
                '}';
    }
}
