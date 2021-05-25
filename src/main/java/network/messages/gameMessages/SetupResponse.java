package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

public class SetupResponse extends GameMessage {
    private String message;
    private String firstPlayer;

    public SetupResponse(String username, String firstPlayer){
        super(username, MessageType.SETUPRESPONSE);
        this.message = "SI PARTE SEEEEEE UUUUUUUUUUUUUUU";
    }
    @Override
    public void executeCommand(LightController controller){
        controller.showSuccess(getUsername(), this.message);
    }
}
