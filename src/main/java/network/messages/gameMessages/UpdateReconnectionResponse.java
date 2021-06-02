package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

public class UpdateReconnectionResponse extends GameMessage{

    //todo:
    //      questi 2 messaggi servono a ricaricare tutto il light
    //      model dopo che il player si riconnette al game


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
