package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

public class UpdateReconnectionResponse extends GameMessage{

    //todo: teo mi servi tu qua zio
    //      mi serve un update completo di tutto il model


    public UpdateReconnectionResponse(String username/* + TUTTO IL MODEEEEEEEEEEEEEL*/) {
        super(username, MessageType.RECONNECTION_UPDATE_RESPONSE);
    }

    @Override
    public void executeCommand(LightController controller) {
        //fatto dal client

        //aggiorna per intero il lightModel (e lo mostra?)

        //dice al player di aspettare il suo turno
        controller.waitForMyTurn();
    }
}
