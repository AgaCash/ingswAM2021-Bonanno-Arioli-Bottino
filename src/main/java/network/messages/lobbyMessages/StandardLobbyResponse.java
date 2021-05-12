package network.messages.lobbyMessages;

import network.messages.MessageType;
import network.server.LobbyHandler;
import view.View;
import view.VirtualClient;

import java.io.PrintWriter;

public class StandardLobbyResponse extends LobbyMessage{

    private final boolean success;

    public StandardLobbyResponse(String username, boolean success) {
        super(username, MessageType.STANDARDRESPONSE);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public void executeCommand(LobbyHandler lobbyHandler, View view, VirtualClient virtualClient) {
        System.out.println("success: " + success);
    }

    @Override
    public String toString() {
        return "StandardLobbyResponse{" +
                "success=" + success +
                '}';
    }
}
