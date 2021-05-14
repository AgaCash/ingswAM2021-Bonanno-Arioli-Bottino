package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.server.LobbyHandler;
import view.View;
import view.VirtualClient;

import java.io.PrintWriter;

public class StandardLobbyResponse extends LobbyMessage{

    private final boolean success;
    private String message;

    public StandardLobbyResponse(String username, boolean success) {
        super(username, MessageType.STANDARDRESPONSE);
        this.success = success;
        this.message = null;
    }

    public StandardLobbyResponse(String username, boolean success, String message) {
        super(username, MessageType.STANDARDRESPONSE);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler,  VirtualClient virtualClient) {
        System.out.println("success: " + success);
    }

    @Override
    public String toString() {
        return "StandardLobbyResponse{" +
                "success=" + success +", "+
                "message=" + message +
                '}';
    }
}
