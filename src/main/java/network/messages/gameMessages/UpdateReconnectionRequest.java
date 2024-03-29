package network.messages.gameMessages;

import clientModel.player.LightPlayer;
import controller.Controller;
import exceptions.NoSuchUsernameException;
import network.messages.MessageType;
import network.server.LobbyHandler;
import view.VirtualClient;

import java.util.ArrayList;

//todo aga
public class UpdateReconnectionRequest extends GameMessage{
    public UpdateReconnectionRequest(String username) {
        super(username, MessageType.RECONNECTION_UPDATE_REQUEST);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {

        ArrayList<LightPlayer> playersssss = new ArrayList<>();
        int numOfPlayersInLobby;
        try {
            numOfPlayersInLobby= LobbyHandler.getInstance().getLobbyFromUsername(getUsername()).getUsernameList().size();
            controller.getPlayers().forEach(p-> {
                playersssss.add(p.convert());
                System.out.println("playerssss after convert "+playersssss);
                System.out.println("converted "+ p.convert());
            });
            UpdateReconnectionResponse response = new UpdateReconnectionResponse(
                    getUsername(),
                    controller.getMarketBoard().convert(),
                    controller.getDevBoard().convert(),
                    playersssss,
                    controller.isSinglePlayer(),
                    numOfPlayersInLobby
                    );
            client.getVirtualView().sendReconnectionUpdate(response);
        } catch (NoSuchUsernameException e) {
            e.printStackTrace();
        }
    }
}
