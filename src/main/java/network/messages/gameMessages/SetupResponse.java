package network.messages.gameMessages;

import clientController.LightController;
import network.messages.MessageType;

public class SetupResponse extends GameMessage {
    private String message;
    private String firstPlayer;

    public SetupResponse(String username, String firstPlayer){
        super(username, MessageType.SETUPRESPONSE);
        this.message = " ";
        this.firstPlayer = firstPlayer;
        System.out.println("setup rsponse creata");
    }
    @Override
    public void executeCommand(LightController controller){
        controller.showSuccess(getUsername(), firstPlayer);
        controller.start(this.message+this.firstPlayer);
    }
}
