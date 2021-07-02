package network.messages.lobbyMessages;

import exceptions.NoSuchUsernameException;
import exceptions.UsernameAlreadyInAGameException;
import exceptions.UsernameAlreadyUsedException;
import network.messages.MessageType;
import network.server.LobbyHandler;
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
        }catch (UsernameAlreadyInAGameException f){
            //riconnetti il player
            try {
                LobbyHandler.getInstance().getLobbyFromUsername(super.getUsername())
                        .resetController(virtualClient);
                virtualClient.getController().reconnectUsername(super.getUsername(), virtualClient);
                sendReconnection(virtualClient);
            } catch (NoSuchUsernameException ignored) {
            }
        } catch (UsernameAlreadyUsedException e) {
            sendError(virtualClient, e.getMessage());
        }
    }

    private void sendSuccess(VirtualClient virtualClient){
        RegisterUsernameResponse response =
                new RegisterUsernameResponse(super.getUsername(), false);
        virtualClient.getVirtualView().sendLobbyResponse(response);
    }

    private void sendReconnection(VirtualClient virtualClient){
        RegisterUsernameResponse response =
                new RegisterUsernameResponse(super.getUsername(), true);
        virtualClient.getVirtualView().sendLobbyResponse(response);
    }

    private void sendError(VirtualClient virtualClient, String message){
        RegisterUsernameResponse response =
                new RegisterUsernameResponse(super.getUsername(), "Username Already Exists");
        virtualClient.getVirtualView().sendLobbyResponse(response);
    }
}
