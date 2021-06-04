package network.messages.gameMessages;

import clientModel.player.LightPlayer;
import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

import java.util.ArrayList;

public class UpdateReconnectionRequest extends GameMessage{
    public UpdateReconnectionRequest(String username) {
        super(username, MessageType.RECONNECTION_UPDATE_REQUEST);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        //fatto dal server

        //sto messaggio alla fine deve solo creare l'update

        ArrayList<LightPlayer> players = new ArrayList<>();
        controller.getPlayers().forEach(e-> players.add(e.convert()));
        UpdateReconnectionResponse response = new UpdateReconnectionResponse(
                                                    getUsername(),
                                                    controller.getMarketBoard().convert(),
                                                    controller.getDevBoard().convert(),
                                                    players,
                                                    controller.isSinglePlayer());
        client.getVirtualView().sendReconnectionUpdate(response);
    }
}
