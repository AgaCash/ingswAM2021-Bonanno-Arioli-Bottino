package network.messages.gameMessages;

import clientController.LightController;
import clientModel.strongbox.LightStrongbox;
import network.messages.MessageType;

public class CheatResponse extends GameMessage {
    private LightStrongbox strongbox;

    public CheatResponse(String username, LightStrongbox newStrongbox){
        super(username, MessageType.CHEATUPDATE);
        this.strongbox = newStrongbox;
    }
    @Override
    public void executeCommand(LightController controller){
        controller.updateStrongbox(getUsername(), this.strongbox);
    }
}
