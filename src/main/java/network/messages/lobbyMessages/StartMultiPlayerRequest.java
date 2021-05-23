package network.messages.lobbyMessages;

import exceptions.NoSuchUsernameException;
import exceptions.NotEnoughPlayersException;
import network.messages.MessageType;
import network.server.Lobby;
import network.server.LobbyHandler;
import view.VirtualClient;

public class StartMultiPlayerRequest extends LobbyMessage{
    public StartMultiPlayerRequest(String username) {
        super(username, MessageType.LOBBYSTARTGAME_REQUEST);
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        Lobby userLobby = null;
        try {
            userLobby = LobbyHandler.getInstance().getLobbyFromUsername(super.getUsername());
            userLobby.startGame();
            //sendSuccess(virtualClient);
        } catch (NotEnoughPlayersException | NoSuchUsernameException e) {
            sendError(virtualClient, e.getMessage());
        }
    }

    //lo fa già startgame in broadcast
    private void sendSuccess(VirtualClient virtualClient){
        StartMultiPlayerResponse response = new StartMultiPlayerResponse(super.getUsername());
        virtualClient.getVirtualView().sendLobbyResponse(response);
    }

    private void sendError(VirtualClient virtualClient, String message){
        System.out.println("MA ENTRA QUA?");
        StartMultiPlayerResponse response = new StartMultiPlayerResponse(super.getUsername(), message);
        virtualClient.getVirtualView().sendLobbyResponse(response);
    }
}
