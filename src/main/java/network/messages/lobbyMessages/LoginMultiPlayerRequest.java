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
        StandardLobbyResponse s = null;
        try{
            System.out.println(getUsername() + " trying to join lobby "+lobbyId);
            LobbyHandler.getInstance().joinLobby(new Player(this.getUsername()), this.lobbyId, virtualClient);
            System.out.println(getUsername()+" joined lobby "+lobbyId);
            s = new StandardLobbyResponse(getUsername(), true);
        }catch (LobbyFullException l){
            s = new StandardLobbyResponse(getUsername(), false, "Lobby selected is full");
        }catch (IndexOutOfBoundsException i){
            s = new StandardLobbyResponse(getUsername(), false, "Lobby selected doesn't exist");
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            virtualClient.getVirtualView().sendStandardLobbyResponse(s);
        }
    }

}
