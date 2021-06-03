package network.messages.lobbyMessages;

import exceptions.GameAlreadyStartedException;
import exceptions.LobbyFullException;
import network.messages.MessageType;
import network.server.LobbyHandler;
import model.player.Player;

import view.VirtualClient;
import view.*;

public class LoginMultiPlayerRequest extends LobbyMessage{
    private int lobbyId;

    public LoginMultiPlayerRequest(String username, int lobbyId) {
        super(username, MessageType.JOINMULTIPLAYER);
        this.lobbyId = lobbyId;
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        LoginMultiPlayerResponse s = null;
        try{
            //joinLobby notifica già a tutti l'arrivo del nuovo player
            LobbyHandler.getInstance().joinLobby(new Player(this.getUsername()), this.lobbyId, virtualClient);
        }catch (LobbyFullException l){
            s = new LoginMultiPlayerResponse(getUsername(), "Lobby selected is full");
            virtualClient.getVirtualView().sendLobbyResponse(s);
        }catch (IndexOutOfBoundsException i) {
            s = new LoginMultiPlayerResponse(getUsername(), "Lobby selected doesn't exist");
            virtualClient.getVirtualView().sendLobbyResponse(s);
        }catch(GameAlreadyStartedException g){
            s = new LoginMultiPlayerResponse(getUsername(), g.getMessage());
            virtualClient.getVirtualView().sendLobbyResponse(s);
        }
    }

}
