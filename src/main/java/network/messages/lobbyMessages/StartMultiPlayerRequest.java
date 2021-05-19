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
            sendSuccess(virtualClient);
        } catch (NotEnoughPlayersException | NoSuchUsernameException e) {
            sendError(virtualClient, e.getMessage());
        }
    }

    private void sendSuccess(VirtualClient virtualClient){
        StandardLobbyResponse response = new StandardLobbyResponse(super.getUsername(), true);
        virtualClient.getVirtualView().sendLobbyMessage(response);
    }

    private void sendError(VirtualClient virtualClient, String message){
        StandardLobbyResponse response = new StandardLobbyResponse(super.getUsername(), false, message);
        virtualClient.getVirtualView().sendLobbyMessage(response);
    }
}
