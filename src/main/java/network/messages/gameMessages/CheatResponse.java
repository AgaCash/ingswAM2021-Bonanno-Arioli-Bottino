package network.messages.gameMessages;

import clientController.LightController;
import clientModel.strongbox.LightStrongbox;
import clientModel.table.LightFaithTrack;
import network.messages.MessageType;

public class CheatResponse extends GameMessage {
    private LightStrongbox strongbox;
    private LightFaithTrack faithTrack;

    public CheatResponse(String username, LightStrongbox newStrongbox, LightFaithTrack track){
        super(username, MessageType.CHEATUPDATE);
        this.strongbox = newStrongbox;
        this.faithTrack = track;
    }
    @Override
    public void executeCommand(LightController controller){
        controller.updateStrongbox(getUsername(), this.strongbox);
        controller.updateFaithTrack(getUsername(), this.faithTrack);
    }
}
