package network.messages.lobbyMessages;

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
            //System.out.println(getUsername() + " trying to join lobby "+lobbyId);
            LobbyHandler.getInstance().joinLobby(new Player(this.getUsername()), this.lobbyId, virtualClient);
            //System.out.println(getUsername()+" joined lobby "+lobbyId);
            //LO FACCIO FARE A .joinLobby così notifica a tutti l'arrivo di qualcuno
            //s = new LoginMultiPlayerResponse(getUsername());
        }catch (LobbyFullException l){
            s = new LoginMultiPlayerResponse(getUsername(), "Lobby selected is full");
            virtualClient.getVirtualView().sendLobbyResponse(s);
        }catch (IndexOutOfBoundsException i){
            s = new LoginMultiPlayerResponse(getUsername(), "Lobby selected doesn't exist");
            virtualClient.getVirtualView().sendLobbyResponse(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
