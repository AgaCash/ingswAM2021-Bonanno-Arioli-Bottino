package network.messages.lobbyMessages;

import exceptions.UsernameAlreadyUsedException;
import network.messages.MessageType;
import view.VirtualClient;

public class RegisterUsernameRequest extends LobbyMessage{
    public RegisterUsernameRequest(String username) {
        super(username, MessageType.REGISTER_USERNAME);
    }

    @Override
    public void executeCommand(VirtualClient virtualClient) {
        try {
            virtualClient.getVirtualView().setUsername(super.getUsername());
            sendSuccess(virtualClient);
        } catch (UsernameAlreadyUsedException e) {
            sendError(virtualClient, e.getMessage());
        }
    }

    private void sendSuccess(VirtualClient virtualClient){
        StandardLobbyResponse response =
                new StandardLobbyResponse(super.getUsername(), true);
        virtualClient.getVirtualView().sendLobbyMessage(response);
    }

    private void sendError(VirtualClient virtualClient, String message){
        StandardLobbyResponse response =
                new StandardLobbyResponse(super.getUsername(), false, "Username Already Exists");
        virtualClient.getVirtualView().sendLobbyMessage(response);
    }
}
