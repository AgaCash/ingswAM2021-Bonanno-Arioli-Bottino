package network.messages.gameMessages;

import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

public class CheatRequest extends GameMessage{

    public CheatRequest(String username){
        super(username, MessageType.CHEAT);

    }

    @Override
    public void executeCommand(Controller controller, VirtualClient view){
        controller.cheat();
        update(controller);
    }

    public void update(Controller controller){
        CheatResponse response = new CheatResponse(getUsername(),
                controller.getCurrentPlayer().getPlayerBoard().getStrongbox().convert());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateCheat(response);});
    }
}
