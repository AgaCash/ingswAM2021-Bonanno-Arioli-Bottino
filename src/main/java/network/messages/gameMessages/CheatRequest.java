package network.messages.gameMessages;

import controller.Controller;
import network.messages.MessageType;
import view.VirtualClient;

public class CheatRequest extends GameMessage{
    private int choice;

    public CheatRequest(String username, int choice){
        super(username, MessageType.CHEAT);
        this.choice = choice;

    }

    @Override
    public void executeCommand(Controller controller, VirtualClient view){
        if(choice==1)
            controller.cheat();
        else if(choice == 2)
            controller.cheat2();
        update(controller);
    }

    public void update(Controller controller){
        CheatResponse response = new CheatResponse(getUsername(),
                controller.getCurrentPlayer().getPlayerBoard().getStrongbox().convert(),
                controller.getCurrentPlayer().getPlayerBoard().getFaithTrack().convert());
        controller.getViews().forEach((element)-> { element.getVirtualView().updateCheat(response);});
    }
}
