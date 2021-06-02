package network.messages.gameMessages;

import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

public class UpdateReconnectionRequest extends GameMessage{
    public UpdateReconnectionRequest(String username) {
        super(username, MessageType.RECONNECTION_UPDATE_REQUEST);
    }

    @Override
    public void executeCommand(Controller controller, VirtualClient client) {
        //fatto dal server

        //sto messaggio alla fine deve solo creare l'update

        //crea il messaggio e lo fa inviare a VV
        UpdateReconnectionResponse response = null;
                                         // = new UpdateReconnectionResponse(TUTTO IL MODEL);
        client.getVirtualView().sendReconnectionUpdate(response);
    }
}
